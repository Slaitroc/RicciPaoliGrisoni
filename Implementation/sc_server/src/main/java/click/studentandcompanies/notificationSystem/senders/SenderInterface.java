package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationAdapters.EmailServiceAdapter;
import click.studentandcompanies.notificationSystem.notificationAdapters.PushNotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public interface SenderInterface {
    PushNotificationAdapter PUSH_NOTIFICATION_ADAPTER = new PushNotificationAdapter();
    EmailServiceAdapter EMAIL_SERVICE_ADAPTER = new EmailServiceAdapter();

    void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager);

    default List<Integer> getDeviceTokens(List<Integer> userIDs, NotificationManager notificationManager) {
        List<Integer> deviceTokens = new ArrayList<>();
        for (Integer userID : userIDs) {
            deviceTokens.addAll(notificationManager.getDeviceTokens(userID));
        } //todo: implement the correct function to refresh the device tokens
        return deviceTokens;
    }

    default List<String> getEmails(List<Integer> userIDs, NotificationManager notificationManager) {
        List<String> emails = new ArrayList<>();
        for (Integer userID : userIDs) {
            emails.add(notificationManager.getUserEmail(userID));
        }
        return emails;
    }
}
