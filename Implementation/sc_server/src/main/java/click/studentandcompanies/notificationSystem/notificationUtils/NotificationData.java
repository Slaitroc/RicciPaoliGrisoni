package click.studentandcompanies.notificationSystem.notificationUtils;

import click.studentandcompanies.dto.DTO;

/**
 * This class is used to store the data that will be sent to the notification system
 * It contains the trigger type and the DTO containing all the relevant data to the notification
 */
public class NotificationData {
    private final NotificationTriggerType triggerType;
    private final DTO dto;

    /**
     * Constructor
     *
     * @param triggerType the trigger type of the notification
     * @param dto         the DTO containing all the relevant data to the notification
     */
    public NotificationData(NotificationTriggerType triggerType, DTO dto) {
        this.triggerType = triggerType;
        this.dto = dto;
    }

    /**
     * @return the trigger type of the notification
     */
    public NotificationTriggerType getTriggerType() {
        return triggerType;
    }

    /**
     * @return the DTO containing all the relevant data to the notification
     */
    public DTO getDTO() {
        return dto;
    }
}


