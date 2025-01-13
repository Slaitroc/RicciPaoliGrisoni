package click.studentandcompanies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import click.studentandcompanies.entityManager.universityManager;
@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
    private final universityManager universityManager;
    //Inject the universityManager into the Controller (thanks to the @Autowired and @Service annotations)
    @Autowired
    public Controller(universityManager universityManager) {
        this.universityManager = universityManager;
    }
    @GetMapping("/{country}/count")
    public String getCountryCount(@PathVariable String country) {
        System.out.println("Getting the number of universities in " + country);
        long count = universityManager.getNumberOfUniversitiesByCountry(country);
        if (count==0){
            if(universityManager.areThereAnyUniversities()){
                return "There are no universities in " + country;
            } else {
                return "There are no universities at all";
            }
        }else{
            return "There are " + count + " universities in " + country;
        }
    }
}
