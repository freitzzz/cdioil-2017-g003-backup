package cdioil.framework.dto;

/**
 * Data Transfer Object for SystemUser
 */
public class SystemUserDTO extends GenericDTO implements DTO {

    /**
     * Creates a System User DTO with given parameters
     * @param type dto type
     * @param firstName first name
     * @param lastName last name
     * @param email email
     */
    public SystemUserDTO(String type, String firstName, String lastName, String email) {
        super(type);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    /**
     * Getter method
     * @return first name
     */
    public String getFirstName() {
        return get("firstName").toString();
    }

    /**
     * Setter method
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    /**
     * Getter method
     * @return last name
     */
    public String getLastName() {
        return get("lastName").toString();
    }

    /**
     * Setter method
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    /**
     * Getter method
     * @return email
     */
    public String getEmail() {
        return get("email").toString();
    }

    /**
     * Setter method
     * @param email email
     */
    public void setEmail(String email) {
        put("email", email);

    }
}
