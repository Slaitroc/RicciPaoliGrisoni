package click.studentandcompanies.notificationSystem.senders;


import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationPayload;

import java.util.ArrayList;
import java.util.List;

public class SendInternshipCancelledNotification implements SenderInterface {

    /**
     * Sends a notification of closed internships to students that had open recommendations or
     * spontaneous applications to that Internship.
     * @param userIDs List of students that has to be notified
     * @param dto DTO containing the information of the internship that has been closed
     */
    @Override
    public void sendNotification(List<Integer> userIDs, DTO dto, NotificationManager notificationManager) {
        List<Integer> deviceTokens = new ArrayList<>();
        List<String> userEmails = new ArrayList<>();
        for(Integer userID : userIDs) {
            deviceTokens.addAll(notificationManager.getDeviceTokens(userID));
            userEmails.add(notificationManager.getUserEmail(userID));
            //todo: how does the device token works when it expires?
        }
        try {
            NotificationPayload payload = new NotificationPayload("Internship Offer Cancelled", "The internship " + dto.getProperties().get("title") + " has been closed.");
            EmailContent emailContent = new EmailContent("Internship Offer Cancelled", "The internship " + dto.getProperties().get("title") + " has been closed.");
            PUSH_NOTIFICATION_ADAPTER.sendPushNotification(deviceTokens, payload);
            EMAIL_SERVICE_ADAPTER.sendEmail(userEmails, emailContent);
        }catch (NullPointerException e){
        System.out.println("Error with the NotificationData: check the TriggerDataType and the DTO fields");
        e.printStackTrace();
    }
    }
}
