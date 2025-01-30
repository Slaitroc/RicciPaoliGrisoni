package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.time.Instant;
import java.util.Map;

public class UpdateCVCommand implements SubmissionManagerCommand<Cv> {
    Map<String, Object> payload;
    CvRepository cvRepository;
    UserManager userManager;

    public UpdateCVCommand(UserManager userManager, CvRepository cvRepository, Map<String, Object> payload) {
        this.payload = payload;
        this.cvRepository = cvRepository;
        this.userManager = userManager;
    }

    @Override
    public Cv execute() {
        if(payload.get("studentID")==null){
            System.out.println("Student id not found");
            throw new BadInputException("Student id not found");
        }
        Student student  = userManager.getStudentById((String) payload.get("studentID"));
        if(student == null){
            System.out.println("Student not found");
            throw new NotFoundException("Student not found");
        }
        Cv cv = cvRepository.getCvByStudent_Id(student.getId());
        if(cv == null) {
            cv = createCV(payload);
        } else {
            cv = updateCV(payload, cv);
        }
        //If the cv is not found in the database it will be created else it will be updated
        return cvRepository.save(cv);
    }

    private Cv createCV(Map<String, Object> payload) throws BadInputException {
        String studentId;
        Instant updateTime;

        //Save non-nullable fields
        studentId = (String) payload.get("studentID");
        updateTime = Instant.now();

        //Save nullable fields
        String skills = (String) payload.get("skills") != null ? (String) payload.get("skills") : "";
        String workExperiences = (String) payload.get("workExperiences") != null ? (String) payload.get("workExperiences") : "";
        String education = (String) payload.get("education") != null ? (String) payload.get("education") : "";
        String project = (String) payload.get("project") != null ? (String) payload.get("project") : "";
        String certifications = (String) payload.get("certifications") != null ? (String) payload.get("certifications") : "";
        String spokenLanguages = (String) payload.get("spokenLanguages") != null ? (String) payload.get("spokenLanguages") : "";
        String contacts = (String) payload.get("contacts") != null ? (String) payload.get("contacts") : "";

        return new Cv(userManager.getStudentById(studentId), skills, workExperiences, education, project, certifications, updateTime, spokenLanguages, contacts);
    }

    private Cv updateCV(Map<String, Object> payload, Cv cv){
        if(payload.get("skills")!=null)
            cv.setSkills((String) payload.get("skills"));
        if(payload.get("workExperiences")!=null)
            cv.setWorkExperiences((String) payload.get("workExperiences"));
        if(payload.get("education")!=null)
            cv.setEducation((String) payload.get("education"));
        if(payload.get("project")!=null)
            cv.setProject((String) payload.get("project"));
        if(payload.get("certifications")!=null)
            cv.setCertifications((String) payload.get("certifications"));
        if(payload.get("spokenLanguages")!=null)
            cv.setSpokenLanguages((String) payload.get("spokenLanguages"));
        if(payload.get("contacts")!=null)
            cv.setContacts((String) payload.get("contacts"));
        cv.setUpdateTime(Instant.now());
        return cv;
    }
}
