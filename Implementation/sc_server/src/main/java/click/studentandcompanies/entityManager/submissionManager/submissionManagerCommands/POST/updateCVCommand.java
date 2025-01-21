package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;

import java.time.Instant;
import java.util.Map;

public class updateCVCommand implements SubmissionManagerCommand<Cv> {
    Map<String, Object> payload;
    CvRepository cvRepository;
    UserManager userManager;

    public updateCVCommand(UserManager userManager, CvRepository cvRepository, Map<String, Object> payload) {
        this.payload = payload;
        this.cvRepository = cvRepository;
        this.userManager = userManager;
    }

    @Override
    public Cv execute() {
        if(payload.get("student_id")==null){
            System.out.println("Student id not found");
            throw new IllegalArgumentException("Student id not found");
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
            cv = updateCV(payload, cv);
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

    private Cv updateCV(Map<String, Object> payload, Cv cv){
        Instant updateTime = Instant.parse(String.valueOf(payload.get("update_time")));
        if(payload.get("update_time")==null){
            System.out.println("Update time not found");
            throw new IllegalCallerException("Update time not found");
        }
        if(payload.get("skills")!=null)
            cv.setSkills((String) payload.get("skills"));
        if(payload.get("work_experiences")!=null)
            cv.setWorkExperiences((String) payload.get("work_experiences"));
        if(payload.get("education")!=null)
            cv.setEducation((String) payload.get("education"));
        if(payload.get("project")!=null)
            cv.setProject((String) payload.get("project"));
        if(payload.get("certifications")!=null)
            cv.setCertifications((String) payload.get("certifications"));
        if (payload.get("update_time")!=null)
            cv.setUpdateTime(updateTime);
        return cv;
    }
}
