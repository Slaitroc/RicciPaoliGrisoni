package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //todo redo all the fucking things
    @GetMapping("/private/test-notification")
    public HttpStatus testNotification(@RequestHeader("Authorization") String authToken) {
        String userID = GetUuid.getUuid(authToken);
        List<String> deviceTokens = notificationManager.getDeviceTokens(userID);
        for(String deviceToken : deviceTokens) {
            this.sendNotification(List.of(userID), new NotificationData(NotificationTriggerType.TEST, null));
        }
        return HttpStatus.OK;
    }


}
