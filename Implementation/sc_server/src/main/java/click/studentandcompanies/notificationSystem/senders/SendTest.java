package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendTest implements SenderInterface {

    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        //List<String> emails = getEmails(userIDs, notificationManager);

        String pushTitle = "Test";
        String pushBody = "This is a test notification.";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);

        saveNotification(payload, userIDs, notificationManager);
        //EMAIL_SERVICE_ADAPTER.sendEmail(emails, new EmailContent(pushTitle, pushBody));
    }

    @Override
    public void saveNotification(NotificationPayload notificationPayload, List<String> userIDs, NotificationManager notificationManager) {
        System.out.println("Saving notification...");
        notificationManager.saveNotification(userIDs, notificationPayload);
    }
}
