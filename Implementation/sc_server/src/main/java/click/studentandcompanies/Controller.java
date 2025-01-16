package click.studentandcompanies;

import click.studentandcompanies.DTO.DTOCreator;
import click.studentandcompanies.DTO.DTO;
import click.studentandcompanies.DTO.DTOTypes;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
    private final UserManager userManager;
    //Inject the universityManager into the Controller (thanks to the @Autowired and @Service annotations)
    @Autowired
    public Controller(UserManager userManager) {
        this.userManager = userManager;
    }
    @GetMapping("/university/{country}/count")
    public String getCountryCount(@PathVariable String country) {
        System.out.println("Getting the number of universities in " + country);
        long count = userManager.getNumberOfUniversitiesByCountry(country);
        if (count==0){
            if(userManager.areThereAnyUniversities()){
                return "There are no universities in " + country;
            } else {
                return "There are no universities at all";
            }
        }else{
            return "There are " + count + " universities in " + country;
        }
    }

    @GetMapping("/dto/test/")
    public ResponseEntity<DTO> testDTO() {
        System.out.println("testDTO called");
        Student student = userManager.getStudentById(1);
        System.out.println("Student is " + student);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.STUDENT, student), HttpStatus.OK);
    }

    //Because this is a post request, the data is sent in the body of the request
    //The @RequestBody annotation tells Spring to convert the body of the request into a generic Object
    //Can be the same url as the get request because the method requested is different
    @PostMapping("/dto/test/")
    public HttpStatus testDTO(@RequestBody Object dto) {
        System.out.println("testDTO called");
        System.out.println("DTO is " + dto);
        return HttpStatus.OK;
    }

}
