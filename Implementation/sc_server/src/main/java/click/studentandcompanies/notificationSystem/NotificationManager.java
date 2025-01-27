package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.entity.Contact;
import click.studentandcompanies.notificationSystem.entityRepository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationManager {

    private final ContactRepository contactRepository;

    public NotificationManager(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // todo implement the device token refresh
    public List<String> getDeviceTokens(String userID) {
        List<Contact> contacts = contactRepository.getAllById(userID);
        return contacts.stream().map(Contact::getDeviceToken).toList();
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

    public HttpStatus saveTokenNotification(Map<String, Object> payload) {
        if (payload.get("notificationToken") == null) {
            return HttpStatus.BAD_REQUEST;
        }
        String deviceToken = (String) payload.get("notificationToken");
        contactRepository.save(new Contact((String) payload.get("userID"), deviceToken));
        return HttpStatus.OK;
    }

}
