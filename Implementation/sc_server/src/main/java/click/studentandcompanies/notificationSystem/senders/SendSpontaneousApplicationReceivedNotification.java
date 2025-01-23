package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.ArrayList;
import java.util.List;

public class SendSpontaneousApplicationReceivedNotification implements SenderInterface {

    /**
     * La compagnia viene avvisata che uno studente ha inviato spontaneamente una candidatura.
     * @param userIDs in genere id della/e azienda/e. Se c'è un singolo recruiter userID, o più contatti.
     * @param dto contiene dati come student_name, internship_title, messaggi, etc.
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO dto, NotificationManager notificationManager) {
        List<Integer> deviceTokens = notificationManager.getDeviceTokens(userIDs.getFirst());
        List<String> emails = List.of(notificationManager.getUserEmail(userIDs.getFirst()));

            String internshipTitle = (String) dto.getProperties().get("internship_title");
            String studentName = (String) dto.getProperties().get("student_name");

            String pushTitle = "New Spontaneous Application";
            String pushBody = "Student " + studentName + " has sent a spontaneous application for internship \""
                    + internshipTitle + "\".";

            NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
            EmailContent emailContent = new EmailContent(pushTitle, pushBody);

            PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
            EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);

    }
}
