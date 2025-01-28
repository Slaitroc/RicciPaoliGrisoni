package click.studentandcompanies.notificationSystem.notificationUtils;
import click.studentandcompanies.notificationSystem.senders.*;

/**
 * This enum contains all the possible triggers for the notification system
 * Each trigger has a corresponding sender (strategy pattern) that will send the notification
 */
public enum NotificationTriggerType {
    MATCH_FOUND(new SendSelectionProcessInitiatedNotification()),
    NEW_COMMUNICATION(new SendNewCommunicationNotification()),
    INTERVIEW_EVALUATED(new SendInterviewEvaluatedNotification()),
    INTERNSHIP_CANCELLED(new SendInternshipCancelledNotification()),
    INTERVIEW_ANSWER_SENT(new SendInterviewAnswerNotification()),
    TERMINATE_COMMUNICATION(new SendTerminateCommunicationNotification()),
    INTERNSHIP_POSITION_OFFER_SENT(new SendInternshipPositionOfferNotification()),
    SPONTANEOUS_APPLICATION_ACCEPTED(new SendSelectionProcessInitiatedNotification()),
    SPONTANEOUS_APPLICATION_RECEIVED(new SendSpontaneousApplicationReceivedNotification()),
    SPONTANEOUS_APPLICATION_REJECTED(new SendSpontaneousApplicationRejectedNotification()),
    INTERNSHIP_POSITION_OFFER_ACCEPTED(new SendInternshipPositionOfferAcceptedNotification()),
    INTERNSHIP_POSITION_OFFER_REJECTED(new SendInternshipPositionOfferRejectedNotification()),
    TEST(new SendTest());

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
