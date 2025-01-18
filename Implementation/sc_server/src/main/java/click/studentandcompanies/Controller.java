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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;

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

    //The payload is a map with the userID
    @PostMapping("/recommendations/private/{RecommendationID}/accept")
    @Operation(
        summary = "Accept recommendation",
        description = "The payload is a map with the 'userID' used to accept a recommendation."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recommendation accepted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Recommendation not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    //The payload is a map with the userID
    @PostMapping("/recommendations/private/{RecommendationID}/reject")
    @Operation (
        summary = "Reject recommendation",
        description = "Reject a recommendation by providing the 'userID' in the payload."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recommendation rejected successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Recommendation not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @PostMapping("/sub/private/update-cv")
    @RequestBody
    @Operation(
        summary = "Update student's CV",
        description = "The payload is a map with the 'student_id', 'update_time', and other optional fields used to update a student's CV."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CV updated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "CV not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateCV(
        @Parameter(
            description = "Payload containing the 'student_id' (required), 'update_time', and any other optional fields",
            required = true
        )
        @RequestBody Map<String, Object> payload){
        try{
            Cv cv = submissionManager.updateCvCall(payload);
            //todo: start the recommendation process
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, cv), HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //The payload is a map with the "company_id", optionally the "internshipOffer_id" if we are UPDATING an existing offer (the backend will check if the company is the owner of the offer)
    //title, description, compensation, location, start_date, end_date, duration_hours and any other (optional) field
    @PostMapping("/sub/private/update-offer")
    @Operation(
        summary = "Update internship offer",
        description = "The payload is a map with the 'company_id', optionally the 'internshipOffer_id' if we are UPDATING an existing offer (the backend will check if the company is the owner of the offer), 'title', 'description', 'compensation', 'location', 'start_date', 'end_date', 'duration_hours', and any other (optional) field."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Internship offer updated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Internship offer not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateOffer(@RequestBody Map<String, Object> payload) {
        try {
            InternshipOffer offer = submissionManager.updateInternshipOfferCall(payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer), HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (IllegalAccessException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
