package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.recommendationAlgorithm;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.utils.RecommendationAlgortimSetting;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class RecommendationProcessUtil {
    protected final StandardAnalyzer analyzer = new StandardAnalyzer();
    protected final Path indexDirectoryPath = Paths.get("sc_server/indexDirectory");
    protected final Directory indexDirectory = loadIndexFolder(indexDirectoryPath);
    protected final IndexWriterConfig config = new IndexWriterConfig(analyzer);
    protected final IndexWriter writer = loadIndexWriter(indexDirectory, config);

    protected Query buildQuery(String queryString, StandardAnalyzer analyzer){
        try {
            return new QueryParser("content", analyzer).parse(queryString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //N.B. Special char are reserved for Lucene query language, as written in the documentation that I of course read and understood well :)
    protected String buildQueryStringFromCv(Cv cv) {
        StringBuilder queryBuilder = new StringBuilder();
        if (cv.getSkills() != null) {
            queryBuilder.append(cv.getSkills().replaceAll("[^a-zA-Z0-9\\s]", "")).append(" ");
        }
        if (cv.getWorkExperiences() != null) {
            queryBuilder.append(cv.getWorkExperiences().replaceAll("[^a-zA-Z0-9\\s]", "")).append(" ");
        }
        if (cv.getEducation() != null) {
            queryBuilder.append(cv.getEducation().replaceAll("[^a-zA-Z0-9\\s]", "")).append(" ");
        }
        if (cv.getProject() != null) {
            queryBuilder.append(cv.getProject().replaceAll("[^a-zA-Z0-9\\s]", "")).append(" ");
        }
        if (cv.getCertifications() != null) {
            queryBuilder.append(cv.getCertifications().replaceAll("[^a-zA-Z0-9\\s]", "")).append(" ");
        }
        return queryBuilder.toString().trim();
    }

    protected void addInternshipOffer(IndexWriter writer, String title, String description, String required_skills, int id){
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("description", description, Field.Store.YES));
        if (required_skills != null && !required_skills.isEmpty()) {
            doc.add(new TextField("required_skills", required_skills, Field.Store.YES));
        }
        //Add a "content" field that will be used for searching (it contains every other field)
        //Searching in a "single" field is more efficient than searching in multiple fields and leads to better results(according to Lucene documentation)
        doc.add(new TextField("content", title + " " + description + " " + required_skills, Field.Store.YES));
        // Stored, not indexed, but is retrievable for creating a Recommendation object
        doc.add(new StoredField("id", id));
        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void closeIndexWriter(IndexWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while closing the index writer", e);
        }
    }

    //Feedback compute the threshold using a variation of the exponential moving average (EMA) formula.
    //A older feedback has less weight than a recent one, so the threshold is more influenced by recent feedbacks.
    //The formula of EMA is: threshold = e^((1 - alpha) * normalize(rating1) + ... + (1 - alpha)^n * normalize(ratingn))/((1 - alpha) + (1 - alpha)^2 + ... + (1 - alpha)^n)
    //Where the first term is the most recent feedback and the last term is the oldest feedback in the last NUMBER OF days.
    //Also notice that if we get a higher rating, the threshold will decrease and more match will be created. The opposite is also true.
    protected double computeDynamicThreshold(List<Feedback> feedbacks) {
        double numerator = 0.0;
        double denominator = 0.0;
        long now = System.currentTimeMillis();
        long numberOfDaysInMillis = (long) RecommendationAlgortimSetting.DAYS_BEFORE_EXPIRATION.getValue() * 24 * 60 * 60 * 1000;
        feedbacks.sort(Comparator.comparing(Feedback::getUploadTime).reversed());
        int feedbacksSize = feedbacks.size();
        System.out.println("feedbacks size: " + feedbacksSize);
        for (int i = 0; i < feedbacksSize; i++) {
            Feedback feedback = feedbacks.get(i);
            long feedbackTime = feedback.getUploadTime().toEpochMilli();
            // Only consider feedbacks that are less than NUMBER OF days old
            if (now - feedbackTime >= numberOfDaysInMillis) {
                break;
            }
            double alpha = RecommendationAlgortimSetting.ALPHA.getValue();
            float weight = (float) Math.pow(1 - alpha, i+1);

            //Normalize rating (1-5) to range [0, 1], where 1 maps to 1.0 and 5 maps to 0.0
            double normalizedRating = (5 - feedback.getRating()) / 4.0;
            System.out.println("normalized rating: " + normalizedRating + "original rating: " + feedback.getRating());
            numerator += weight * normalizedRating;
            denominator += weight;
        }
        //Default threshold if no valid feedback is found
        if (denominator == 0) {
            return RecommendationAlgortimSetting.DEFAULT_THRESHOLD.getValue();
        }
        //Exponential function to ensure the threshold stays in range [0,1]
        double threshold = Math.exp(- numerator / denominator);
        System.out.println("dynamic threshold: " + threshold);
        return threshold;
    }

    private Directory loadIndexFolder(Path indexDirectoryPath){
        Directory directory;
        try {
            directory = FSDirectory.open(indexDirectoryPath);
            // Clear the index directory if it exists
            if (Files.exists(indexDirectoryPath)) {
                for (String file : directory.listAll()) {
                    directory.deleteFile(file);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while opening the index directory", e);
        }
        return directory;
    }


    private IndexWriter loadIndexWriter(Directory indexDirectory, IndexWriterConfig config){
        IndexWriter writer;
        try {
            writer = new IndexWriter(indexDirectory, config);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening the index writer", e);
        }
        return writer;
    }
}
