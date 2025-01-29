package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.recommendationAlgorithm;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartRecommendationProcessCommandCV extends RecommendationProcessUtil implements RecommendationProcessCommand<Void> {

    private final UserManager userManager;
    private final Cv cv;
    private final RecommendationRepository recommendationRepository;
    private final List<Recommendation> recommendationList = new ArrayList<>();
    public StartRecommendationProcessCommandCV(UserManager userManager, Cv cv, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.cv = cv;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Void execute() {
        addDocuments();
        closeIndexWriter(writer);
        String queryString = buildQueryStringFromCv(cv);
        Query query = buildQuery(queryString, analyzer);
        match(query, indexDirectory);
        recommendationRepository.saveAll(recommendationList);
        return null;
    }

    private void addDocuments(){
        List<InternshipOffer> internshipOffers = userManager.getAllInternshipOffers();
        for(InternshipOffer internshipOffer : internshipOffers){
            addInternshipOffer(writer, internshipOffer.getTitle(), internshipOffer.getDescription(), internshipOffer.getRequiredSkills(), internshipOffer.getId());
        }
    }

    private void match(Query query, Directory indexDirectory){
        try {
            //Search through the different indexed documents
            IndexReader reader = DirectoryReader.open(indexDirectory);
            //Create a searcher to search through the selected index
            IndexSearcher searcher = new IndexSearcher(reader);
            int numberOfInternship = reader.maxDoc();
            //Load all documents (internshipOffer)
            StoredFields storedFields = reader.storedFields();
            for (int i = 0; i < numberOfInternship; i++) {
                Document doc = storedFields.document(i);
                float score = searcher.explain(query, i).isMatch() ? searcher.explain(query, i).getValue().floatValue() : 0.0f;
                createRecommendation(doc, score);
                System.out.println(i + ") " + doc.get("title") + " - " + score);
            }
            System.out.println("*--------------------------*");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createRecommendation(Document doc, float score){
        if(score >= computeDynamicThreshold(userManager.getAllFeedbacks())){
            int id = doc.getField("id").numericValue().intValue();
            InternshipOffer internshipOffer = userManager.getInternshipOfferById(id);

            //todo se in futuro si vuole correggere sto algoritmo e eliminare i match deprecati bisogna resettare tutti i match di questo studente
            // cosí stiamo solo facendo in modo che uno studente e una internship abbiano solo un match
            Recommendation oldRecommendation = recommendationRepository.findRecommendationByInternshipOfferAndCv(internshipOffer, cv);
            if(oldRecommendation != null && oldRecommendation.getStatus() != RecommendationStatusEnum.pendingMatch){
                return;
            }else if(oldRecommendation != null ) recommendationRepository.removeRecommendationById(oldRecommendation.getId());

            Recommendation recommendation = new Recommendation(internshipOffer, cv, RecommendationStatusEnum.pendingMatch, score);
            recommendationList.add(recommendation);
        }
    }
}
