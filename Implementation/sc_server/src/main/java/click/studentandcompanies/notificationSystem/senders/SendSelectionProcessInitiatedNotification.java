package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.ArrayList;
import java.util.List;

public class SendSelectionProcessInitiatedNotification implements SenderInterface {

    /**
     * Sends a notification to the Participant of the selection process that the process has been initiated.
     * Triggered when a recommendation is accepted by both parties or when a company accepts a student's application.
     *
     * @param userIDs list of user IDs to send the notification to
     * @param data     DTO containing the necessary information about the student, company and internship related
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> studentDeviceTokens = getStudentDeviceTokens(userIDs, data, notificationManager);
        List<String> companyDeviceTokens = getCompanyDeviceTokens(userIDs, data ,notificationManager);
        List<String> studentEmails = getStudentEmails(userIDs, data, notificationManager);
        List<String> companyEmails = getStudentEmails(userIDs, data, notificationManager);

        String internshipTitle = (String) data.getProperties().get("internship_title");
        String company_name = (String) data.getProperties().get("company_name");
        String studentName = (String) data.getProperties().get("student_name");

        String studentTitle = "Selection Process Initiated";
        String studentBody = "The selection process for the internship \"" + internshipTitle +
                "\" for the company " + company_name + " has been initiated.";
        String companyTitle = "Selection Process Initiated";
        String companyBody = studentName + " is your new candidate for the internship " + internshipTitle;

        NotificationPayload studentPayload = new NotificationPayload(studentTitle, studentBody);
        EmailContent studentEmailContent = new EmailContent(studentTitle, studentBody);

        NotificationPayload companyPayload = new NotificationPayload(companyTitle, companyBody);
        EmailContent companyEmailContent = new EmailContent(companyTitle, companyBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(studentDeviceTokens, studentPayload);
        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(companyDeviceTokens, companyPayload);
        EMAIL_SERVICE_ADAPTER.sendEmail(studentEmails, studentEmailContent);
        EMAIL_SERVICE_ADAPTER.sendEmail(companyEmails, companyEmailContent);
    }
}
