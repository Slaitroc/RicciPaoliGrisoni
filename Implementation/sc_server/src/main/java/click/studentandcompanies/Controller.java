package click.studentandcompanies;

import click.studentandcompanies.DTO.DTOCreator;
import click.studentandcompanies.DTO.DTO;
import click.studentandcompanies.DTO.DTOTypes;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.RecommendationProcess;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    private final UserManager userManager;
    private final RecommendationProcess recommendationProcess;
    //Inject the universityManager into the Controller (thanks to the @Autowired and @Service annotations)
    @Autowired
    public Controller(UserManager userManager, RecommendationProcess recommendationProcess) {
        this.userManager = userManager;
        this.recommendationProcess = recommendationProcess;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
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

    //Please notice that because we are not yet using the firebase key to uniquely identify the user, at the moment we have an
    //overlap of userID between students and companies. This will be fixed in the future.
    //At the moment if they have the same ID, they are the same user (which is not the case in the real world)
    @PostMapping("/recommendations/private/{RecommendationID}/accept")
    public ResponseEntity<DTO> acceptRecommendation(@PathVariable Integer RecommendationID, @RequestBody Map<String, Object> payload) {
        try {
            Recommendation recommendation = recommendationProcess.acceptRecommendation(RecommendationID, (Integer) payload.get("userID"));
            if(recommendation.getStatus() == RecommendationStatusEnum.acceptedMatch){
                //todo: call the notification service
                //todo: call feedback service
            }
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.OK);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recommendations/private/{RecommendationID}/reject")
    public ResponseEntity<DTO> refuseRecommendation(@PathVariable Integer RecommendationID, @RequestBody Map<String, Object> payload) {
        try {
            Recommendation recommendation = recommendationProcess.refuseRecommendation(RecommendationID, (Integer) payload.get("userID"));
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.OK);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
