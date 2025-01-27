package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManager {
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    //is this a hack? We want UserManager to be able to access the RecommendationRepository?
    private final RecommendationRepository recommendationRepository;
    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final CvRepository cvRepository;
    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;

    public UserManager(UniversityRepository universityRepository, StudentRepository studentRepository,
                       CompanyRepository companyRepository, RecommendationRepository recommendationRepository
                       , SpontaneousApplicationRepository spontaneousApplicationRepository, InternshipOfferRepository internshipOfferRepository
                       , CvRepository cvRepository, FeedbackRepository feedbackRepository, InterviewRepository interviewRepository) {
        this.universityRepository = universityRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.recommendationRepository = recommendationRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.cvRepository = cvRepository;
        this.feedbackRepository = feedbackRepository;
        this.interviewRepository = interviewRepository;
    }

    //CRUD operations, all of them are already implemented by the JpaRepository
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }
    public void deleteUniversity(String id) {
        universityRepository.deleteById(id);
    }

    //Call the method from the UniversityRepository interface
    public University getUniversityByName(String name) {
        return universityRepository.findByName(name);
    }
    public long getNumberOfUniversitiesByCountry(String country) {
        String input = country.trim().substring(0, 2);
        System.out.println("Getting the number of universities in " + input);
        long output = universityRepository.countUniversitiesByCountry(input);
        System.out.println("There are " + output + " universities in " + input);
        return output;
    }

    public long getNumberOfUniversities() {
        return universityRepository.countAll();
    }

    //This is a "logic" method.
    // It uses the method from the UniversityRepository to implement a more complex logic than a simple CRUD operation
    public boolean areThereAnyUniversities() {
        return universityRepository.countAll() > 0;
    }

    public Student getStudentById(String id) {
        return studentRepository.getStudentById(id);
    }

    public Company getCompanyById(String id) {
        return companyRepository.getCompanyById(id);
    }

    public University getUniversityById(String id) {
        return universityRepository.getUniversityById(id);
    }

    public UserType getUserType(String id) {
        if (studentRepository.getStudentById(id) != null) {
            return UserType.STUDENT;
        } else if (companyRepository.getCompanyById(id) != null) {
            return UserType.COMPANY;
        } else if (universityRepository.getUniversityById(id) != null) {
            return UserType.UNIVERSITY;
        } else {
            return UserType.UNKNOWN;
        }
    }

    public ParticipantTypeEnum getParticipantType(String id) {
        if (studentRepository.getStudentById(id) != null) {
            return ParticipantTypeEnum.student;
        } else if (companyRepository.getCompanyById(id) != null) {
            return ParticipantTypeEnum.company;
        } else {
            return null;
        }
    }

    public List<Recommendation> getRecommendationByStudentId(String studentId) {
        return recommendationRepository.findRecommendationByStudentId(studentId);
    }

    public List<Recommendation> getRecommendationByCompanyId(String companyId) {
        return recommendationRepository.findRecommendationByCompanyId(companyId);
    }

    public Recommendation getRecommendationById(int id) {
        return recommendationRepository.getRecommendationById(id);
    }

    public List<String> getInvolvedUsers(Integer internshipID) {
        List<String> userIDs = new ArrayList<>();

        //Fetch all spontaneous applications and recommendations linked to the internship
        List<SpontaneousApplication> applications = spontaneousApplicationRepository.findAllByInternshipOfferId(internshipID);
        List<Recommendation> recommendations = recommendationRepository.findRecommendationByInternshipOfferId(internshipID);

        //Add all student IDs from spontaneous applications and recommendations sources linked to the internship
        userIDs.addAll(applications.stream().map(application -> application.getStudent().getId()).toList());
        userIDs.addAll(recommendations.stream().map(recommendation -> recommendation.getCv().getStudent().getId()).toList());

        return userIDs;
    }

    public InternshipOffer getInternshipOfferById(int id) {
        return internshipOfferRepository.getInternshipOfferById(id);
    }

    public List<InternshipOffer> getAllInternshipOffers() {
        return internshipOfferRepository.findAll();
    }

    public List<Cv> getAllCvs(){
        return cvRepository.findAll();
    }

    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.findAll();
    }

    public String getStudentIDByInternshipPosOfferID(Integer id) {
        Interview interview = interviewRepository.getInterviewByInternshipPosOffer_Id(id);

        if(interview.getRecommendation() != null) {
            return interview.getRecommendation().getCv().getStudent().getId();
        } else {
            return interview.getSpontaneousApplication().getStudent().getId();
        }
    }

    public List<University> getUniversity() {
        return universityRepository.findAll();
    }

    public List<Company> getCompany() {
        return companyRepository.findAll();
    }
}

