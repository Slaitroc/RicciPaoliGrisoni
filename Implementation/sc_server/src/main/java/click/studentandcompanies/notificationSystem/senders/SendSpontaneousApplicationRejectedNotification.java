package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendSpontaneousApplicationRejectedNotification implements SenderInterface {

    /**
     * The student is notified that the company has rejected their spontaneous application.
     *
     * @param userIDs generally the ID of the student.
     * @param data     contains data such as companyName, internshipTitle, messages, etc.
     */
    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        //List<String> emails = getEmails(userIDs, notificationManager);

        String internshipTitle = (String) data.getProperties().get("internshipOfferTitle");
        String companyName = (String) data.getProperties().get("companyName");

        String pushTitle = "Spontaneous Application Rejected";
        String pushBody = "Your spontaneous application for the internship \"" + internshipTitle +
                "\" at " + companyName + " has been rejected.";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        //EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        this.saveNotification(payload, userIDs, notificationManager);
        //EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);

    }

    @Override
    public void saveNotification(NotificationPayload notificationPayload, List<String> userIDs, NotificationManager notificationManager) {
        notificationManager.saveNotification(userIDs, notificationPayload);
    }
}
