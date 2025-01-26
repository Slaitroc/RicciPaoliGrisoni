package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class AcceptRecommendationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int RecommendationID;
    private final RecommendationProcess recommendationProcess;
    private final Map<String, Object> payload;
    private final NotificationManager notificationManager;

    public AcceptRecommendationCommandCall(int RecommendationID, RecommendationProcess recommendationProcess, NotificationManager notificationManager, Map<String, Object> payload) {
        this.RecommendationID = RecommendationID;
        this.recommendationProcess = recommendationProcess;
        this.payload = payload;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Recommendation recommendation = recommendationProcess.acceptRecommendation(RecommendationID, payload);
            if (recommendation.getStatus() == RecommendationStatusEnum.acceptedMatch) {
                List<String> userIDs = List.of(recommendation.getInternshipOffer().getCompany().getId(), recommendation.getCv().getStudent().getId());

                NotificationData data = new NotificationData(NotificationTriggerType.MATCH_FOUND, DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation));

                new NotificationController(notificationManager).sendNotification(userIDs, data);
                //todo: call feedback service
            }
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.CREATED);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
