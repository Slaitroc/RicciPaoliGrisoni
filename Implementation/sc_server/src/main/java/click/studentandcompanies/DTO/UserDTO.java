package click.studentandcompanies.DTO;

import click.studentandcompanies.entity.University;

import java.util.Objects;

/*DTO stands for Data Transfer Object. It is a design pattern used to transfer data between software application subsystems.
 * DTO are not mandatory and do not extend any class or implement any interface.
 * DTOs are used to encapsulate data and send it from one subsystem of an application to another.
 * THEY DO NOT CONTAIN ANY BUSINESS LOGIC, they are used to transfer data between the client and the server.
 * They can be AUTOMATICALLY created by the JPA BUDDY by clicking new->DTO and following the wizard.
 */

public class UserDTO{
    private final Integer id;
    private final String name;
    private final String email;
    private final University universityID;

    public UserDTO(Integer id, String name, String email, University universityID) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.universityID = universityID;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public University getUniversityID() {
        return universityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.universityID, entity.universityID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, universityID);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "email = " + email + ", " +
                "universityID = " + universityID + ")";
    }
}