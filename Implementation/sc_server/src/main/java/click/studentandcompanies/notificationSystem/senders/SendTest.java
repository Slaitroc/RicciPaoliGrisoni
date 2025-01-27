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

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, new NotificationPayload(pushTitle, pushBody));
        //EMAIL_SERVICE_ADAPTER.sendEmail(emails, new EmailContent(pushTitle, pushBody));

    }
}
