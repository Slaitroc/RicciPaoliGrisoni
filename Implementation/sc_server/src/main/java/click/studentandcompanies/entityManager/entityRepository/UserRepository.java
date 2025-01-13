package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
* This class will contain all the methods that interact with the Table in the DB.
* JpaRepository already provides methods for common CRUD operations like save(), findById(), deleteById()
* Other, custom, method will be written here.
* The boilerplate of this class can be easy written by JPA BUDDY by clicking on new->SPRING JPA REPOSITORY
*/
public interface UserRepository extends JpaRepository<User, Integer> {
    //Derive method. This method are not implemented by default by the JPA, but are easy buildable and do not require writing SQL
    //These are call "derive method" because the SQL is derived by the name
    User findByEmail(String email);
    List<User> findAllByName(String name);

    //Custom method. Can't be build like the other, so the SQL must be written
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.name = :name")
    User findByEmailAndName(@Param("email") String email, @Param("name") String name);
}

