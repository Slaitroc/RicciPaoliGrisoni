package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendNewCommunicationNotification implements SenderInterface {

    /**
     * Send a notification to the student, company and university when a new communication is created.
     *
     * @param userIDs list of student, company and university IDs
     * @param data    Contains the communication data
     */
    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> DeviceTokens = getDeviceTokens(userIDs, notificationManager);
        //List<String> Emails = getEmails(userIDs, notificationManager);

        String internshipTitle = (String) data.getProperties().get("internshipOffer_title");
        String studentName = (String) data.getProperties().get("student_name");
        String communicationTitle = (String) data.getProperties().get("title");
        String company_name = (String) data.getProperties().get("company_name");

        String pushTitle = "New Communication: " + communicationTitle;
        String pushBody = "A new communication has been created for the internship \"" + internshipTitle +
                "\" between " + studentName + " and " + company_name + ".";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(DeviceTokens, payload);
        this.saveNotification(payload, userIDs, notificationManager);
        //EMAIL_SERVICE_ADAPTER.sendEmail(Emails, emailContent);
    }

    @Override
    public void saveNotification(NotificationPayload notificationPayload, List<String> userIDs, NotificationManager notificationManager) {
        notificationManager.saveNotification(userIDs, notificationPayload);
    }
}
