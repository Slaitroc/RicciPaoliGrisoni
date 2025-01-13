package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.entityRepository.UniversityRepository;
import org.springframework.stereotype.Service;

@Service
public class universityManager {
    private final UniversityRepository universityRepository;

    public universityManager(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
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
        return universityRepository.countUniversitiesByCountry(country);
    }

    //This is a "logic" method.
    // It uses the method from the UniversityRepository to implement a more complex logic than a simple CRUD operation
    public boolean areThereAnyUniversities() {
        return universityRepository.countAllUniversity() > 0;
    }
}
