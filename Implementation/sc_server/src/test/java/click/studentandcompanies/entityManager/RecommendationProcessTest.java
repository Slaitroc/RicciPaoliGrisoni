package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecommendationProcessTest {

    private RecommendationProcess recommendationProcess;
    private UserManager userManager;
    private RecommendationRepository recommendationRepository;

    @BeforeEach
    void setup() {
        userManager = mock(UserManager.class);
        recommendationRepository = mock(RecommendationRepository.class);
        recommendationProcess = new RecommendationProcess(userManager, recommendationRepository);
    }

    @Test
    void testStartRecommendationProcessCv() {
        Cv cv = new Cv();
        cv.setSkills("Java, Spring");
        cv.setWorkExperiences("Software Engineer");
        cv.setEducation("Bachelor's Degree");
        List<Feedback> feedbacks = new ArrayList<>();
        when(userManager.getAllInternshipOffers()).thenReturn(new ArrayList<>());
        when(userManager.getAllFeedbacks()).thenReturn(feedbacks);

        recommendationProcess.startRecommendationProcess(cv);

        verify(recommendationRepository).saveAll(anyList());
    }

    @Test
    void testStartRecommendationProcessInternshipOffer() {
        InternshipOffer internshipOffer = new InternshipOffer();
        internshipOffer.setTitle("Software Engineer Internship");
        internshipOffer.setDescription("Great opportunity to learn Java.");
        internshipOffer.setRequiredSkills("Java");
        internshipOffer.setId(1);

        List<Feedback> feedbacks = new ArrayList<>();
        when(userManager.getAllCvs()).thenReturn(new ArrayList<>());
        when(userManager.getAllFeedbacks()).thenReturn(feedbacks);

        recommendationProcess.startRecommendationProcess(internshipOffer);

        verify(recommendationRepository).saveAll(anyList());
    }

    @Test
    void testAcceptRecommendation() throws BadInputException, NotFoundException {
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.pendingMatch);
        when(recommendationRepository.getRecommendationById(1)).thenReturn(recommendation);

        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "user123");

        // Case 1: UserType STUDENT
        when(userManager.getUserType("user123")).thenReturn(UserType.STUDENT);
        Recommendation result = recommendationProcess.acceptRecommendation(1, payload);
        assertEquals(RecommendationStatusEnum.acceptedByStudent, result.getStatus());
        verify(recommendationRepository).save(any(Recommendation.class));

        // Case 2: UserType COMPANY
        recommendation.setStatus(RecommendationStatusEnum.pendingMatch); // Status Reset
        when(userManager.getUserType("user123")).thenReturn(UserType.COMPANY);
        result = recommendationProcess.acceptRecommendation(1, payload);
        assertEquals(RecommendationStatusEnum.acceptedByCompany, result.getStatus());
        verify(recommendationRepository, times(2)).save(any(Recommendation.class)); // Called twice

        // Case 3: UserType UNIVERSITY (not valid)
        when(userManager.getUserType("user123")).thenReturn(UserType.UNIVERSITY);
        BadInputException exception = assertThrows(
                BadInputException.class,
                () -> recommendationProcess.acceptRecommendation(1, payload)
        );
        assertEquals("Universities can't accept recommendations", exception.getMessage());

        // Case 4: UserType UNKNOWN
        when(userManager.getUserType("user123")).thenReturn(UserType.UNKNOWN);
        exception = assertThrows(
                BadInputException.class,
                () -> recommendationProcess.acceptRecommendation(1, payload)
        );
        assertEquals("Unknown user type", exception.getMessage());
    }


    @Test
    void testRejectRecommendation() throws BadInputException, NotFoundException {
        Recommendation recommendation = new Recommendation();
        recommendation.setStatus(RecommendationStatusEnum.pendingMatch);
        when(recommendationRepository.getRecommendationById(1)).thenReturn(recommendation);

        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "user123");

        // Case 1: UserType STUDENT
        when(userManager.getUserType("user123")).thenReturn(UserType.STUDENT);
        Recommendation result = recommendationProcess.rejectRecommendation(1, payload);
        assertEquals(RecommendationStatusEnum.rejectedMatch, result.getStatus());
        verify(recommendationRepository).save(any(Recommendation.class));

        // Case 2: UserType COMPANY
        recommendation.setStatus(RecommendationStatusEnum.pendingMatch); // Status Reset
        when(userManager.getUserType("user123")).thenReturn(UserType.COMPANY);
        result = recommendationProcess.rejectRecommendation(1, payload);
        assertEquals(RecommendationStatusEnum.rejectedMatch, result.getStatus());
        verify(recommendationRepository, times(2)).save(any(Recommendation.class)); // Called twice

        // Case 3: UserType UNIVERSITY (not valid)
        when(userManager.getUserType("user123")).thenReturn(UserType.UNIVERSITY);
        BadInputException exception = assertThrows(
                BadInputException.class,
                () -> recommendationProcess.rejectRecommendation(1, payload)
        );
        assertEquals("Universities can't refuse recommendations", exception.getMessage());

        // Case 4: UserType UNKNOWN
        when(userManager.getUserType("user123")).thenReturn(UserType.UNKNOWN);
        exception = assertThrows(
                BadInputException.class,
                () -> recommendationProcess.rejectRecommendation(1, payload)
        );
        assertEquals("Unknown user type", exception.getMessage());
    }


    @Test
    void testGetRecommendationsByParticipant() throws BadInputException, NotFoundException, NoContentException {
        List<Recommendation> recommendations = new ArrayList<>();
        Recommendation recommendation1 = new Recommendation();
        recommendation1.setStatus(RecommendationStatusEnum.pendingMatch);
        Recommendation recommendation2 = new Recommendation();
        recommendation2.setStatus(RecommendationStatusEnum.acceptedByCompany);
        recommendations.add(recommendation1);
        recommendations.add(recommendation2);

        when(userManager.getUserType("student123")).thenReturn(UserType.STUDENT);
        when(recommendationRepository.findRecommendationByStudentId("student123")).thenReturn(recommendations);

        List<Recommendation> result = recommendationProcess.getRecommendationsByParticipant("student123");

        assertEquals(2, result.size());
        assertEquals(RecommendationStatusEnum.pendingMatch, result.getFirst().getStatus());
    }

    @Test
    void testCloseRelatedRecommendations() {
        recommendationProcess.closeRelatedRecommendations(1);

        verify(recommendationRepository).removeRecommendationByInternshipOffer_Id(1);
    }
}
