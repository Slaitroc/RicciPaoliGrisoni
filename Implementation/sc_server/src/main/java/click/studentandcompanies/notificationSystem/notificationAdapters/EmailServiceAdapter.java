package click.studentandcompanies.notificationSystem.notificationAdapters;

import click.studentandcompanies.notificationSystem.notificationUtils.EmailContent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceAdapter {
    public void sendEmail(List<String> emails, EmailContent content) {
        // todo implement the actual firebase email sending
        System.out.println("Sending email to " + emails.size() + " recipients:\n" + emails);
        System.out.println("Object: " + content.subject() + "\n" + content.body() +  "\n\n");
    }
}

