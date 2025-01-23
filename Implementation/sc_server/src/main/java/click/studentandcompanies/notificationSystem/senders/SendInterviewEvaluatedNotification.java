package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInterviewEvaluatedNotification implements SenderInterface {

    /**
     * Sends a notification to the student that has been evaluated in an interview
     * @param userIDs List of students that has to be notified
     * @param data DTO containing the information of the interview
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager) {
        try{
            NotificationPayload payload = new NotificationPayload("Interview Evaluated", data.getProperties().get("company") + " has evaluated your interview for the internship " + data.getProperties().get("internship"));
            EmailContent emailContent = new EmailContent("Interview Evaluated", data.getProperties().get("company") + " has evaluated your interview for the internship " + data.getProperties().get("internship"));

            PUSH_NOTIFICATION_ADAPTER.sendPushNotification(notificationManager.getDeviceTokens(userIDs.getFirst()), payload);
            EMAIL_SERVICE_ADAPTER.sendEmail(List.of(notificationManager.getUserEmail(userIDs.getFirst())), emailContent);

        }catch (NullPointerException e){
            System.out.println("Error with the NotificationData: check the TriggerDataType and the DTO fields");
            e.printStackTrace();
        }
    }
}
