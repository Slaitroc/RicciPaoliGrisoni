package click.studentandcompanies.entityManager;
import click.studentandcompanies.entityManager.entityRepository.UserRepository;
import org.springframework.stereotype.Service;
import click.studentandcompanies.entity.User;

import java.util.List;

//@Service annotation is used to mark the class as a service provider. It can be used with classes that contain business logic.
//It is not mandatory to use this annotation, but it helps to keep the code clean and organized. It is a specialization of the @Component annotation.
@Service
public class userManager {
    private final UserRepository userRepository;

    public userManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //CRUD operations, all of them are already implemented by the JpaRepository
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByEmailAndName(String email, String name) {
        return userRepository.findByEmailAndName(email, name);
    }

    /*
    * This method is not implemented by default by the JPA, but is easy buildable and do not require writing SQL.
    * Because of the naming convention, the method is automatically implemented by the JPA, and the SQL is generated.
    * There is no need to define this method in the UserRepository interface, but it helps to keep the code clean and organized.
    * */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsersByName(String name) {
        return userRepository.findAllByName(name);
    }
}
