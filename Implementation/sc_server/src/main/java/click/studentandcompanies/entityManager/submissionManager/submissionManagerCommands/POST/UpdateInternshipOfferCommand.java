package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateInternshipOfferCommand implements SubmissionManagerCommand<InternshipOffer> {
    UserManager userManager;
    InternshipOfferRepository internshipOfferRepository;
    Map<String, Object> payload;

    public UpdateInternshipOfferCommand(UserManager userManager, InternshipOfferRepository internshipOfferRepository, Map<String, Object> payload) {
        this.userManager = userManager;
        this.internshipOfferRepository = internshipOfferRepository;
        this.payload = payload;
    }

    @Override
    public InternshipOffer execute() {
        if(payload.get("company_id")==null){
            System.out.println("Company id not found");
            throw new BadInputException("Company id not found");
        }
        Company company = userManager.getCompanyById((String) payload.get("company_id"));
        if(company == null){
            System.out.println("Company not found");
            throw new NotFoundException("Company not found");
        }

        if(payload.get("internshipOffer_id")!=null){ //If the offer id is present, we are UPDATING it
            InternshipOffer updatedOffer = internshipOfferRepository.getInternshipOfferById((Integer) payload.get("internshipOffer_id"));
            if(updatedOffer == null){
                System.out.println("Internship offer not found");
                throw new NotFoundException("Internship offer not found");
            }
            List<InternshipOffer> offers = internshipOfferRepository.getInternshipOfferByCompanyId(company.getId());
            if(!offers.contains(updatedOffer)){
                System.out.println("Company is not the owner of the offer");
                throw new UnauthorizedException("Company is not the owner of the offer");
            }
            return internshipOfferRepository.save(updateInternshipOffer(payload, updatedOffer));
        }else{ //if the offer id is not present, we are CREATING it
            return internshipOfferRepository.save(createInternshipOffer(payload));
        }
    }

    private InternshipOffer createInternshipOffer(Map<String, Object> payload){
        //Save non-nullable fields
        String title = (String) payload.get("title");
        if(title == null){
            System.out.println("Title not found");
            throw new BadInputException("Title not found");
        }
        String description = (String) payload.get("description");
        if(description == null){
            System.out.println("Description not found");
            throw new BadInputException("Description not found");
        }
        Integer compensation = (Integer) payload.get("compensation");
        if(compensation == null){
            System.out.println("Compensation not found");
            throw new BadInputException("Compensation not found");
        }
        String location = (String) payload.get("location");
        if(location == null){
            System.out.println("Location not found");
            throw new BadInputException("Location not found");
        }
        Integer durationHours = (Integer) payload.get("duration_hours");
        if(durationHours == null){
            System.out.println("Duration hours not found");
            throw new BadInputException("Duration hours not found");
        }
        //If a problem occurs with the parsing of the dates, the exception will be thrown by the LocalDate.parse method
        //This means that start_date and end_date are never null if they are present in the payload and the format is correct
        if (payload.get("start_date") == null) {
            System.out.println("Start date not found");
            throw new BadInputException("Start date not found");
        }
        LocalDate startDate = LocalDate.parse((String) payload.get("start_date"));
        if (payload.get("end_date") == null) {
            System.out.println("End date not found");
            throw new BadInputException("End date not found");
        }
        LocalDate endDate = LocalDate.parse((String) payload.get("end_date"));
        //Check if the start date is before the end date
        if(startDate.isAfter(endDate)){
            System.out.println("Start date is after end date");
            throw new BadInputException("Start date is after end date");
        }
        //Save nullable fields
        String requiredSkills = (String) payload.get("required_skills");
        Integer numberPositions = (Integer) payload.get("number_positions");
        //Get the company (we already checked if the company id is present in the caller method)
        Company company = userManager.getCompanyById((String) payload.get("company_id"));
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
        updatedOffer.setUpdateTime(Instant.now());
        return updatedOffer;
    }
}
