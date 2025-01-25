package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    public void sendNotification(List<Integer> userIDs, NotificationData data) {
        data.getTriggerType().getSender().sendNotification(userIDs, data.getDTO(), notificationManager);
    }

    @PostMapping("/private/send-token")
    public ResponseEntity<?> sendTokenNotification(@RequestBody Map<String, Object> payload,
            @RequestHeader("Authorization") String authHeader, @RequestHeader("X-Custom-Header") String traefikHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        String idToken = authHeader.substring(7);

        try {
            System.out.println("token:" + payload.get("notificationToken"));
            System.out.println(traefikHeader);
            return ResponseEntity.ok(payload.get("notificationToken"));

        } catch (Error e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

    }
}
