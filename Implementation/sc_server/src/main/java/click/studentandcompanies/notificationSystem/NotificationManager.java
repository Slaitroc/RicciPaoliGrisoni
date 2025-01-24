package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.notificationSystem.entity.Contact;
import click.studentandcompanies.notificationSystem.entityRepository.ContactRepository;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationManager {

    private final ContactRepository contactRepository;

    public NotificationManager(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    //todo implement the device token refresh
    public List<String> getDeviceTokens(Integer userID) {
        List<Contact> contacts = contactRepository.getAllById(userID);
        return contacts.stream().map(Contact::getDeviceToken).toList();
    }

    public String getUserEmail(Integer userID) {
        List<Contact> contacts = contactRepository.getAllById(userID);
        //todo see if a user can have multiple emails and what is the actual primary key of the table
        // now it is like this only to make it work
        return contacts.stream().map(Contact::getEmail).findFirst().orElse(null);
    }

    public void storeNotification(List<Integer> userIds, NotificationData data) {
        // todo: fix with what we actually want to save in the database about the notification
        //  (could be the only title and body more than the whole data)
        System.out.println("Notifica salvata per " + userIds.size() + " utenti.");
    }


}

