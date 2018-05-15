package cdioil.application.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for EmailSenders class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class EmailSendersTest {
    /**
     * Test for EmailSenders constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("The condition should succeed because both arguments are "
                + "valid",createEmailSenders("lilpump@gmail.com", "Password123"));
        assertNotNull("Empty constructor call",new EmailSenders());
    }

    /**
     * Test of getEmail method, of class EmailSenders.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        EmailSenders instance = new EmailSenders("lilpump@gmail.com","Password123");
        String expResult = "lilpump@gmail.com";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class EmailSenders.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        EmailSenders instance = new EmailSenders("xxxtentacion@gmail.com","LookAtMe123");
        String expResult = "LookAtMe123";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Returns an EmailSenders instance
     *
     * @param email string with the email
     * @param password string with the password
     * @return EmailSenders instance
     */
    private EmailSenders createEmailSenders(String email, String password) {
        try {
            return new EmailSenders(email, password);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
