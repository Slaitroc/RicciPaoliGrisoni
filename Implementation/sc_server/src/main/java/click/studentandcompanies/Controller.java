package click.studentandcompanies;

import click.studentandcompanies.DTO.DTOCreator;
import click.studentandcompanies.DTO.DTO;
import click.studentandcompanies.DTO.DTOTypes;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    private final UserManager userManager;
    private final RecommendationProcess recommendationProcess;
    private final SubmissionManager submissionManager;
    //Inject the universityManager into the Controller (thanks to the @Autowired and @Service annotations)

    @Autowired
    public Controller(UserManager userManager, RecommendationProcess recommendationProcess, SubmissionManager submissionManager) {
        this.userManager = userManager;
        this.recommendationProcess = recommendationProcess;
        this.submissionManager = submissionManager;
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

    //Here we are returning a ResponseEntity with a list of DTOs.
    //Could also return a specific customized DTO with the list of Internships
    //but frontend libraries works fine with a list of JSON (says ChatGPT)
    @GetMapping("/sub/private/internships/{companyID}")
    public ResponseEntity<List<DTO>> getCompanyInternships(@PathVariable Integer companyID) {
        List<InternshipOffer> internshipOffers = submissionManager.getInternshipsByCompany(companyID);
        if (internshipOffers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DTO> dtos = new ArrayList<>();
        //For every InternshipOffer in the list, create a DTO and add it to the list of DTOs
        for (InternshipOffer offer : internshipOffers) {
            dtos.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer));
        }
        //Return the list of DTOs with a status code of 200 (OK)
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/sub/private/cv/{studentID}")
    public ResponseEntity<DTO> getStudentCV(@PathVariable Integer studentID) {
        Cv studentCV = submissionManager.getCvByStudent(studentID);
        if (studentCV == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //For every InternshipOffer in the list, create a DTO and add it to the list of DTOs

        //Return the list of DTOs with a status code of 200 (OK)
        return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, studentCV), HttpStatus.OK);
    }

    @GetMapping("/dto/test/")
    public ResponseEntity<DTO> testDTO() {
        System.out.println("testDTO called");
        Student student = userManager.getStudentById(1);
        System.out.println("Student is " + student);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("final return: " + DTOCreator.createDTO(DTOTypes.STUDENT, student));
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
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.CREATED);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/recommendations/private/{RecommendationID}/reject")
    public ResponseEntity<DTO> refuseRecommendation(@PathVariable Integer RecommendationID, @RequestBody Map<String, Object> payload) {
        try {
            Recommendation recommendation = recommendationProcess.refuseRecommendation(RecommendationID, (Integer) payload.get("userID"));
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.RECOMMENDATION_UPDATED_STATUS, recommendation), HttpStatus.CREATED);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
