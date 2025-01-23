package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.ArrayList;
import java.util.List;

public class SendNewCommunicationNotification implements SenderInterface {

    /**
     * Send a notification to the student, company and university when a new communication is created.
     * @param userIDs list of student, company and university IDs
     * @param data Contains the communication data
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager) {
        List<Integer> DeviceTokens = new ArrayList<>();
        List<String> Emails = new ArrayList<>();
        for(Integer userID : userIDs) {
            DeviceTokens.addAll(notificationManager.getDeviceTokens(userID));
            Emails.add(notificationManager.getUserEmail(userID));
        }
        try{
            NotificationPayload payload = new NotificationPayload("New Communication: " + data.getProperties().get("title"),
                    "A new communication has been created for the internship " + data.getProperties().get("internshipOffer_title") +
                            " between " + data.getProperties().get("student") + " and " + data.getProperties().get("company") + ".");

            EmailContent emailContent = new EmailContent("New Communication: " + data.getProperties().get("title"),
                    "A new communication has been created for the internship " + data.getProperties().get("internshipOffer_title") +
                            " between " + data.getProperties().get("student") + " and " + data.getProperties().get("company") + ".");

            PUSH_NOTIFICATION_ADAPTER.sendPushNotification(DeviceTokens, payload);
            EMAIL_SERVICE_ADAPTER.sendEmail(Emails, emailContent);

        }catch (NullPointerException e){
            System.out.println("Error with the NotificationData: check the TriggerDataType and the DTO fields");
            e.printStackTrace();
        }
    }
}
