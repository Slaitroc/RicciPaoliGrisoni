package click.studentandcompanies.APIController.APIControllerCommandCall.PUT;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SendUserDataCommandCall implements APIControllerCommandCall<Object> {
    Map<String, Object> payload;

    public SendUserDataCommandCall(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public Object execute() {
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue().toString());
        }
        return null;
    }

}
