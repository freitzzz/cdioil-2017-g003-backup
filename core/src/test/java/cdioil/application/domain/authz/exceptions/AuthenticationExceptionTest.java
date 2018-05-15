package cdioil.application.domain.authz.exceptions;

import cdioil.application.domain.authz.exceptions.AuthenticationException.AuthenticationExceptionCause;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for AuthenticationException class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AuthenticationExceptionTest {

    /**
     * Test of getAuthenticationExceptionCause method, of class
     * AuthenticationException.
     */
    @Test
    public void testGetAuthenticationExceptionCause() {
        System.out.println("getAuthenticationExceptionCause");
        AuthenticationException instance = new AuthenticationException("test",
                AuthenticationExceptionCause.INVALID_CREDENTIALS);
        AuthenticationException other = new AuthenticationException("test",
                AuthenticationExceptionCause.NOT_ACTIVATED);
        AuthenticationException.AuthenticationExceptionCause expResult
                = AuthenticationExceptionCause.INVALID_CREDENTIALS;
        AuthenticationException.AuthenticationExceptionCause result
                = instance.getAuthenticationExceptionCause();
        assertEquals(expResult, result);
        expResult = AuthenticationExceptionCause.NOT_ACTIVATED;
        result = other.getAuthenticationExceptionCause();
        assertEquals(expResult, result);
    }
    /**
     * Test of enum AuthenticationExceptionCause, of class
     * AuthenticationException.
     */
    @Test
    public void testEnum(){
        System.out.println("enum tests");
        AuthenticationExceptionCause instance = AuthenticationExceptionCause.INVALID_CREDENTIALS;
        AuthenticationExceptionCause other = AuthenticationExceptionCause.NOT_ACTIVATED;
        
        assertEquals(instance.toString(),AuthenticationExceptionCause
                .INVALID_CREDENTIALS.toString());
        assertEquals(other.toString(),AuthenticationExceptionCause.
                NOT_ACTIVATED.toString());
        //Kill mutations
        assertNotEquals(null,instance.toString());
        assertNotEquals(null,other.toString());
    }
}
