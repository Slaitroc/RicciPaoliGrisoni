package click.studentandcompanies.DTO;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entity.University;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DTOCreator {
    public static DTO createDTO(DTOTypes dtoType, Object obj) {
        return functionMap.get(dtoType).apply(obj);
    }

    private static final Map<DTOTypes, Function<Object, DTO>> functionMap = new HashMap<>();
    static {
        functionMap.put(DTOTypes.ERROR, obj -> createErrorDTO((String) obj));
        functionMap.put(DTOTypes.STUDENT, obj -> createStudentDTO((Student) obj));
        functionMap.put(DTOTypes.STUDENT_V2, obj -> createStudentDTOV2((Student) obj));
        functionMap.put(DTOTypes.UNIVERSITY, obj -> createUniversityDTO((University) obj));
        functionMap.put(DTOTypes.RECOMMENDATION_UPDATED_STATUS, object -> createRecommendationUpdatedStatusDTO((Recommendation) object));
        functionMap.put(DTOTypes.INTERNSHIP_OFFER, object -> createInternshipOfferDTO((InternshipOffer) object));
    }

    private static DTO createRecommendationUpdatedStatusDTO(Recommendation recommendation) {
        final DTO recommendationDTO = new DTO();
        recommendationDTO.addProperty("id", recommendation.getId());
        recommendationDTO.addProperty("status", recommendation.getStatus().toString());
        return recommendationDTO;
    }

    private static DTO createErrorDTO(String message) {
        final DTO errorDTO = new DTO();
        errorDTO.addProperty("error", message);
        return errorDTO;
    }

    private static DTO createStudentDTO(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("email", student.getEmail());
        return studentDTO;
    }

    private static DTO createStudentDTOV2(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("email", student.getEmail());
        return studentDTO;
    }

    private static DTO createUniversityDTO(University university) {
        final DTO universityDTO = new DTO();
        universityDTO.addProperty("id", university.getId());
        universityDTO.addProperty("name", university.getName());
        universityDTO.addProperty("email", university.getEmail());
        universityDTO.addProperty("country", university.getCountry());
        universityDTO.addProperty("vatNumber", university.getVatNumber());
        return universityDTO;
    }

    private static DTO createInternshipOfferDTO(InternshipOffer offer) {
        final DTO offerDTO = new DTO();
        offerDTO.addProperty("id", offer.getId());
        offerDTO.addProperty("compensation", offer.getCompensation());
        offerDTO.addProperty("description", offer.getDescription());
        offerDTO.addProperty("duration", offer.getDurationHours());
        offerDTO.addProperty("company", offer.getCompany());
        offerDTO.addProperty("endDate", offer.getEndDate());
        offerDTO.addProperty("location", offer.getLocation());
        offerDTO.addProperty("numberPositions", offer.getNumberPositions());
        offerDTO.addProperty("requiredSkills", offer.getRequiredSkills());
        offerDTO.addProperty("startDate", offer.getStartDate());
        offerDTO.addProperty("title", offer.getTitle());
        offerDTO.addProperty("company", offer.getCompany());
        return offerDTO;
    }
}
