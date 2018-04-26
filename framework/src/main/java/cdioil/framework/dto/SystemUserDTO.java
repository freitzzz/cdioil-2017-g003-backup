package cdioil.framework.dto;

public class SystemUserDTO extends GenericDTO implements DTO {

    public SystemUserDTO(String type, String firstName, String lastName, String email) {
        super(type);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public String getFirstName() {
        return get("firstName").toString();
    }

    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    public String getLastName() {
        return get("lastName").toString();
    }

    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    public String getEmail() {
        return get("email").toString();
    }

    public void setEmail(String email) {
        put("email", email);

    }
}
