package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.recommendationAlgorithm;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class StartRecommendationProcessCommandOffer extends RecommendationProcessUtil implements RecommendationProcessCommand<Void> {
    private final UserManager userManager;
    private final InternshipOffer internshipOffer;
    private final RecommendationRepository recommendationRepository;
    private final List<Recommendation> recommendationList = new ArrayList<>();

    public StartRecommendationProcessCommandOffer(UserManager userManager, InternshipOffer internshipOffer, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.internshipOffer = internshipOffer;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Void execute() {
        addInternshipOffer(writer, internshipOffer.getTitle(), internshipOffer.getDescription(), internshipOffer.getRequiredSkills(), internshipOffer.getId());
        closeIndexWriter(writer);
        List<AbstractMap.SimpleImmutableEntry<Cv, Query>> queryList = buildQueryList(userManager.getAllCvs());
        match(queryList, indexDirectory);
        recommendationRepository.saveAll(recommendationList);
        return null;
    }

    private List<AbstractMap.SimpleImmutableEntry<Cv, Query>> buildQueryList(List<Cv> cvList){
        List<AbstractMap.SimpleImmutableEntry<Cv, Query>> queryList = new ArrayList<>();
        for(Cv cv : cvList){
            String queryString = buildQueryStringFromCv(cv);
            Query query = buildQuery(queryString, analyzer);
            queryList.add(new AbstractMap.SimpleImmutableEntry<>(cv, query));
        }
        return queryList;
    }

    private void match(List<AbstractMap.SimpleImmutableEntry<Cv, Query>> queryList, Directory indexDirectory) {
        try {
            IndexReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);
            StoredFields storedFields = reader.storedFields();

            for (AbstractMap.SimpleImmutableEntry<Cv, Query> entry : queryList) {
                Query query = entry.getValue();
                if(reader.maxDoc() != 1){
                    throw new RuntimeException("Only the new internship offer should be indexed");
                }
                Document doc = storedFields.document(0);
                float score = searcher.explain(query, 0).isMatch() ? searcher.explain(query, 0).getValue().floatValue() : 0.0f;
                createRecommendation(score, entry.getKey());
                System.out.println(entry.getKey().getStudent().getName() + ") " + doc.get("title") + " - " + score);
            }
            System.out.println("*--------------------------*");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createRecommendation(float score, Cv cv){
        if(score > 0.0f){
            Recommendation recommendation = new Recommendation(internshipOffer, cv, RecommendationStatusEnum.pendingMatch, score);
            recommendationList.add(recommendation);
        }
    }

}
