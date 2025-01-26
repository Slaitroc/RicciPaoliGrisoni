package click.studentandcompanies.notificationSystem;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;

import java.util.Map;

public class SendNotificationTokenCommandCall implements APIControllerCommandCall<Void> {
    Map<String, Object> payload;

    public SendNotificationTokenCommandCall(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public Void execute() {
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue().toString());
        }
        return null;
    }
}
