package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StartRecommendationProcessCommandCV implements RecommendationProcessCommand<Void> {

    private final StandardAnalyzer analyzer = new StandardAnalyzer();
    private final Path indexDirectory = Paths.get("sc_server/indexDirectory");
    private final Directory index = loadIndexFolder();

    private final IndexWriterConfig config = new IndexWriterConfig(analyzer);
    private final IndexWriter writer = loadIndexWriter();

    private final UserManager userManager;
    private final Cv cv;
    private final RecommendationRepository recommendationRepository;

    public StartRecommendationProcessCommandCV(UserManager userManager, Cv cv, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.cv = cv;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Void execute() {
        addDocuments();
        closeIndexWriter();
        String queryString = buildQueryStringFromCv(cv);
        Query query = buildQuery(queryString);
        System.out.println("Parsed Query: " + query.toString());
        try {
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            int numberOfInternship = reader.maxDoc();
            StoredFields storedFields = reader.storedFields();
            for (int i = 0; i < numberOfInternship; i++) {
                Document doc = storedFields.document(i);
                Explanation explanation = searcher.explain(query, i);
                float score = explanation.isMatch() ? explanation.getValue().floatValue() : 0.0f;
                System.out.println((i + 1) + ". " + doc.get("title") + " -> Score: " + score);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Directory loadIndexFolder(){
        Directory index;
        try {
            index = FSDirectory.open(indexDirectory);
            // Clear the index directory if it exists
            if (Files.exists(indexDirectory)) {
                for (String file : index.listAll()) {
                    index.deleteFile(file);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while opening the index directory", e);
        }
        return index;
    }

    private IndexWriter loadIndexWriter(){
        IndexWriter writer;
        try {
            writer = new IndexWriter(index, config);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening the index writer", e);
        }
        return writer;
    }

    private void closeIndexWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while closing the index writer", e);
        }
    }

    private void addDocuments(){
        List<InternshipOffer> internshipOffers = userManager.getAllInternshipOffers();
        for(InternshipOffer internshipOffer : internshipOffers){
            addInternshipOffer(writer, internshipOffer.getTitle(), internshipOffer.getDescription(), internshipOffer.getRequiredSkills());
        }
    }

    private static void addInternshipOffer(IndexWriter writer, String title, String description, String required_skills){
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("description", description, Field.Store.YES));
        if (required_skills != null && !required_skills.isEmpty()) {
            doc.add(new TextField("required_skills", required_skills, Field.Store.YES));
        }

        //Add a "content" field that will be used for searching (it contains every other field)
        //Searching in a "single" field is more efficient than searching in multiple fields and leads to better results(according to Lucene documentation)
        doc.add(new TextField("content", title + " " + description + " " + required_skills, Field.Store.YES));

        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildQueryStringFromCv(Cv cv) {
        StringBuilder queryBuilder = new StringBuilder();
        if (cv.getSkills() != null) {
            queryBuilder.append(cv.getSkills()).append(" ");
        }
        if (cv.getWorkExperiences() != null) {
            queryBuilder.append(cv.getWorkExperiences()).append(" ");;
        }
        if (cv.getEducation() != null) {
            queryBuilder.append(cv.getEducation()).append(" ");;
        }
        if (cv.getProject() != null) {
            queryBuilder.append(cv.getProject()).append(" ");;
        }
        if (cv.getCertifications() != null) {
            queryBuilder.append(cv.getCertifications()).append(" ");;
        }
        return queryBuilder.toString().trim();
    }

    private Query buildQuery(String queryString){
        try {
            return new QueryParser("content", analyzer).parse(queryString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
