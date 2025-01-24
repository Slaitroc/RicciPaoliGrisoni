package click.studentandcompanies.APIController;

import click.studentandcompanies.APIController.APIControllerCommandCall.GET.*;
import click.studentandcompanies.APIController.APIControllerCommandCall.POST.*;
import click.studentandcompanies.APIController.APIControllerCommandCall.PUT.*;
import click.studentandcompanies.dto.*;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.*;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanism;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.notificationSystem.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/application-api")
public class APIController {
    private final UserManager userManager;
    private final RecommendationProcess recommendationProcess;
    private final SubmissionManager submissionManager;
    private final FeedbackMechanism feedbackMechanism;
    private final CommunicationManager communicationManager;
    private final InterviewManager interviewManager;
    private final NotificationManager notificationManager;

    // Inject the Managers into the APIController (thanks to the @Autowired
    // and @Service annotations)
    @Autowired
    public APIController(UserManager userManager, RecommendationProcess recommendationProcess,
                         SubmissionManager submissionManager, FeedbackMechanism feedbackMechanism,
                         CommunicationManager communicationManager, InterviewManager interviewManager, NotificationManager notificationManager) {
        this.userManager = userManager;
        this.recommendationProcess = recommendationProcess;
        this.submissionManager = submissionManager;
        this.feedbackMechanism = feedbackMechanism;
        this.communicationManager = communicationManager;
        this.interviewManager = interviewManager;
        this.notificationManager = notificationManager;
    }

    @GetMapping("/private/test")
    public ResponseEntity<DTO> showTraefikHeader(@RequestHeader("X-Custom-Header") String customHeader) {
        return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, customHeader), HttpStatus.OK);

    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/university/{country}/count")
    public String getCountryCount(@PathVariable String country) {
        System.out.println("Getting the number of universities in " + country);
        long count = userManager.getNumberOfUniversitiesByCountry(country);
        if (count == 0) {
            if (userManager.areThereAnyUniversities()) {
                return "There are no universities in " + country;
            } else {
                return "There are no universities at all";
            }
        } else {
            return "There are " + count + " universities in " + country;
        }
    }

    // Here we are returning a ResponseEntity with a list of DTOs.
    // Could also return a specific customized DTO with the list of Internships
    // but frontend libraries works fine with a list of JSON (says ChatGPT)
    @GetMapping("/sub/private/internships/")
    @Operation(summary = "Request Company Internships", description = "Get a list of Internship Offers advertised by a specific company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Internships retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Company Internships found"),
            @ApiResponse(responseCode = "404", description = "Not Found, Company ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getCompanyInternships(@RequestParam("companyID") Integer companyID) {
        return new GetCompanyInternshipsCommandCall(companyID, submissionManager).execute();
    }

    @GetMapping("/sub/private/cv/")
    @Operation(summary = "Request Student CV", description = "Get the CV of a specific student.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, CV retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No CV found"),
            @ApiResponse(responseCode = "404", description = "Not Found, Student ID not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User not authorized to access this resource"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getStudentCV(@RequestParam("studentID") Integer studentID) {
        return new GetStudentCVCommandCall(studentID, submissionManager).execute();
    }

    // API called by student and companies when looking for their spontaneous
    // applications
    @GetMapping("/applications/private/spontaneous-applications/")
    @Operation(summary = "User requests the list of his Spontaneous Applications", description = "Get a list of Spontaneous Applications submitted by a specific student or submitted to a specific company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Spontaneous Applications retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Spontaneous Applications found"),
            @ApiResponse(responseCode = "400", description = "Bad request, User is not a student or a company"),
            @ApiResponse(responseCode = "404", description = "Not Found, User ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getSpontaneousApplications(@RequestParam("userID") Integer userID) {
        return new GetSpontaneousApplicationsCommandCall(userID, submissionManager).execute();
    }

    @GetMapping("/applications/private/get-matches/")
    @Operation(summary = "User requests the list of his Matches", description = "Get the list of the user Recommendations.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Matches retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Matches found for required user"),
            @ApiResponse(responseCode = "400", description = "Bad request, User is not a student or a company"),
            @ApiResponse(responseCode = "404", description = "Not Found, User ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getRecommendations(@RequestParam("userID") Integer userID) {
        return new GetRecommendationsCommandCall(userID, recommendationProcess).execute();
    }

    @GetMapping("/comm/private/communications/")
    @Operation(summary = "User userID requests the communication commID", description = "Get the communication commID for the user userID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Communication retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Communication found"),
            @ApiResponse(responseCode = "404", description = "Not Found, userID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getAllUserCommunications(@RequestParam("userID") Integer userID) {
        return new GetAllUserCommunicationsCommandCall(userID, communicationManager).execute();
    }

    // TODO: Where is done the check if the user is retrieving one of his
    // communications? There is no body in the GET request
    @GetMapping("/comm/private/communication/{userID}/")
    @Operation(summary = "User userID requests the communication commID", description = "Get the communication commID for the user userID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Communication retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not participating in the communication"),
            @ApiResponse(responseCode = "404", description = "Not Found, Communication ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getCommunication(@PathVariable Integer userID, @RequestParam("commID") Integer commID) {
        return new GetCommunicationCommandCall(commID, userID, communicationManager).execute();
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

    // Because this is a post request, the data is sent in the body of the request
    // The @RequestBody annotation tells Spring to convert the body of the request
    // into a generic Object
    // Can be the same url as the get request because the method requested is
    // different
    @PostMapping("/dto/test/")
    public HttpStatus testDTO(@RequestBody Object dto) {
        System.out.println("testDTO called");
        System.out.println("DTO is " + dto);
        return HttpStatus.OK;
    }

    // Please notice that because we are not yet using the firebase key to uniquely
    // identify the user, at the moment we have an
    // overlap of userID between students and companies. This will be fixed in the
    // future.
    // At the moment if they have the same ID, they are the same user (which is not
    // the case in the real world)

    // The payload is a map with the userID
    @PostMapping("/recommendations/private/{RecommendationID}/accept")
    @Operation(summary = "Accept recommendation", description = "The payload is a map with the 'userID' used to accept a recommendation.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recommendation accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> acceptRecommendation(@PathVariable Integer RecommendationID,
            @RequestBody Map<String, Object> payload) {
        return new AcceptRecommendationCommandCall(RecommendationID, recommendationProcess, notificationManager, payload).execute();
    }

    // The payload is a map with the userID
    @PostMapping("/recommendations/private/{RecommendationID}/reject")
    @Operation(summary = "Reject recommendation", description = "Reject a recommendation by providing the 'userID' in the payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recommendation rejected successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> refuseRecommendation(@PathVariable Integer RecommendationID,
            @RequestBody Map<String, Object> payload) {
        return new RefuseRecommendationCommandCall(RecommendationID, recommendationProcess, payload).execute();
    }

    @PostMapping("/sub/private/update-cv")
    @Operation(summary = "Update student's CV", description = "The payload is a map with the 'student_id', 'update_time', and other optional fields used to update a student's CV.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CV updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "CV not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateCV(@RequestBody Map<String, Object> payload) {
        return new UpdateCVCommandCall(payload, submissionManager, recommendationProcess).execute();
    }

    // The payload is a map with the "company_id", optionally the
    // "internshipOffer_id" if we are UPDATING an existing offer (the backend will
    // check if the company is the owner of the offer)
    // title, description, compensation, location, start_date, end_date,
    // duration_hours and any other (optional) field
    @PostMapping("/sub/private/update-offer")
    @Operation(summary = "Update internship offer", description = "The payload is a map with the 'company_id', optionally the 'internshipOffer_id' if we are UPDATING an existing offer (the backend will check if the company is the owner of the offer), 'title', 'description', 'compensation', 'location', 'start_date', 'end_date', 'duration_hours', and any other (optional) field.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Internship offer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Internship offer not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateOffer(@RequestBody Map<String, Object> payload) {
        return new UpdateInternshipOfferCommandCall(payload, submissionManager, recommendationProcess).execute();
    }

    @PutMapping("/feedback/private/{RecommendationID}/submit")
    @Operation(summary = "Submit feedback", description = "The payload is a map with a 'student_id' OR 'company_id' (ownership is checked by the backend), 'rating', and any other optional field.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Feedback not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> submitFeedback(@PathVariable Integer RecommendationID,
            @RequestBody Map<String, Object> payload) {
        return new SubmitFeedbackCommandCall(RecommendationID, payload, feedbackMechanism).execute();
    }

    @PostMapping("/sub/private/application/{InternshipOfferID}/submit")
    @Operation(summary = "Submit spontaneous application", description = "The payload is a map with the 'student_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Spontaneous application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> submitSpontaneousApplication(@PathVariable Integer InternshipOfferID,
            @RequestBody Map<String, Object> payload) {
        return new SubmitSpontaneousApplicationCommandCall(InternshipOfferID, payload, submissionManager, notificationManager).execute();
    }

    @PostMapping("/interviews/private/send-answer/{InterviewID}")
    @Operation(summary = "Send interview answer", description = "payload will contain the 'student_id' and the 'answer'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview answer sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewAnswer(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload) {
        return new SendInterviewAnswerCommandCall(InterviewID, payload, interviewManager, notificationManager).execute();
    }

    @PostMapping("/interviews/private/send-interview/{InterviewID}")
    @Operation(summary = "Send interview", description = "payload will contain the 'questions' and the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterview(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload) {
        return new SendInterviewCommandCall(InterviewID, payload, interviewManager).execute();
    }

    @PostMapping("/interviews/private/save-template-interview/{InterviewID}")
    @Operation(summary = "Save interview template", description = "payload will contain the 'questions' and the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview template saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> saveInterviewTemplate(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload) {
        return new SaveInterviewTemplateCommandCall(InterviewID, interviewManager, payload).execute();
    }

    @PostMapping("interviews/private/{TemplateInterviewID}/send-template-interview/{InterviewID}")
    @Operation(summary = "Send interview template", description = "payload will contain the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview template sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview template or Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewTemplate(@PathVariable Integer TemplateInterviewID,
            @PathVariable Integer InterviewID, @RequestBody Map<String, Object> payload) {
        return new SendInterviewTemplateCommandCall(interviewManager, InterviewID, TemplateInterviewID, payload)
                .execute();
    }

    @PostMapping("interviews/private/evaluate-interview/{InterviewID}")
    @Operation(summary = "Evaluate interview", description = "payload will contain the 'company_id' the 'evaluation' (a integer from 1 to 5) and the 'status' (failed or passed)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview evaluated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> evaluateInterview(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload) {
        return new EvaluateInterviewCommandCall(interviewManager, notificationManager, InterviewID, payload).execute();
    }

    @PostMapping("comm/private/create")
    @Operation(summary = "Create communication", description = "payload will contain the 'student_id', 'internshipOffer_id', 'university_id', 'title', 'content', 'communication_type' (communication, complaint)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Communication created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Sender or Receiver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> createCommunication(@RequestBody Map<String, Object> payload) {
        return new CreateCommunicationCommandCall(communicationManager, notificationManager, payload).execute();
    }

    @PostMapping("/sub/private/close-internship/")
    @Operation(summary = "Close internship", description = "payload will contain the company_id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, Internship closed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not the owner of the internship"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> closeInternship(@RequestParam("internshipID") Integer internshipID, @RequestBody Map<String, Object> payload) {
        //If the Internship does not exist or does not exist any pending application, the list will be empty. no exception will be thrown
        return new CloseInternshipOfferCommandCall(internshipID, payload, submissionManager, userManager, recommendationProcess, notificationManager).execute();
    }

    @PostMapping("/comm/private/{commID}/terminate")
    @Operation(summary = "Close communication", description = "payload will contain the 'university_id")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Communication created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Unauthorized or not found University"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> terminateCommunication(@PathVariable Integer commID,
            @RequestBody Map<String, Object> payload) {
        return new TerminateCommunicationCommandCall(communicationManager, commID, payload, notificationManager).execute();
    }

    @PostMapping("interviews/private/{InterviewID}/send-int-pos-off")
    @Operation(summary = "Send interview position offer", description = "payload will contain the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview position offer sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewPositionOffer(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload) {
        return new SendInternshipPositionOfferCommandCall(InterviewID, payload, interviewManager, userManager, notificationManager).execute();
    }

    @PostMapping("/interview/private/accept-int-pos-off/")
    @Operation(summary = "Close internship", description = "payload will contain the user_id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, Internship closed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not the owner of the internship"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> acceptInternshipPositionOffer(@RequestParam("intPosOffID") Integer intPosOffID,
            @RequestBody Map<String, Object> payload) {
        return new AcceptInternshipPositionOfferCommandCall(intPosOffID, payload, interviewManager).execute();
    }

    //todo: redo the all fucking thing
    @PutMapping("/acc/private/send-user-data")
    public Object sendUserData(@RequestBody Map<String, Object> payload) {
        return new SendUserDataCommandCall(payload).execute();
    }
}
