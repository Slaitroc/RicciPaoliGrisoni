package click.studentandcompanies.controllers.APIControllerCommand.POST;

import click.studentandcompanies.controllers.APIControllerCommand.APIControllerCommand;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class AcceptRecommendationCommand implements APIControllerCommand<ResponseEntity<DTO>> {
    private final int RecommendationID;
    private final RecommendationProcess recommendationProcess;
    private final Map<String, Object> payload;

    public AcceptRecommendationCommand(int RecommendationID, RecommendationProcess recommendationProcess, Map<String, Object> payload) {
        this.RecommendationID = RecommendationID;
        this.recommendationProcess = recommendationProcess;
        this.payload = payload;
    }
    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Recommendation recommendation = recommendationProcess.acceptRecommendation(RecommendationID, (Integer) payload.get("userID"));
            if(recommendation.getStatus() == RecommendationStatusEnum.acceptedMatch){
                //todo: call the notification service
                //todo: call feedback service
            }
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.CREATED);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
