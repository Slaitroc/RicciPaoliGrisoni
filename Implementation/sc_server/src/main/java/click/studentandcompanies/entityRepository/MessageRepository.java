package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message getMessageById(Integer id);

    List<Message> getMessagesByCommunication_Id(Integer communicationId);

}
