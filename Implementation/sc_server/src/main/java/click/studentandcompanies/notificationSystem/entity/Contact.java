package click.studentandcompanies.notificationSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "contact")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contact {
    @Id
    @Column(name = "user_id", nullable = false)
    private String id;

    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    public Contact() {
        // empty constructor required by JPA
    }

    public Contact(String id, String deviceToken) {
        this.id = id;
        this.deviceToken = deviceToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
