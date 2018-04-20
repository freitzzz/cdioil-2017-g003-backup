package cdioil.application.domain.authz;

import java.time.format.DateTimeParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests relatively to SystemUserBuilder class
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class SystemUserBuilderTest {
    
    public SystemUserBuilderTest() {
    }

    /**
     * Test of create method, of class SystemUserBuilder.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        SystemUserBuilder builder = SystemUserBuilder.create();
        assertNotNull("The condition should be successful since the builder was created with success builder"
                ,builder);
    }

    /**
     * Test of build method, of class SystemUserBuilder.
     */
    @Test
    public void testValidBuild() {
        System.out.println("Valid Build");
        SystemUserBuilder instance = SystemUserBuilder.create();
        instance.withEmail("lilpump@blip.pt");
        instance.withPassword("Password123");
        instance.withName("Gazzy","Garcia");
        instance.withPhoneNumber("919555555");
        System.out.println("The build should be successful since it is valid");
        instance.build();
    }
    
    /**
     * Test of build method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBuild() {
        System.out.println("Invalid Build");
        SystemUserBuilder instance = SystemUserBuilder.create();
        instance.withEmail("lilpump@blip.pt");
        instance.withPassword("Password123");
        instance.withName("Gazzy","Garcia");
        System.out.println("The build should not be successful since it is missing the user phone number");
        instance.build();
    }

    /**
     * Test of withEmail method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidEmail() {
        System.out.println("Valid Email");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the email being built in is valid");
        instance.withEmail("lilpump@blip.pt");
    }
    
    /**
     * Test of withEmail method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidEmail() {
        System.out.println("Invalid Email");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the email being built in is invalid");
        instance.withEmail("lilpu..mp@blip.pt");
    }

    /**
     * Test of withPassword method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidPassword() {
        System.out.println("Valid Password");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the password being built in is valid");
        instance.withPassword("Password123");
    }
    
    /**
     * Test of withPassword method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidPassword() {
        System.out.println("Invalid Password");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the password being built in is invalid");
        instance.withPassword("Password");
    }

    /**
     * Test of withName method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidName() {
        System.out.println("Valid Name");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the name being built in is valid");
        instance.withName("Smoke","Purpp");
    }

    /**
     * Test of withName method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidName() {
        System.out.println("Invalid Name");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the name being built in is invalid");
        instance.withName("Smo3ke","Purpp");
    }
    
    /**
     * Test of withPhoneNumber method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidPhoneNumber() {
        System.out.println("Valid Phone Number");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the phone number being built in is valid");
        instance.withPhoneNumber("919555555");
    }

    /**
     * Test of withPhoneNumber method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidPhoneNumber() {
        System.out.println("Invalid Phone Number");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the phone number being built in is invalid");
        instance.withPhoneNumber("124521521");
    }

    /**
     * Test of withLocation method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidLocation() {
        System.out.println("Valid Location");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the location being built in is valid");
        instance.withLocation("Codeine City");
    }

    /**
     * Test of withLocation method, of class SystemUserBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidLocation() {
        System.out.println("Invalid Location");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the location being built in is invalid");
        instance.withLocation("");
    }

    /**
     * Test of withBirthDate method, of class SystemUserBuilder.
     */
    @Test
    public void testWithValidBirthDate() {
        System.out.println("Valid Birth Date");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should be successful since the birth date being built in is valid");
        instance.withBirthDate("1900-01-01");
    }

    /**
     * Test of withBirthDate method, of class SystemUserBuilder.
     */
    @Test(expected = DateTimeParseException.class)
    public void testWithInvalidBirthDate() {
        System.out.println("Invalid Birth Date");
        SystemUserBuilder instance = SystemUserBuilder.create();
        System.out.println("The condition should not be successful since the birth date being built in is invalid");
        instance.withBirthDate("");
    }
    
}
