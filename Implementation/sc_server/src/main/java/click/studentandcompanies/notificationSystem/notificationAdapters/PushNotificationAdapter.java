package click.studentandcompanies.notificationSystem.notificationAdapters;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationAdapter {
    public void sendPushNotification(List<String> deviceTokens, NotificationPayload payload) {
        for (String deviceToken : deviceTokens) {
            Message message = createMessage(deviceToken, payload);
            FirebaseMessaging.getInstance().sendAsync(message);
            System.out.println("Sent push notification to " + deviceToken);
            System.out.println("Payload: " + payload.title() + "\n" + payload.body() +  "\n\n");
        }
    }

    private Message createMessage(String deviceToken, NotificationPayload payload) {
        return Message.builder()
            .setToken(deviceToken)
            .putData("title", payload.title())
            .putData("body", payload.body())
            .build();
    }
}
