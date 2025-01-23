package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationAdapters.EmailServiceAdapter;
import click.studentandcompanies.notificationSystem.notificationAdapters.PushNotificationAdapter;

import java.util.List;

public interface SenderInterface {
    PushNotificationAdapter PUSH_NOTIFICATION_ADAPTER = new PushNotificationAdapter();
    EmailServiceAdapter EMAIL_SERVICE_ADAPTER = new EmailServiceAdapter();

    void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager);
}
