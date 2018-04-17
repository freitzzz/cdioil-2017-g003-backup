/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import cdioil.domain.EAN;
import cdioil.domain.Product;
import cdioil.domain.Review;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests relatively to Profile class
 * @author João
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ProfileTest {
    /**
     * Test of addReview method, of class Profile.
     */
    @Test
    public void testAddReview() {
        System.out.println("addReview");
        Review review = new Review("Popping a Xan",new Product("Xanax",new EAN("1111"),new EAN("1112")));
        Profile profile=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        assertTrue("The condition should be succesful since the Review being added is valid",profile.addReview(review));
        assertFalse("The condition should be succesful since the Review being added is not valid so it should fail",profile.addReview(null));
    }

    /**
     * Test of changeInformation method, of class Profile.
     */
    @Test
    public void testChangeInformation() {
        System.out.println("changeInformation");
        String newInfo = "Also I sip Lean";
        Profile profile=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        profile.changeInformation(newInfo);
        //TO-DO: João check for valid info
    }

    /**
     * Test of hashCode method, of class Profile.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Profile profileX=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        Profile profileY=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        assertEquals("The condition should be successul since both profiles have the same hashcode",
                profileX.hashCode(),profileY.hashCode());
    }

    /**
     * Test of equals method, of class Profile.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Profile profileX=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        Profile profileY=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        Profile profileZ=createProfile(new RegisteredUser(new SystemUser(new Email("as@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        assertEquals("The condition should be successful since both profiles are the same",profileX,profileY);
        assertNotEquals("The condition should be successful since both profiles are different",profileX,profileZ);
        assertNotEquals("The condition should be successful since one profile is null",profileX,null);
    }

    /**
     * Test of toString method, of class Profile.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Profile profileX=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        Profile profileY=createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123"))));
        assertEquals("The condition should be successful since both profiles are the same",profileX.toString()
                ,profileY.toString());
    }

    /**
     * Test of getID method, of class Profile.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        RegisteredUser registeredUser=new RegisteredUser(new SystemUser(new Email("asd@email.com"),new Name("Lil","Pump"),new Password("Password123")));
        Profile profileX=createProfile(registeredUser);
        RegisteredUser result = profileX.getID();
        assertEquals("The condition should be successful since both profiles identities are the same",registeredUser
                ,result);
    }

    /**
     * Test of adicionarAvaliacao method, of class Perfil.
     */
    /*@Test
    public void testAdicionarAvaliacao() {
        System.out.println("adicionarAvaliacao");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of mudarInformacao method, of class Perfil.
     */
    /*@Test
    public void testMudarInformacao() {
        System.out.println("mudarInformacao");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc(){assertNotNull(new Profile());}
    
    /**
     * Creates a new Profile of a certain Registered User
     * @param registeredUser RegisteredUser that owns the profile
     * @return Profile with the new profile of the registered user
     */
    private Profile createProfile(RegisteredUser registeredUser){try{return new Profile(registeredUser);}catch(IllegalArgumentException e){return null;}}
}
