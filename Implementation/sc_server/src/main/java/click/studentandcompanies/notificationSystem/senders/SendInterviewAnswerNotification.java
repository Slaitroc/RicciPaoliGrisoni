package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendInterviewAnswerNotification implements SenderInterface {

    /**
     * The company receives a notification that the student has completed the template with their answers.
     * @param userIDs list of company users to notify (or a single userID).
     * @param dto contains the interview data (e.g., student_name, internship_title, etc.)
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO dto, NotificationManager notificationManager) {
        List<String> deviceTokens = getDeviceTokens(userIDs, notificationManager);
        List<String> emails = getEmails(userIDs, notificationManager);

        String internshipTitle = (String) dto.getProperties().get("internship_title");
        String studentName = (String) dto.getProperties().get("student_name");

        String pushTitle = "Interview Completed";
        String pushBody = "The student " + studentName + " has completed the interview template for \""
                + internshipTitle + "\". Check the new answers!";

        NotificationPayload payload = new NotificationPayload(pushTitle, pushBody);
        EmailContent emailContent = new EmailContent(pushTitle, pushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
        EMAIL_SERVICE_ADAPTER.sendEmail(emails, emailContent);
    }
}