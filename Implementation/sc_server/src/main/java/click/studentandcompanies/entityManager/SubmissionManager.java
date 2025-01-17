package click.studentandcompanies.entityManager;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionManager {
    private final CvRepository cvRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final UserManager userManager;

    @PersistenceContext
    private EntityManager entityManager;

    public SubmissionManager(CvRepository cvRepository, InternshipOfferRepository internshipOfferRepository, SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager) {
        this.cvRepository = cvRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
    }

    public List<InternshipOffer> getInternshipsByCompany(Integer companyID) {
        System.out.println("Getting the Internships of company: " + companyID);
        List<InternshipOffer> list = internshipOfferRepository.getInternshipOfferByCompanyId(companyID);
        System.out.println("Internships queried: " + list);
        return list;
    }

    public Cv getCvByStudent(Integer studentID) {
        return cvRepository.getCvByStudent_Id(studentID);
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

    public Cv updateCvCall(Map<String, Object> payload) {
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

    @Transactional
    public InternshipOffer updateInternshipOfferCall(Map<String, Object> payload) throws IllegalCallerException, IllegalArgumentException, IllegalAccessException {
        if(payload.get("company_id")==null){
            System.out.println("Company id not found");
            throw new IllegalArgumentException("Company id not found");
        }
        Company company = userManager.getCompanyById((Integer) payload.get("company_id"));
        if(company == null){
            System.out.println("Company not found");
            throw new IllegalCallerException("Company not found");
        }

        if(payload.get("internshipOffer_id")!=null){ //If the offer id is present, we are UPDATING it
            InternshipOffer updatedOffer = internshipOfferRepository.getInternshipOfferById((Integer) payload.get("internshipOffer_id"));
            if(updatedOffer == null){
                System.out.println("Internship offer not found");
                throw new IllegalCallerException("Internship offer not found");
            }
            List<InternshipOffer> offers = internshipOfferRepository.getInternshipOfferByCompanyId(company.getId());
            if(!offers.contains(updatedOffer)){
                System.out.println("Company is not the owner of the offer");
                throw new IllegalAccessException("Company is not the owner of the offer");
            }
            return internshipOfferRepository.save(updateInternshipOffer(payload, updatedOffer));
        }else{ //if the offer id is not present, we are CREATING it
            return internshipOfferRepository.save(createInternshipOffer(payload));
        }
    }

    @Transactional
    protected InternshipOffer createInternshipOffer(Map<String, Object> payload) {
        //Save non-nullable fields
        String title = (String) payload.get("title");
        if(title == null){
            System.out.println("Title not found");
            throw new IllegalArgumentException("Title not found");
        }
        String description = (String) payload.get("description");
        if(description == null){
            System.out.println("Description not found");
            throw new IllegalArgumentException("Description not found");
        }
        Integer compensation = (Integer) payload.get("compensation");
        if(compensation == null){
            System.out.println("Compensation not found");
            throw new IllegalArgumentException("Compensation not found");
        }
        String location = (String) payload.get("location");
        if(location == null){
            System.out.println("Location not found");
            throw new IllegalArgumentException("Location not found");
        }
        Integer durationHours = (Integer) payload.get("duration_hours");
        if(durationHours == null){
            System.out.println("Duration hours not found");
            throw new IllegalArgumentException("Duration hours not found");
        }
        //If a problem occurs with the parsing of the dates, the exception will be thrown by the LocalDate.parse method
        //This means that start_date and end_date are never null if they are present in the payload and the format is correct
        if (payload.get("start_date") == null) {
            System.out.println("Start date not found");
            throw new IllegalArgumentException("Start date not found");
        }
        LocalDate startDate = LocalDate.parse((String) payload.get("start_date"));
        if (payload.get("end_date") == null) {
            System.out.println("End date not found");
            throw new IllegalArgumentException("End date not found");
        }
        LocalDate endDate = LocalDate.parse((String) payload.get("end_date"));
        //Check if the start date is before the end date
        if(startDate.isAfter(endDate)){
            System.out.println("Start date is after end date");
            throw new IllegalArgumentException("Start date is after end date");
        }
        //Save nullable fields
        String requiredSkills = (String) payload.get("required_skills");
        Integer numberPositions = (Integer) payload.get("number_positions");
        //Get the company (we already checked if the company id is present in the caller method)
        Company company = userManager.getCompanyById((Integer) payload.get("company_id"));
        return new InternshipOffer(company, title, description, requiredSkills, compensation, location, startDate, endDate, numberPositions, durationHours);
    }

    private InternshipOffer updateInternshipOffer(Map<String, Object> payload, InternshipOffer updatedOffer) {
        if(payload.get("title")!=null)
            updatedOffer.setTitle((String) payload.get("title"));
        if(payload.get("description")!=null)
            updatedOffer.setDescription((String) payload.get("description"));
        if(payload.get("required_skills")!=null)
            updatedOffer.setRequiredSkills((String) payload.get("required_skills"));
        if(payload.get("compensation")!=null)
            updatedOffer.setCompensation((Integer) payload.get("compensation"));
        if(payload.get("location")!=null)
            updatedOffer.setLocation((String) payload.get("location"));
        if(payload.get("start_date")!=null)
            updatedOffer.setStartDate(LocalDate.parse((String) payload.get("start_date")));
        if(payload.get("end_date")!=null)
            updatedOffer.setEndDate(LocalDate.parse((String) payload.get("end_date")));
        if(payload.get("number_positions")!=null)
            updatedOffer.setNumberPositions((Integer) payload.get("number_positions"));
        if(payload.get("duration_hours")!=null)
            updatedOffer.setDurationHours((Integer) payload.get("duration_hours"));
        return updatedOffer;
    }

    public List<SpontaneousApplication> getSpontaneousApplicationByStudent(Integer studentID){
        return spontaneousApplicationRepository.getSpontaneousApplicationByStudent_Id(studentID);
    }

    public List<SpontaneousApplication> getSpontaneousApplicationByCompany(Integer companyID) {
        //get a list of only the IDs of the internships offered by that company
        List<Integer> internshipsIDs = getInternshipsByCompany(companyID).stream().map(InternshipOffer::getId).toList();
        System.out.println("IDs found: " + internshipsIDs);
        List<SpontaneousApplication> result = new ArrayList<>();
        //spontaneous applications are not linked directly to companies but only through Internships Offers
        for(Integer id : internshipsIDs){
            result.addAll(spontaneousApplicationRepository.getSpontaneousApplicationByInternshipOffer_Id(id));
        }
        System.out.println("SubmissionManager output: " + result);
        return result;
    }
}
