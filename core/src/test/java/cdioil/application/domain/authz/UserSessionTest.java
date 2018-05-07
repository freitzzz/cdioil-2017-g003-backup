package cdioil.application.domain.authz;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.SystemUser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests relatively to UserSession class
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public class UserSessionTest {
    
    public UserSessionTest() {
    }

    /**
     * Test of logSessionEnd method, of class UserSession.
     */
    @Test
    public void testLogSessionEnd() {
        System.out.println("logSessionEnd");
        UserSession instance = new UserSession();
        instance.logSessionEnd();
    }

    /**
     * Test of getUser method, of class UserSession.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        SystemUser expResult = new SystemUser(new Email("0xFF3D@email.com"),null,null);
        UserSession instance = new UserSession(expResult);
        SystemUser result = instance.getUser();
        assertEquals("The condition should be successful since the user is the same on the session",
                expResult,result);
    }

    /**
     * Test of getUserToken method, of class UserSession.
     */
    @Test
    public void testGetUserToken() {
        System.out.println("getUserToken");
        UserSession instance = new UserSession(new SystemUser(new Email("0xFF4D@email.com"),null,null));
        String result = instance.getUserToken();
        assertNotNull("The condition should be successful since the token generated is not null"
                ,result);
    }
    
}
