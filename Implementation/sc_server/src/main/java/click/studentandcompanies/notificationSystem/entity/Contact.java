package click.studentandcompanies.notificationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @Size(max = 255)
    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    @Size(max = 255)
    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToMany
    List<Notification> notifications = new ArrayList<>();

    public Contact() {
        //empty constructor required by JPA
    }

    public Contact(String userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}