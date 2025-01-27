package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.entity.Contact;
import click.studentandcompanies.notificationSystem.entity.Notification;
import click.studentandcompanies.notificationSystem.entityRepository.ContactRepository;
import click.studentandcompanies.notificationSystem.entityRepository.NotificationRepository;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;
import com.google.protobuf.MapEntry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationManager {

    private final ContactRepository contactRepository;
    private final NotificationRepository notificationRepository;

    public NotificationManager(ContactRepository contactRepository, NotificationRepository notificationRepository) {
        this.contactRepository = contactRepository;
        this.notificationRepository = notificationRepository;
    }

    // todo implement the device token refresh
    public List<String> getDeviceTokensOf(String userID) {
        List<Contact> contacts = contactRepository.getContactsByUserId(userID);
        return contacts.stream().map(Contact::getDeviceToken).toList();
    }

    //todo, device token should become primary key.
    //todo if the device token is already in the database, do nothing
    public HttpStatus saveTokenNotification(Map<String, Object> payload) {
        if (payload.get("notificationToken") == null) {
            return HttpStatus.BAD_REQUEST;
        }
        String deviceToken = (String) payload.get("notificationToken");
        //If the pair (userID, deviceToken) is already in the database, do nothing
        List<Contact> contacts = contactRepository.findAll();
        for (Contact contact : contacts) {
            if (contact.getDeviceToken().equals(deviceToken) && contact.getUserId().equals(payload.get("userID"))) {
                return HttpStatus.OK;
            }
        }
        contactRepository.save(new Contact((String) payload.get("userID"), deviceToken));
        return HttpStatus.OK;
    }

    //For each notification, save all the contacts that receive it
    //For each user, retrieve all the contacts and save the notification for each of them
    public void saveNotification(List<String> userIds, NotificationPayload notificationPayload) {
        Notification notification = new Notification(notificationPayload.title(), notificationPayload.body());
        notificationRepository.save(notification);
        for (String userID : userIds) {
            List<Contact> contacts = contactRepository.getContactsByUserId(userID);
            for (Contact contact : contacts) {
                contact.getNotifications().add(notification);
                contactRepository.save(contact);
                notification.getContacts().add(contact);
                notificationRepository.save(notification);
            }
        }
        System.out.println("notification saved for " + userIds.size() + " users.");
    }

    /*
     * public String getUserEmail(String userID) {
     * List<Contact> contacts = contactRepository.getAllById(userID);
     * //todo see if a user can have multiple emails and what is the actual primary
     * key of the table
     * // now it is like this only to make it work
     * return contacts.stream().map(Contact::getEmail).findFirst().orElse(null);
     * }
     */

    /*
     * public void storeNotification(List<String> userIds, NotificationData data) {
     * // todo: fix with what we actually want to save in the database about the
     * notification
     * // (could be the only title and body more than the whole data)
     * System.out.println("Notifica salvata per " + userIds.size() + " utenti.");
     * }
     */

}
