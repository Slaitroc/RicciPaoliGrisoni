package click.studentandcompanies.notificationSystem.senders;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.List;

public class SendTerminateCommunicationNotification implements SenderInterface{
    @Override
    public void sendNotification(List<String> userIDs, DTO data, NotificationManager notificationManager) {
        List<String> studentDeviceTokens = getStudentDeviceTokens(userIDs, data, notificationManager);
        List<String> companyDeviceTokens = getCompanyDeviceTokens(userIDs, data, notificationManager);
        //List<String> studentEmails = getStudentEmails(userIDs, data, notificationManager);
        //List<String> companyEmails = getCompanyEmails(userIDs, data, notificationManager);

        String internshipTitle = (String) data.getProperties().get("internshipOfferTitle");
        String studentName = (String) data.getProperties().get("studentName");
        String communicationTitle = (String) data.getProperties().get("title");
        String company_name = (String) data.getProperties().get("companyName");

        String pushTitle = "Communication Terminated: " + communicationTitle;
        String studentPushBody = "The university has terminated the communication for the internship \"" + internshipTitle +
                "\" between you and the company " + company_name + ".";
        String companyPushBody = "The company has terminated the communication for the internship \"" + internshipTitle +
                "\" between you and the student " + studentName + ".";

        NotificationPayload studentPayload = new NotificationPayload(pushTitle, studentPushBody);
        NotificationPayload companyPayload = new NotificationPayload(pushTitle, companyPushBody);
        EmailContent studentEmailContent = new EmailContent(pushTitle, studentPushBody);
        EmailContent companyEmailContent = new EmailContent(pushTitle, companyPushBody);

        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(studentDeviceTokens, studentPayload);
        this.saveNotification(studentPayload, userIDs, notificationManager);
        PUSH_NOTIFICATION_ADAPTER.sendPushNotification(companyDeviceTokens, companyPayload);
        this.saveNotification(companyPayload, userIDs, notificationManager);
        //EMAIL_SERVICE_ADAPTER.sendEmail(studentEmails, studentEmailContent);
        //EMAIL_SERVICE_ADAPTER.sendEmail(companyEmails, companyEmailContent);

    }

    @Override
    public void saveNotification(NotificationPayload notificationPayload, List<String> userIDs, NotificationManager notificationManager) {
        notificationManager.saveNotification(userIDs, notificationPayload);
    }
}
