package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanism;
import click.studentandcompanies.entityRepository.FeedbackRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedbackMechanismTest {

    @Mock
    private UserManager userManager;

    @Mock
    private FeedbackRepository feedbackRepository;

    private FeedbackMechanism feedbackMechanism;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedbackMechanism = new FeedbackMechanism(userManager, feedbackRepository);
    }

    @Test
    void testSubmitFeedback_ValidStudent() {
        // Setup valid student payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "student1");
        payload.put("participant_type", "student");
        payload.put("rating", 4);
        payload.put("upload_time", Instant.now().toString());
        payload.put("comment", "Great recommendation!");

        // Mock recommendation and user
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.acceptedMatch);
        recommendation.setCv(new Cv());
        recommendation.getCv().setStudent(new Student());
        recommendation.getCv().getStudent().setId("student1");
        recommendation.setId(1);

        when(userManager.getRecommendationById(1)).thenReturn(recommendation);
        when(userManager.getParticipantType("student1")).thenReturn(ParticipantTypeEnum.student);
        when(userManager.getRecommendationByStudentId("student1")).thenReturn(List.of(recommendation));
        when(feedbackRepository.findByRecommendation(recommendation)).thenReturn(null);

        // Execute and verify
        Feedback feedback = feedbackMechanism.submitFeedback(1, payload);
        assertNotNull(feedback);
        assertEquals(4, feedback.getRating());
        //assertEquals("Great recommendation!", feedback.getComment());
        verify(feedbackRepository).save(any(Feedback.class));
    }

    @Test
    void testSubmitFeedback_ValidCompany() {
        // Setup valid company payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "company1");
        payload.put("participant_type", "company");
        payload.put("rating", 5);
        payload.put("upload_time", Instant.now().toString());
        payload.put("comment", "Excellent!");

        // Mock recommendation and user
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.acceptedMatch);
        recommendation.setInternshipOffer(new InternshipOffer());
        recommendation.getInternshipOffer().setCompany(new Company());
        recommendation.getInternshipOffer().getCompany().setId("company1");
        recommendation.setId(1);

        when(userManager.getRecommendationById(1)).thenReturn(recommendation);
        when(userManager.getParticipantType("company1")).thenReturn(ParticipantTypeEnum.company);
        when(userManager.getRecommendationByCompanyId("company1")).thenReturn(List.of(recommendation));
        when(feedbackRepository.findByRecommendation(recommendation)).thenReturn(null);

        // Execute and verify
        Feedback feedback = feedbackMechanism.submitFeedback(1, payload);
        assertNotNull(feedback);
        assertEquals(5, feedback.getRating());
        //assertEquals("Excellent!", feedback.getComment());
        verify(feedbackRepository).save(any(Feedback.class));
    }

    @Test
    void testSubmitFeedback_InvalidRating() {
        // Setup payload with invalid rating
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "student1");
        payload.put("participant_type", "student");
        payload.put("rating", 6); // Invalid rating
        payload.put("upload_time", Instant.now().toString());

        // Execute and verify exception
        BadInputException exception = assertThrows(
                BadInputException.class,
                () -> feedbackMechanism.submitFeedback(1, payload)
        );
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void testSubmitFeedback_InvalidUploadTime() {
        // Setup payload with invalid upload time
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "student1");
        payload.put("participant_type", "student");
        payload.put("rating", 4);
        payload.put("upload_time", Instant.now().plusSeconds(3600).toString()); // Future upload time

        // Execute and verify exception
        BadInputException exception = assertThrows(
                BadInputException.class,
                () -> feedbackMechanism.submitFeedback(1, payload)
        );
        assertEquals("Upload time can't be in the future", exception.getMessage());
    }

    @Test
    void testSubmitFeedback_InvalidRecommendationState() {
        // Setup payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "student1");
        payload.put("participant_type", "student");
        payload.put("rating", 4);
        payload.put("upload_time", Instant.now().toString());

        // Mock recommendation with invalid state
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.pendingMatch);

        when(userManager.getRecommendationById(1)).thenReturn(recommendation);

        // Execute and verify exception
        WrongStateException exception = assertThrows(
                WrongStateException.class,
                () -> feedbackMechanism.submitFeedback(1, payload)
        );
        assertEquals("Feedback can only be submitted for accepted matches", exception.getMessage());
    }

    @Test
    void testSubmitFeedback_FeedbackAlreadySubmitted() {
        // Setup payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "student1");
        payload.put("participant_type", "student");
        payload.put("rating", 4);
        payload.put("upload_time", Instant.now().toString());

        // Mock recommendation and feedback
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.acceptedMatch);
        Feedback existingFeedback = new Feedback();

        when(userManager.getRecommendationById(1)).thenReturn(recommendation);
        when(feedbackRepository.findByRecommendation(recommendation)).thenReturn(existingFeedback);

        // Execute and verify exception
        WrongStateException exception = assertThrows(
                WrongStateException.class,
                () -> feedbackMechanism.submitFeedback(1, payload)
        );
        assertEquals("Feedback already submitted for this recommendation", exception.getMessage());
    }
}
