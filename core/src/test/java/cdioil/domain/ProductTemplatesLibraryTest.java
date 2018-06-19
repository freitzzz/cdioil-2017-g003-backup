package cdioil.domain;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author António Sousa [1161371]
 */
public class ProductTemplatesLibraryTest {
    
    public ProductTemplatesLibraryTest() {
    }
    
    @Test
    public void ensureAddProductAddsProduct() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        assertTrue(library.addProduct(product));
        
        assertTrue(library.getID().containsKey(product));
    }
    
    @Test
    public void ensureAddProductDoesNotAddDuplicateProduct() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        assertFalse(library.addProduct(product));
        
        assertTrue(library.getID().containsKey(product));
        
        assertEquals(library.getID().size(), 1);
    }
    
    @Test
    public void ensureRemoveProductRemovesExistingProduct() {
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Product product2 = new Product("Beer", "Sagres", new SKU("87554395"));
        
        library.addProduct(product);
        library.addProduct(product2);
        
        assertTrue(library.removeProduct(product2));
    }
    
    @Test
    public void ensureRemoveProductDoesNotRemoveNotAddedProduct() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Product product2 = new Product("Beer", "Sagres", new SKU("87554395"));
        
        library.addProduct(product);
        
        assertFalse(library.removeProduct(product2));
    }
    
    @Test
    public void ensureAddTemplateAddsTemplate() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertTrue(library.addTemplate(product, template));
        
        assertTrue(library.getID().get(product).containsTemplate(template));
    }
    
    @Test
    public void ensureAddTemplateDoesNotAddDuplicateTemplate() {
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addTemplate(product, template);
        
        assertFalse(library.addTemplate(product, template));
        
        assertTrue(library.getID().get(product).containsTemplate(template));
    }
    
    @Test
    public void ensureAddTemplateDoesNotAddTemplateIfProductHasNotBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertFalse(library.addTemplate(product, template));
        
        assertNull(library.getID().get(product));
    }
    
    @Test
    public void ensureRemoveTemplateRemovesExistingTemplate() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addTemplate(product, template);
        
        assertTrue(library.removeTemplate(product, template));
        
        assertFalse(library.getID().get(product).containsTemplate(template));
    }
    
    @Test
    public void ensureRemoveTemplateDoesNotRemoveTemplateIfProductHasNotBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertFalse(library.removeTemplate(product, template));
    }
    
    @Test
    public void ensureRemoveTemplateDoesNotRemoveNotAddedTemplate() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertFalse(library.removeTemplate(product, template));
        
        assertFalse(library.getID().get(product).containsTemplate(template));
    }
    
    @Test
    public void ensureDoesProductExistReturnsTrueIfProductHasBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        assertTrue(library.doesProductExist(product));
    }
    
    @Test
    public void ensureDoesProductExistReturnsFalseIfProductHasNotBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        assertFalse(library.doesProductExist(product));
    }
    
    @Test
    public void ensureDoesTemplateExistReturnsTrueIfTemplateHasBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        
        library.addTemplate(product, template);
        
        assertTrue(library.doesTemplateExist(product, template));
    }
    
    @Test
    public void ensureDoesTemplateExistReturnsFalseIfProductHasNotBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertFalse(library.doesTemplateExist(product, template));
    }
    
    @Test
    public void ensureDoesTemplateExistReturnsFalseIfTemplateHasNotBeenAdded() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        Template template = new SimpleTemplate("Simple Template");
        
        assertFalse(library.doesTemplateExist(product, template));
    }
    
    @Test
    public void ensureHashCodeIsEqualIfLibrariesAreEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        ProductTemplatesLibrary library2 = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library2.addProduct(product);
        
        library.addTemplate(product, template);
        library2.addTemplate(product, template);
        
        assertEquals(library.hashCode(), library2.hashCode());
    }
    
    @Test
    public void ensureHashCodeIsNotEqualIfLibrariesAreNotEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        ProductTemplatesLibrary library2 = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library2.addProduct(product);
        
        library.addTemplate(product, template);
        
        assertNotEquals(library.hashCode(), library2.hashCode());
    }
    
    @Test
    public void ensureHashCodeMutationsAreKilled() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        int hashCode = 3;
        hashCode = 29 * hashCode + library.getID().hashCode();
        
        assertEquals(library.hashCode(), hashCode);
        
        assertNotEquals("".hashCode(), library.hashCode());
    }
    
    @Test
    public void ensureLibrariesAreEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        ProductTemplatesLibrary library2 = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library2.addProduct(product);
        
        library.addTemplate(product, template);
        library2.addTemplate(product, template);
        
        assertEquals(library, library2);
    }
    
    @Test
    public void ensureLibrariesAreNotEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        ProductTemplatesLibrary library2 = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library2.addProduct(product);
        
        library.addTemplate(product, template);
        
        assertNotEquals(library, library2);
    }
    
    @Test
    public void ensureSameReferenceIsEqual() {
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library.addTemplate(product, template);
        
        ProductTemplatesLibrary library2 = library;
        
        assertEquals(library, library2);
    }
    
    @Test
    public void ensureNullIsNotEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library.addTemplate(product, template);
        
        ProductTemplatesLibrary library2 = null;
        
        assertFalse(library.equals(library2));
    }
    
    @Test
    public void ensureOtherObjectIsNotEqual() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addProduct(product);
        library.addTemplate(product, template);
        
        assertFalse(library.equals(product));
    }
    
    @Test
    public void ensureGetIDWorks() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        Product product2 = new Product("Beer", "Sagres", new SKU("87554395"));
        
        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");
        
        library.addProduct(product);
        library.addProduct(product2);
        
        library.addTemplate(product, template);
        library.addTemplate(product2, template2);
        
        Map<Product, TemplateGroup> expResult = new HashMap<>();
        
        expResult.put(product, new TemplateGroup(product.getID() + " Template Group"));
        expResult.get(product).addTemplate(template);
        
        expResult.put(product2, new TemplateGroup(product2.getID() + " Template Group"));
        expResult.get(product2).addTemplate(template2);
        
        Map<Product, TemplateGroup> result = library.getID();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void ensureProductTemplateSetReturnsNull() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        assertNull(library.productTemplateSet(product));
    }
    
    @Test
    public void ensureProductTemplateSetReturns() {
        
        ProductTemplatesLibrary library = new ProductTemplatesLibrary();
        
        Product product = new Product("Water", "Luso", new SKU("123245"));
        
        library.addProduct(product);
        
        assertTrue(library.productTemplateSet(product).isEmpty());
        
        Template template = new SimpleTemplate("Simple Template");
        
        library.addTemplate(product, template);
        
        assertTrue(library.productTemplateSet(product).contains(template));
    }
}
