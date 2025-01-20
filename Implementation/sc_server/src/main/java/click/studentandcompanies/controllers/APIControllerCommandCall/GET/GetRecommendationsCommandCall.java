package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetRecommendationsCommandCall {

    RecommendationProcess recommendationProcess;
    Integer userID;

    public GetRecommendationsCommandCall(Integer userID, RecommendationProcess recommendationProcess) {
        this.recommendationProcess = recommendationProcess;
        this.userID = userID;
    }

    public ResponseEntity<List<DTO>> execute(){
        List<DTO> dtos = new ArrayList<>();
        try{
            List<Recommendation> recommendations = recommendationProcess.getRecommendationsByParticipant(userID);
            if (recommendations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for(Recommendation recommendation : recommendations){
                dtos.add(DTOCreator.createDTO(DTOTypes.RECOMMENDATION, recommendation));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);

        }catch (IllegalArgumentException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }catch (IllegalCallerException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
