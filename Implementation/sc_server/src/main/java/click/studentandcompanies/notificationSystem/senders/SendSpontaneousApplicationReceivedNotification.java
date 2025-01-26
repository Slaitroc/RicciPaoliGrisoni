package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendSpontaneousApplicationReceivedNotification implements SenderInterface {

    /**
     * The company is notified that a student has sent a spontaneous application.
     *
     * @param userIDs generally the ID of the company.
     * @param dto     contains data such as student_name, internship_title, messages, etc.
     */
    @Override
    public void sendNotification(List<String> userIDs, DTO dto, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        List<String> emails = getEmails(userIDs, notificationManager);

        String internshipTitle = (String) dto.getProperties().get("internship_title");
        String studentName = (String) dto.getProperties().get("student_name");

        String pushTitle = "New Spontaneous Application";
        String pushBody = "Student " + studentName + " has sent a spontaneous application for internship \""
                + internshipTitle + "\".";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);

    }
}
