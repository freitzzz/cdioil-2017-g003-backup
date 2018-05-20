package cdioil.domain.authz;

import cdioil.domain.EAN;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.Product;
import cdioil.domain.QRCode;
import cdioil.domain.Review;
import cdioil.domain.SKU;
import cdioil.domain.SurveyItem;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests relatively to Profile class
 *
 * @author João
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ProfileTest {

    /**
     * Test of getReviews method, of class Profile.
     */
    @Test
    public void testGetReviews() {
        System.out.println("getReviews");
        List<SurveyItem> list1 = new ArrayList<>();
        list1.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        
        ArrayList<SurveyItem> list2 = new ArrayList<>();
        list2.add(new Product("OutroProduto", new SKU("664239834"), "1 L", new QRCode("4765209")));        
        
        Review review1 = new Review(new GlobalSurvey(list1, new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX)));
        Review review2 = new Review(new GlobalSurvey(list2, new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX)));

        Profile profile = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        profile.addReview(review1);
        profile.addReview(review2);
        
        List<Review> expected = new ArrayList<>();  
        expected.add(review1);
        expected.add(review2);
        
        assertEquals("Should be equal", expected, profile.getReviews());
    }

    /**
     * Test of addReview method, of class Profile.
     */
    @Test
    public void testAddReview() {
        System.out.println("addReview");
        ArrayList<SurveyItem> list = new ArrayList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Review review = new Review(new GlobalSurvey(list, new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX)));
        Profile profile = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        assertTrue("The condition should be succesful since the Review being added is valid", profile.addReview(review));
        assertFalse("The condition should be succesful since the Review being added is not valid so it should fail", profile.addReview(null));
    }

    /**
     * Test of removeReview method, of class Profile for an invalid review.
     */
    @Test
    public void testRemoveInvalidReview() {
        System.out.println("removeReview for invalid review");
        ArrayList<SurveyItem> list = new ArrayList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Profile profile = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Review review = new Review(new GlobalSurvey(list, new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX)));
        assertFalse("Shouldn't be able to remove a non existent review", profile.removeReview(review));
    }

    /**
     * Test of removeReview method, of class Profile for a null review.
     */
    @Test
    public void testRemoveNullReview() {
        System.out.println("removeReview for null review");
        ArrayList<SurveyItem> list = new ArrayList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Profile profile = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        assertFalse("Shouldn't be able to remove a null review", profile.removeReview(null));
    }

    /**
     * Test of removeReview method, of class Profile for a valid review.
     */
    @Test
    public void testRemoveValidReview() {
        System.out.println("removeReview for valid review");
        ArrayList<SurveyItem> list = new ArrayList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Profile profile = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Review review = new Review(new GlobalSurvey(list, new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX)));
        profile.addReview(review);
        assertTrue("Should be able to remove", profile.removeReview(review));
    }

    /**
     * Test of hashCode method, of class Profile.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Profile profileX = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Profile profileY = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        assertEquals("The condition should be successul since both profiles have the same hashcode",
                profileX.hashCode(), profileY.hashCode());

        //Mutation tests
        assertNotEquals("".hashCode(), profileX.hashCode());
        int num = 71 * 7 + new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))).hashCode();
        assertEquals(num, profileX.hashCode());
    }

    /**
     * Test of equals method, of class Profile.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Profile profileX = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Profile profileY = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Profile profileZ = createProfile(new RegisteredUser(new SystemUser(new Email("as@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        assertEquals("The condition should be successful since both profiles are the same", profileX, profileY);
        assertNotEquals("The condition should be successful since both profiles are different", profileX, profileZ);
        assertNotEquals("The condition should be successful since one profile is null", profileX, null);
    }

    /**
     * Test of toString method, of class Profile.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Profile profileX = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        Profile profileY = createProfile(new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123"))));
        assertEquals("The condition should be successful since both profiles are the same", profileX.toString(),
                profileY.toString());

        //Mutation test
        assertNotEquals(null, profileX.toString());
    }

    /**
     * Test of getID method, of class Profile.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        RegisteredUser registeredUser = new RegisteredUser(new SystemUser(new Email("asd@email.com"), new Name("Lil", "Pump"), new Password("Password123")));
        Profile profileX = createProfile(registeredUser);
        RegisteredUser result = profileX.getID();
        assertEquals("The condition should be successful since both profiles identities are the same", registeredUser,
                result);
    }

    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc() {
        assertNotNull(new Profile());
    }

    /**
     * Creates a new Profile of a certain Registered User
     *
     * @param registeredUser RegisteredUser that owns the profile
     * @return Profile with the new profile of the registered user
     */
    private Profile createProfile(RegisteredUser registeredUser) {
        try {
            return new Profile(registeredUser);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
