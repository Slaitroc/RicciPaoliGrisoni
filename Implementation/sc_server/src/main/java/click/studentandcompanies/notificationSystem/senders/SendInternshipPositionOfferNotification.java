package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInternshipPositionOfferNotification implements SenderInterface {

    /**
     * The student receives an internship offer after the interview. They can decide whether to accept it or not.
     *
     * @param userIDs list of students to send the notification to (usually a single ID).
     * @param data    contains data such as internship_title, company_name, and possibly a link to accept/reject.
     */
    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        //List<String> emails = getEmails(userIDs, notificationManager);

        String pushTitle = "Internship Offer";
        String pushBody = "Congratulations! You've received an offer for the internship";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        //EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        this.saveNotification(payload, userIDs, notificationManager);
        //EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);
    }

    @Override
    public void saveNotification(NotificationPayload notificationPayload, List<String> userIDs, NotificationManager notificationManager) {
        notificationManager.saveNotification(userIDs, notificationPayload);
    }
}
