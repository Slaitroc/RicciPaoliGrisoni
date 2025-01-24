package click.studentandcompanies.notificationSystem.notificationAdapters;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationAdapter {
    public void sendPushNotification(List<String> deviceTokens, NotificationPayload payload) {
        // todo implement the actual firebase push notification sending
        System.out.println("Sending push notification to " + deviceTokens.size() + " devices:\n" + deviceTokens);
        System.out.println("Payload: " + payload.title() + "\n" + payload.body() +  "\n\n");
    }
}
