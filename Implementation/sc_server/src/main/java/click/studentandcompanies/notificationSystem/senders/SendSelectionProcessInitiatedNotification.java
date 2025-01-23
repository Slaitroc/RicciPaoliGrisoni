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
     * @param userIDs list of user IDs to send the notification to
     * @param dto DTO containing the necessary information about the student, company and internship related
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO dto, NotificationManager notificationManager) {
        List<Integer> studentDeviceTokens = new ArrayList<>();
        List<Integer> companyDeviceTokens = new ArrayList<>();
        List<String> studentEmails = new ArrayList<>();
        List<String> companyEmails = new ArrayList<>();
        for(Integer userID : userIDs) {
            if(userID == dto.getProperties().get("student_ID")) {
                studentDeviceTokens.addAll(notificationManager.getDeviceTokens(userID));
                studentEmails.add(notificationManager.getUserEmail(userID));
            }
            else {
                companyDeviceTokens.addAll(notificationManager.getDeviceTokens(userID));
                companyEmails.add(notificationManager.getUserEmail(userID));
            }
        }


        //todo: valuate when to send email or inApp notification and if the content should be different
        NotificationPayload studentPayload = new NotificationPayload("Selection Process Initiated",
                "The selection process for the internship " + dto.getProperties().get("internship_title") +
                        " for the company " + dto.getProperties().get("company_title") + " has been initiated."); //or something like that
        EmailContent studentEmailContent = new EmailContent("Selection Process Initiated",
                "The selection process for the internship " + dto.getProperties().get("internship_title") +
                        " for the company " + dto.getProperties().get("company_title") + " has been initiated.");

        NotificationPayload companyPayload = new NotificationPayload("Selection Process Initiated",
                dto.getProperties().get("student_name") + " is your new candidate for the internship "
                        + dto.getProperties().get("internship_title"));
        EmailContent companyEmailContent = new EmailContent("Selection Process Initiated",
                dto.getProperties().get("student_name") + " is your new candidate for the internship "
                        + dto.getProperties().get("internship_title"));

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(studentDeviceTokens, studentPayload);
        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(companyDeviceTokens, companyPayload);
        EMAIL_SERVICE_ADAPTER.sendEmail(studentEmails, studentEmailContent);
        EMAIL_SERVICE_ADAPTER.sendEmail(companyEmails, companyEmailContent);
    }
}
