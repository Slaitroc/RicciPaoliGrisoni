package click.studentandcompanies.DTO;

public class UserDTOv2 extends GenericDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTOv2(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

        // Registra automaticamente le proprietà
        addProperty("id", id);
        addProperty("name", name);
        addProperty("email", email);
    }

    // Getter e Setter opzionali, se necessario
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        addProperty("id", id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        addProperty("name", name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        addProperty("email", email);
    }
}
