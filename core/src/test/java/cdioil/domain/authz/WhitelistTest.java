package cdioil.domain.authz;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class WhitelistTest {
    
    public WhitelistTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of equals method, of class Whitelist.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        Whitelist domain = new  Whitelist("isep.ipp.pt");
        
        assertEquals("Objects with the same reference should be equal",domain, domain);
        
        Object obj = null;
        
        assertNotEquals("Null object is not equal", domain, obj);
        
        assertNotEquals("Objects from different classes should not be equal", domain,"isep.ipp.pt");
        
        Whitelist sameDomain = new Whitelist("ISEP.IPP.PT");
        
        assertEquals("Objects should be equal, since equality is case insensitive", domain, sameDomain);
    }

    /**
     * Test of hashCode method, of class Whitelist.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
      
        Whitelist domain = new  Whitelist("isep.ipp.pt");
        
        assertNotEquals("Despite the domain's string being the same, hashcodes should be different", domain.hashCode(), "isep.ipp.pt".hashCode());
        
        Whitelist sameDomain = new Whitelist("ISEP.IPP.PT");
        
        assertEquals("Both domains' hash value should be the same since it's case insensitive", domain.hashCode(), sameDomain.hashCode());
    }

    /**
     * Test of toString method, of class Whitelist.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
     
        Whitelist domain = new  Whitelist("isep.ipp.pt");
        
        Whitelist sameDomain = new Whitelist("ISEP.IPP.PT");
        
        assertEquals(domain.toString(), sameDomain.toString());
    }
    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc(){
        assertNotNull("Creation of the object shouldn't be null",new Whitelist());
    }
    
}
