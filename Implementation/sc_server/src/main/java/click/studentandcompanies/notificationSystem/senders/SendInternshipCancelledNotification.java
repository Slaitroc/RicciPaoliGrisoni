package click.studentandcompanies.notificationSystem.senders;


import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInternshipCancelledNotification implements SenderInterface {

    /**
     * Sends a notification of closed internships to students that had open recommendations or
     * spontaneous applications to that Internship.
     *
     * @param userIDs List of students that has to be notified
     * @param dto     DTO containing the information of the internship that has been closed
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO dto, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        List<String> userEmails = getEmails(userIDs, notificationManager);

        //todo: valuate when to send email or inApp notification and if the content should be different
        String internshipTitle = (String) dto.getProperties().get("internship_title");

        String pushTitle = "Internship Offer Cancelled";
        String pushBody = "The internship \"" + internshipTitle + "\" has been closed.";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        EMAIL_SERVICE_ADAPTER.sendEmail(userEmails, emailContent);
    }
}
