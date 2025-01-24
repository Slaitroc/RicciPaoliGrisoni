package click.studentandcompanies.notificationSystem.entityRepository;

import click.studentandcompanies.notificationSystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}