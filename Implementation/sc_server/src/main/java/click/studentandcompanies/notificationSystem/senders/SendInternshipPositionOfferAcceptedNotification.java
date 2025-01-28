package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInternshipPositionOfferAcceptedNotification implements SenderInterface{

    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        //List<String> emails = getEmails(userIDs, notificationManager);

        String pushTitle = "Accepted Internship Offer";
        String pushBody = "Congratulations! The candidate has accepted the internship offer";

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
