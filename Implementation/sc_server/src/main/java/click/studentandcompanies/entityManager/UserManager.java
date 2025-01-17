package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.entityRepository.CompanyRepository;
import click.studentandcompanies.entityManager.entityRepository.StudentRepository;
import click.studentandcompanies.entityManager.entityRepository.UniversityRepository;
import click.studentandcompanies.utils.UserType;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public UserManager(UniversityRepository universityRepository, StudentRepository studentRepository, CompanyRepository companyRepository) {
        this.universityRepository = universityRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    //CRUD operations, all of them are already implemented by the JpaRepository
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }
    public void deleteUniversity(int id) {
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

    public Student getStudentById(int id) {
        return studentRepository.getStudentById(id);
    }

    public Company getCompanyById(int id) {
        return companyRepository.getCompanyById(id);
    }

    public UserType getUserType(int id) {
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
}

