package cdioil.domain.authz;

import cdioil.domain.authz.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests relatively to Location class
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class LocationTest {
    
    public LocationTest() {
    }
    
    /**
     * Test of Location constructor
     */
    @Test
    public void testConstructor(){
        assertNull("The condition should be successful since the location being created is null"
                ,createLocation(null));
        assertNull("The condition should be successful since the location being created is empty"
                ,createLocation(""));
        assertNull("The condition should be successful since the location being created is null"
                ,createLocation("                    "));
        assertNotNull("The condition should be successful since the location being created is valid"
                ,createLocation("Porto"));
    }
    
    /**
     * Test of equals method, of class Location.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Location locationX=createLocation("Porto");
        Location locationY=createLocation("Lisboa");
        Location locationZ=createLocation("PORTO");
        assertEquals("The condition should be successful since the Location being compared "
                + "is the same instance",locationX,locationX);
        assertNotEquals("The condition should be successful since the Location being "
                + "compared is null",locationX,null);
        assertNotEquals("The condition should be successful since the Location being "
                + "compared is from another class",locationX,"");
        assertNotEquals("The condition should be successful since the locations being "
                + "are not the same",locationX,locationY);
        assertEquals("The condition should be successful since the locations being "
                + "are the same",locationX,locationZ);
        
    }

    /**
     * Test of hashCode method, of class Location.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Location locationX=createLocation("Porto");
        Location locationY=createLocation("Porto");
        assertEquals("The condition should be successful since both locations hashcode "
                + "are the same",locationX.hashCode(),locationY.hashCode());
        
        //Mutation tests
        assertNotEquals("".hashCode(),locationX.hashCode());
        int num = 53 * 7 + "Porto".hashCode();
        assertEquals(num,locationX.hashCode());
    }

    /**
     * Test of toString method, of class Location.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String location="Lil Pump";
        assertEquals("The condition should be successful since the content of the Strings "
                + "are the same",location,createLocation(location).toString());
    }
    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc(){
        assertNotNull(new Location());
    }
    /**
     * Method that creates a new Location
     * @param location String with the location being created
     * @return Location with the created location
     */
    private Location createLocation(String location){try{return new Location(location);}catch(IllegalArgumentException e){ return null;}}
}
