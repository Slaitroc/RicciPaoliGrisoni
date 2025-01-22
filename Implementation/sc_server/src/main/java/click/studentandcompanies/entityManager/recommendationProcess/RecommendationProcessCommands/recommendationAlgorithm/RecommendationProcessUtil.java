package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.recommendationAlgorithm;

import click.studentandcompanies.entity.Cv;
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

    protected String buildQueryStringFromCv(Cv cv) {
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
