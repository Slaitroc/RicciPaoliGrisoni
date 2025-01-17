package click.studentandcompanies.entityManager;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.utils.UserType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionManager {
    private final CvRepository cvRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final UserManager userManager;

    public SubmissionManager(CvRepository cvRepository, InternshipOfferRepository internshipOfferRepository, SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager) {
        this.cvRepository = cvRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
    }

    public List<InternshipOffer> getInternshipsByCompany(Integer companyID) {
        System.out.println("Getting the Internships of company: " + companyID);
        List<InternshipOffer> list = internshipOfferRepository.getInternshipOfferByCompany_Id(companyID);
        System.out.println("Internships queried: " + list);
        return list;
    }

    public Cv getCvByStudent(Integer studentID) {
        return cvRepository.getCvByStudent_Id(studentID);
    }

    public Cv updateCv(Map<String, Object> payload) {
        if(payload.get("update_time")==null || payload.get("student_id")==null){
            System.out.println("Update time or student id not found");
            throw new IllegalArgumentException("Update time or student id not found");
        }
        Student student  = userManager.getStudentById((Integer) payload.get("student_id"));
        if(student == null){
            System.out.println("Student not found");
            throw new IllegalCallerException("Student not found");
        }
        Cv cv = cvRepository.getCvByStudent_Id(student.getId());
        if(cv == null) {
            cv = createCV(payload);
        } else {
            cv.setSkills((String) payload.get("skills"));
            cv.setWorkExperiences((String) payload.get("work_experiences"));
            cv.setEducation((String) payload.get("education"));
            cv.setProject((String) payload.get("project"));
            cv.setCertifications((String) payload.get("certifications"));
            cv.setUpdateTime(Instant.parse(String.valueOf(payload.get("update_time"))));
        }
        //If the cv is not found in the database it will be created else it will be updated
        return cvRepository.save(cv);
    }

    private Cv createCV(Map<String, Object> payload) throws IllegalCallerException {
        Integer studentId;
        Instant updateTime;

        //Save non-nullable fields
        studentId = (Integer) payload.get("student_id");
        updateTime = Instant.parse(String.valueOf(payload.get("update_time")));
        if(payload.get("update_time")==null){
            System.out.println("Update time not found");
            throw new IllegalCallerException("Update time not found");
        }

        //Save nullable fields
        String skills = (String) payload.get("skills") != null ? (String) payload.get("skills") : "";
        String workExperiences = (String) payload.get("work_experiences") != null ? (String) payload.get("work_experiences") : "";
        String education = (String) payload.get("education") != null ? (String) payload.get("education") : "";
        String project = (String) payload.get("project") != null ? (String) payload.get("project") : "";
        String certifications = (String) payload.get("certifications") != null ? (String) payload.get("certifications") : "";

        return new Cv(userManager.getStudentById(studentId), skills, workExperiences, education, project, certifications, updateTime);
    }
}
