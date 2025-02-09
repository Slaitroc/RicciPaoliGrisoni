package click.studentandcompanies.APIController;

import click.studentandcompanies.APIController.APIControllerCommandCall.GET.*;
import click.studentandcompanies.APIController.APIControllerCommandCall.POST.*;
import click.studentandcompanies.APIController.APIControllerCommandCall.PUT.*;
import click.studentandcompanies.dto.*;
import click.studentandcompanies.entityManager.*;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanism;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.utils.GetUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private final AccountManager accountManager;

    // Inject the Managers into the APIController (thanks to the @Autowired
    // and @Service annotations)
    @Autowired
    public APIController(UserManager userManager, RecommendationProcess recommendationProcess,
            SubmissionManager submissionManager, FeedbackMechanism feedbackMechanism,
            CommunicationManager communicationManager, InterviewManager interviewManager,
            NotificationManager notificationManager, AccountManager accountManager) {
        this.userManager = userManager;
        this.recommendationProcess = recommendationProcess;
        this.submissionManager = submissionManager;
        this.feedbackMechanism = feedbackMechanism;
        this.communicationManager = communicationManager;
        this.interviewManager = interviewManager;
        this.notificationManager = notificationManager;
        this.accountManager = accountManager;
    }

    @Deprecated
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }


    //_________________________ acc __________________________

    //Public call, no token needed
    @GetMapping("/acc/get-universities")
    @Operation(summary = "Get Universities", description = "Get the list of all the registered Universities.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Universities retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Universities found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getUniversities() {
        return new GetUniversitiesMapCommandCall(accountManager).execute();
    }


    //_________________________ acc/private _________________________

    @GetMapping("/acc/private/get-user-data")
    @Operation(summary = "Get user data", description = "Get the data of the user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, User data retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found, User ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getUserData(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetUserDataCommandCall(userID, accountManager).execute();
    }

    //The body will contain the 'name', 'email', 'country', 'userType' and any other field relevant to the user type
    @PutMapping("/acc/private/send-user-data")
    @Operation(summary = "Send user data", description = "Send the data of the user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User data sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendUserData(@RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String uuid = GetUuid.getUuid(token);
        payload.put("user_id", uuid);
        return new SendUserDataCommandCall(payload, accountManager).execute();
    }

    @PostMapping("/acc/private/confirm-user")
    @Operation(summary = "Confirm user", description = "Confirm create a new entry in the database for the user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data"),
            @ApiResponse(responseCode = "404", description = "Not Found, User ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO>  confirmedUser(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new ConfirmUserCommandCall(userID, accountManager).execute();
    }


    //_________________________ sub/private _____________________________________

    //_________________________ sub/private/internship __________________________

    @GetMapping("/sub/private/internship/get-internship-offer")
    @Operation(summary = "Request Internships", description = "Get a list of all Internship Offers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Internships retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Internships found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User id not found, user should probably log in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getInternships(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetInternshipsCommandCall(userID, submissionManager).execute();
    }



    @GetMapping("/sub/private/internship/{companyID}/get-company-internships")
    @Operation(summary = "Request Company Internships", description = "Get a list of Internship Offers advertised by a specific company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Internships retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Company Internships found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User does not exists"),
            @ApiResponse(responseCode = "404", description = "Not Found, Company ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getCompanyInternships(@RequestHeader("Authorization") String token, @PathVariable("companyID") String companyID) {
        String userID = GetUuid.getUuid(token);
        return new GetCompanyInternshipsCommandCall(companyID, userID, submissionManager).execute();
    }

    @GetMapping("/sub/private/internship/{internshipID}/get-internship")
    @Operation(summary = "Request Internship", description = "Get the Internship Offer with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Internship retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Internship found with the specified ID"),
            @ApiResponse(responseCode = "404", description = "Internship ID not found in the request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getInternship(@PathVariable("internshipID") Integer internshipID, @RequestHeader("Authorization") String token) {
        return new GetSpecificInternshipCommandCall(internshipID, submissionManager).execute();
    }


    // The boyd will contain:
    // "internshipOffer_id" if we are UPDATING an existing offer (the backend will check if the company is the owner of the offer)
    // title, description, compensation, location, start_date, end_date,
    // duration_hours and any other (optional) field
    @PostMapping("/sub/private/internship/update-my-offer")
    @Operation(summary = "Update internship offer", description = "Update or create the database entry of a company's Internship Offer.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Internship offer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Internship offer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateOffer(@RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        return new UpdateInternshipOfferCommandCall(payload, submissionManager, recommendationProcess).execute();
    }

    //ACTUALLY, THIS IS NOT IMPLEMENTED YET, DO NOT USE!
    @Deprecated
    @PostMapping("/sub/private/internship/{internshipID}/close-internship")
    @Operation(summary = "Close internship", description = "payload will contain the company_id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, Internship closed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not the owner of the internship"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> closeInternship(@PathVariable("internshipID") Integer internshipID,
                                               @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        // If the Internship does not exist or does not exist any pending application,
        // the list will be empty. no exception will be thrown
        return new CloseInternshipOfferCommandCall(internshipID, payload, submissionManager, userManager,
                recommendationProcess, notificationManager).execute();
    }


    //_________________________ sub/private/cv __________________________

    @GetMapping("/sub/private/cv/{studentID}/get-student-cv")
    @Operation(summary = "Request Student CV", description = "Get the CV of a specific student.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, CV retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No CV found for the specified student"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User not logged in"),
            @ApiResponse(responseCode = "404", description = "Not Found, Student ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getStudentCV(@PathVariable("studentID") String studentID, @RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetStudentCVCommandCall(studentID, userID, submissionManager).execute();
    }

    //The payload is a map with 'certifications', 'skills', 'education', 'work_experiences', 'project'
    @PostMapping("/sub/private/cv/update-my-cv")
    @Operation(summary = "Update student's CV", description = "Create or update the database entry of a student's CV.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CV updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "CV not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> updateCV(@RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String student_id = GetUuid.getUuid(token);
        payload.put("studentID", student_id);
        return new UpdateCVCommandCall(payload, submissionManager, recommendationProcess).execute();
    }


    //_________________________ sub/private/application __________________________

    // API called by student and companies when looking for their spontaneous applications
    @GetMapping("/sub/private/application/get-my-applications")
    @Operation(summary = "User requests the list of his Spontaneous Applications", description = "Get a list of Spontaneous Applications submitted by a specific student or submitted to a specific company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Spontaneous Applications retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Spontaneous Applications found for the required user"),
            @ApiResponse(responseCode = "400", description = "Bad request, User is not specified or not a student or a company"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getSpontaneousApplications(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetSpontaneousApplicationsCommandCall(userID, submissionManager).execute();
    }

    //No body needed, the student_id is extracted from the token
    @PostMapping("/sub/private/application/{InternshipOfferID}/submit")
    @Operation(summary = "Submit spontaneous application", description = "Submits a spontaneous application to a specific Internship Offer.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Spontaneous application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> submitSpontaneousApplication(@PathVariable Integer InternshipOfferID, @RequestHeader("Authorization") String token) {
        String student_id = GetUuid.getUuid(token);
        return new SubmitSpontaneousApplicationCommandCall(InternshipOfferID, student_id, submissionManager,
                notificationManager).execute();
    }

    //No body needed, the company_id is extracted from the token
    @PostMapping("/sub/private/application/{SpontaneousApplicationID}/accept")
    @Operation(summary = "Accept Spontaneous Application", description = "Accept a Spontaneous Application, create a toBeSubmitted interview.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SpontaneousApplication accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, the application is already accepted"),
            @ApiResponse(responseCode = "404", description = "SpontaneousApplication not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> acceptSpontaneousApplication(@PathVariable Integer SpontaneousApplicationID, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", company_id);
        return new AcceptSpontaneousApplicationCommandCall(SpontaneousApplicationID, submissionManager, notificationManager, payload).execute();
    }

    //No body needed, the company_id is extracted from the token
    @PostMapping("/sub/private/application/{SpontaneousApplicationID}/reject")
    @Operation(summary = "Reject Spontaneous Application", description = "Reject a Spontaneous Application, changing the status to 'rejected'.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SpontaneousApplication rejected successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, the application is already rejected"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "SpontaneousApplication not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> rejectSpontaneousApplication(@PathVariable Integer SpontaneousApplicationID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", user_id);
        return new RejectSpontaneousApplicationCommandCall(SpontaneousApplicationID, submissionManager, notificationManager, payload).execute();
    }

    //_________________________ recommendation/private __________________________

    @GetMapping("/recommendation/private/get-my-matches")
    @Operation(summary = "User requests the list of his Matches", description = "Get the list of the user Recommendations.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Matches retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Matches found for required user"),
            @ApiResponse(responseCode = "400", description = "Bad request, User is not a student or a company"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getRecommendations(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetRecommendationsCommandCall(userID, recommendationProcess).execute();
    }


    //No body needed, the userID is extracted from the token
    @PostMapping("/recommendation/private/{RecommendationID}/accept")
    @Operation(summary = "Accept recommendation", description = "A participant (student or company) accepts a recommendation. If both participants accept, a new interview is created.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recommendation accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> acceptRecommendation(@PathVariable Integer RecommendationID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", user_id);
        return new AcceptRecommendationCommandCall(RecommendationID, recommendationProcess, notificationManager, payload).execute();
    }


    //No body needed, the userID is extracted from the token
    @PostMapping("/recommendation/private/{RecommendationID}/reject")
    @Operation(summary = "Reject recommendation", description = "A participant (student or company) rejects a recommendation. No interview can be created from this match.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recommendation rejected successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> refuseRecommendation(@PathVariable Integer RecommendationID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", user_id);
        return new RejectRecommendationCommandCall(RecommendationID, recommendationProcess, payload).execute();
    }


    //_________________________ comm/private __________________________

    @GetMapping("/comm/private/get-my-comm")
    @Operation(summary = "User userID requests the communication commID", description = "Get the communication commID for the user userID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Communication retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content, No Communication found for the required user"),
            @ApiResponse(responseCode = "404", description = "Not Found, userID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getAllUserCommunications(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetAllUserCommunicationsCommandCall(userID, communicationManager).execute();
    }


    @GetMapping("/comm/private/{commID}/get-messages")
    @Operation(summary = "Get communication messages", description = "Get the messages of a specific communication.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Messages retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not participating in the communication"),
            @ApiResponse(responseCode = "404", description = "Not Found, Communication ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getCommunication(@RequestHeader("Authorization") String token, @PathVariable("commID") Integer commID) {
        String userID = GetUuid.getUuid(token);
        return new GetCommunicationMessagesCommandCall(commID, userID, communicationManager).execute();
    }

    //The payload will contain 'internshipPosOfferID', 'title', 'content', 'communicationType'
    @PostMapping("/comm/private/create-comm")
    @Operation(summary = "Create communication", description = "Create a new communication, send notification to the other participant and the university.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Communication created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Sender or Receiver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> createCommunication(@RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        payload.put("user_id", user_id);
        return new CreateCommunicationCommandCall(communicationManager, notificationManager, payload).execute();
    }

    //The payload will contain 'internshipOffer_id', 'university_id', 'title', 'content', 'communication_type' (communication, complaint)
    @PostMapping("/comm/private/{commID}/send-messages")
    @Operation(summary = "Create communication", description = "Send a new message to the specified communication. Notification will be sent to the other involved users.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Message created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, not all fields are present"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not participating in the communication"),
            @ApiResponse(responseCode = "404", description = "Sender or Communication not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> createCommunicationMessage(@PathVariable("commID") Integer commID, @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new CreateCommunicationMessageCommandCall(communicationManager, userID, commID, payload, notificationManager).execute();
    }

    //No body needed, the university_id is extracted from the token
    @PostMapping("/comm/private/{commID}/terminate")
    @Operation(summary = "Close communication", description = "Close the communication with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Communication created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Unauthorized or not found University"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> terminateCommunication(@PathVariable Integer commID, @RequestHeader("Authorization") String token) {
        String university_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("university_id", university_id);
        return new TerminateCommunicationCommandCall(communicationManager, commID, payload, notificationManager)
                .execute();
    }


    //_________________________ feedback/private __________________________

    //The body will contain the 'rating' of the feedback
    @PutMapping("/feedback/private/{RecommendationID}/submit")
    @Operation(summary = "Submit feedback", description = "Submit a feedback to the platform from a student to a company.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, missing rating or the user is not a student or a company"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> submitFeedback(@PathVariable Integer RecommendationID,
            @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        payload.put("user_id", user_id);
        return new SubmitFeedbackCommandCall(RecommendationID, payload, feedbackMechanism).execute();
    }


    //_________________________ interview/private __________________________

    @GetMapping("/interview/private/get-my-interviews")
    @Operation(summary = "User requests the list of his Interviews", description = "Get the list of the user Interviews.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok, Interviews retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, User is not a student or a company"),
            @ApiResponse(responseCode = "404", description = "No Interviews found for this user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getInterviews(@RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetInterviewsCommandCall(userID, interviewManager).execute();
    }

    @GetMapping("/interview/private/{interviewID}/get-interview")
    @Operation(summary = "Get interview", description = "Get the interview with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, user is not the owner of the interview"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getInterview(@PathVariable Integer interviewID, @RequestHeader("Authorization") String token) {
        String userID = GetUuid.getUuid(token);
        return new GetInterviewCommandCall(userID, interviewID, interviewManager).execute();
    }

    @GetMapping("/interview/private/get-match-not-interviewed")
    @Operation(summary = "Get match not interviewed", description = "Get the list of students matched with the company that have not assigned an interview yet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Match retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Students not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getMatchNotInterviewed(@RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        return new GetMatchNotInterviewedCommandCall(interviewManager, company_id).execute();
    }

    @GetMapping("/interview/private/get-my-templates")
    @Operation(summary = "Get interview", description = "Return all the template of the specified company'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getTemplateInterviews(@RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        return new GetInterviewTemplatesCommandCall(interviewManager, company_id).execute();
    }

    @GetMapping("/interview/private/{templateID}/get-template")
    @Operation(summary = "Get interview template", description = "Return the specified interview template")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview template retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data or the user is not the owner of the template"),
            @ApiResponse(responseCode = "404", description = "Interview template not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getTemplateInterview(@PathVariable Integer templateID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        return new GetInterviewTemplateCommandCall(interviewManager, templateID, user_id).execute();
    }

    @GetMapping("/interview/private/{quizID}/get-quiz")
    @Operation(summary = "Get interview quiz", description = "Return the specified interview quiz")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview quiz retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data or the user is not the owner of the quiz"),
            @ApiResponse(responseCode = "404", description = "Interview quiz not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> getQuizInterview(@PathVariable Integer quizID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        return new GetInterviewQuizCommandCall(interviewManager, quizID, user_id).execute();
    }

    //No body needed, the user_id is extracted from the token
    @GetMapping("/interview/private/get-my-int-pos-off")
    @Operation(summary = "Get internship position offer", description = "Return all the internship position offers of the specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Internship positions offer retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No internship position offer found for the specified user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getInternshipPositionOffers(@RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        return new GetInternshipPositionOffersCommandCall(interviewManager, user_id).execute();
    }

    //No body needed, the user_idis extracted from the token
    @GetMapping("/interview/private/get-my-acc-int-pos-off")
    @Operation(summary = "Get accepted internship position offer", description = "Return all the accepted internship position offers of the specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accepted internship positions offer retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No accepted internship position offer found for the specified user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DTO>> getAcceptedInternshipPositionOffers(@RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        return new GetAcceptedInternshipPositionOffersCommandCall(interviewManager, user_id).execute();
    }

    //The body will contain the 5 questions: 'question1', 'question2', 'question3', 'question4', 'question5', 'question6'
    @PostMapping("/interview/private/{InterviewID}/send-interview")
    @Operation(summary = "Send interview", description = "Create a new interview template with the specified questions. Assign the template to the specified interview.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "204", description = "Interview not found for the specified ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterview(@PathVariable Integer InterviewID,
                                             @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        return new SendInterviewCommandCall(InterviewID, payload, interviewManager, notificationManager).execute();
    }

    //The body will contain the 'answer1', 'answer2', 'answer3', 'answer4', 'answer5', 'answer6'
    @PostMapping("/interview/private/{InterviewID}/send-answer")
    @Operation(summary = "Send interview answer", description = "Create a new interview quiz with the given answers. Assign the quiz to the specified interview.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview answer sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewAnswer(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String student_id = GetUuid.getUuid(token);
        payload.put("student_id", student_id);
        return new SendInterviewAnswerCommandCall(InterviewID, payload, interviewManager, notificationManager)
                .execute();
    }

    //ACTUALLY, THIS IS NOT IMPLEMENTED YET, DO NOT USE!
    @Deprecated
    @PostMapping("/interview/private/{InterviewID}/save-template")
    @Operation(summary = "Save interview template", description = "payload will contain the 'questions' and the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview template saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> saveInterviewTemplate(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        return new SaveInterviewTemplateCommandCall(InterviewID, interviewManager, payload).execute();
    }

    //No body needed, the company_id is extracted from the token
    @PostMapping("/interview/private/{TemplateInterviewID}/send-template/{InterviewID}")
    @Operation(summary = "Send interview template", description = "Assign the specified interview template to the specified interview.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview template sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview template or Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewTemplate(@PathVariable Integer TemplateInterviewID,
            @PathVariable Integer InterviewID, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        return new SendInterviewTemplateCommandCall(interviewManager, InterviewID, TemplateInterviewID, company_id, notificationManager)
                .execute();
    }

    //The body will contain the 'evaluation' (a integer from 1 to 5)
    @PostMapping("/interview/private/{InterviewID}/evaluate-interview")
    @Operation(summary = "Evaluate interview", description = "payload will the 'evaluation' (a integer from 1 to 5). A grade of 1 or 2 means that the student has failed")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview evaluated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> evaluateInterview(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        return new EvaluateInterviewCommandCall(interviewManager, notificationManager, InterviewID, payload).execute();
    }

    //Interview position offer is automatically created when a interview is passed. DO NOT USE THIS API CALL
    @Deprecated
    @PostMapping("/interview/private/{InterviewID}/send-int-pos-off")
    @Operation(summary = "Send interview position offer", description = "payload will contain the 'company_id'")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interview position offer sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Student is not the owner of the Interview position offer"),
            @ApiResponse(responseCode = "404", description = "Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> sendInterviewPositionOffer(@PathVariable Integer InterviewID,
            @RequestBody Map<String, Object> payload, @RequestHeader("Authorization") String token) {
        String company_id = GetUuid.getUuid(token);
        payload.put("company_id", company_id);
        return new SendInternshipPositionOfferCommandCall(InterviewID, payload, interviewManager, userManager,
                notificationManager).execute();
    }

    //No body needed, the student_id is extracted from the token
    @PostMapping("/interview/private/{intPosOffID}/accept-int-pos-off")
    @Operation(summary = "Close internship", description = "Accept the specified internship position offer, a notification will be sent to the company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, Internship closed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not the owner of the internship"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> acceptInternshipPositionOffer(@PathVariable("intPosOffID") Integer intPosOffID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", user_id);
        return new AcceptInternshipPositionOfferCommandCall(intPosOffID, payload, interviewManager, notificationManager, userManager).execute();
    }

    //No body needed, the student_id is extracted from the token
    @PostMapping("/interview/private/{intPosOffID}/reject-int-pos-off")
    @Operation(summary = "Close internship", description = "Reject the specified internship position offer, a notification will be sent to the company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, Internship closed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, User is not the owner of the internship"),
            @ApiResponse(responseCode = "404", description = "Internship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DTO> rejectInternshipPositionOffer(@PathVariable("intPosOffID") Integer intPosOffID, @RequestHeader("Authorization") String token) {
        String user_id = GetUuid.getUuid(token);
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", user_id);
        return new RejectInternshipPositionOfferCommandCall(intPosOffID, payload, interviewManager, notificationManager, userManager).execute();
    }

    /*---------------------------------*/
    /*Deprecated API calls*/
    /*---------------------------------*/
    /*@GetMapping("/private/test")
    public ResponseEntity<DTO> showTraffickerHeader(@RequestHeader("X-Custom-Header") String customHeader) {
        return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, customHeader), HttpStatus.OK);

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
    }*/
}
