package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class SurveyItemTypeTest {

    public SurveyItemTypeTest() {
    }

    /**
     * Test of values method, of class SurveyItemType.
     */
    @Test
    public void testValues() {
        SurveyItemType[] expResult = {SurveyItemType.CATEGORY, SurveyItemType.PRODUCT};
        SurveyItemType[] result = SurveyItemType.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class SurveyItemType.
     */
    @Test
    public void testValueOf() {
        String name = "PRODUCT";
        SurveyItemType expResult = SurveyItemType.PRODUCT;
        SurveyItemType result = SurveyItemType.valueOf(name);
        assertEquals(expResult, result);
        assertNotEquals(result, SurveyItemType.CATEGORY);

        name = "CATEGORY";
        expResult = SurveyItemType.CATEGORY;
        result = SurveyItemType.valueOf(name);
        assertEquals(expResult, result);
        assertNotEquals(result, SurveyItemType.PRODUCT);
    }

    /**
     * Test of toString method, of class SurveyItemType.
     */
    @Test
    public void testToString() {

        assertEquals(SurveyItemType.PRODUCT.toString(), "Product");
        assertEquals(SurveyItemType.CATEGORY.toString(), "Category");
    }

}
