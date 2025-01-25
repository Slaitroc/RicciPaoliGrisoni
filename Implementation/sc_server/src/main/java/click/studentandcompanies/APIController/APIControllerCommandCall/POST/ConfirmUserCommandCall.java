package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;

import java.util.Map;

public class ConfirmUserCommandCall implements APIControllerCommandCall<Object> {
    Map<String, Object> payload;

    public ConfirmUserCommandCall(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public Object execute() {
        System.out.println("User: " + payload.get("uuid") + " has been confirmed");
        return null;
    }
}
