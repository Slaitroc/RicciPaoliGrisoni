package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInterviewEvaluatedNotification implements SenderInterface {

    /**
     * Sends a notification to the student that has been evaluated in an interview
     *
     * @param userIDs List of students that has to be notified
     * @param data    DTO containing the information of the interview
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        List<String> emails = getEmails(userIDs, notificationManager);

        String internshipTitle = (String) data.getProperties().get("internship_title");
        String company_name = (String) data.getProperties().get("company_name");

        String pushTitle = "Interview Evaluated";
        String pushBody = company_name + " has evaluated your interview for the internship \"" + internshipTitle + "\".";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);
    }
}
