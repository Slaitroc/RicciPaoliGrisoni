package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Or NotificationController. It implements the Facade pattern providing only a simple method to send notifications
 * hiding the complexity of the NotificationManager and the Senders.
 */
@Service
public class NotificationFacade {

    private final NotificationManager notificationManager;

    @Autowired
    public NotificationFacade(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void sendNotification(List<Integer> userIDs, NotificationData data) {
        data.getTriggerType().getSender().sendNotification(userIDs, data.getDTO(), notificationManager);
    }
}

