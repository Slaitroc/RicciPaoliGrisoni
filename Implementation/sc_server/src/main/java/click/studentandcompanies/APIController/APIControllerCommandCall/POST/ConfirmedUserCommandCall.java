package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;

import java.util.Map;

public class ConfirmedUserCommandCall implements APIControllerCommandCall<Object> {
    Map<String, Object> payload;

    public ConfirmedUserCommandCall(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public Object execute() {
        System.out.println("User: " + payload.get("user_id") + " has been confirmed");
        return null;
    }
}
