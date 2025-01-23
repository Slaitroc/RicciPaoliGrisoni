package click.studentandcompanies.notificationSystem.notificationUtils;

import click.studentandcompanies.notificationSystem.senders.*;

/**
 * This enum contains all the possible triggers for the notification system
 * Each trigger has a corresponding sender (strategy pattern) that will send the notification
 */
public enum NotificationTriggerType {
    INTERNSHIP_CANCELLED(new SendInternshipCancelledNotification()),
    SPONTANEOUS_APPLICATION_ACCEPTED(new SendSelectionProcessInitiatedNotification()),
    MATCHED_FOUND(new SendSelectionProcessInitiatedNotification()),
    NEW_COMMUNICATION(new SendNewCommunicationNotification()),
    INTERVIEW_EVALUATED(new SendInterviewEvaluatedNotification()),
    SPONTANEOUS_APPLICATION_RECEIVED(new SendSpontaneousApplicationReceivedNotification());

    private final SenderInterface sender;

    NotificationTriggerType(SenderInterface sender) {
        this.sender = sender;
    }

    /**
     * @return the Sender class that will manage the notification
     */
    public SenderInterface getSender() {
        return sender;
    }
}
