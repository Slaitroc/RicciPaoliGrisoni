package click.studentandcompanies.controllers;

import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Or NotificationController. It implements the Facade pattern providing only a simple method to send notifications
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

    @PostMapping("/notification/private/send-token")
    public void sendTokenNotification(@RequestParam String token) {
        //todo: implement
    }
}

