package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;

import click.studentandcompanies.utils.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Or NotificationController. It implements the Facade pattern providing only a
 * simple method to send notifications
 * hiding the complexity of the NotificationManager and the Senders.
 */
@RestController
@RequestMapping("/notification-api")
public class NotificationController {

    private final NotificationManager notificationManager;

    @Autowired
    public NotificationController(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void sendNotification(List<String> userIDs, NotificationData data) {
        data.getTriggerType().getSender().sendNotification(userIDs, data.getDTO(), notificationManager);
    }

    @PostMapping("/private/send-token")
    public HttpStatus sendTokenNotification(@RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String authToken) {
        String userID = GetUuid.getUuid(authToken);
        payload.put("userID", userID);
        return notificationManager.saveTokenNotification(payload);
    }


}
