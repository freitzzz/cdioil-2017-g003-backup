package cdioil.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class MarketStructureTest {
    
    public MarketStructureTest() {
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureAddCategoryDoesNotAllowNullParameters() {
        
        MarketStructure struct = new MarketStructure();
        
        struct.addCategory(null);
    }
    
    @Test
    public void ensureCategoryCanNotBeAddedIfPathElementsHaveNotBeenAdded() {
        
        MarketStructure struct = new MarketStructure();
        
        Category c = new Category("Bricolage", "10DC-10UN-100CAT");

        assertFalse("This category should not be added since 10DC nor 10UN have been added.", struct.addCategory(c));

        assertEquals("Market Structure should have size equal to 1 since no other category besides the root exists.", 1, struct.size());
    }
    
    @Test
    public void ensureCategoryCanBeAddedIfPathElementsHaveBeenAdded(){
        
        MarketStructure struct = new MarketStructure();

        Category fakeRoot = new Category("All products", "RAIZ");

        assertFalse(struct.addCategory(fakeRoot));

        Category c = new Category("Nome 1", "10DC");
        
        assertTrue(struct.addCategory(c));
        
        Category c1 = new Category("Nome 2", "10DC-10UN");
        
        assertTrue(struct.addCategory(c1));

        //assertTrue(struct.checkDirectlyConnected(c, c1));
        
        Category c2 = new Category("Nome 3", "10DC-10UN-1000CAT");
        
        assertTrue(struct.addCategory(c2));

        //assertTrue(struct.checkDirectlyConnected(c1, c2));

        assertEquals("Size should be 4 since fakeRoot was not added", 4, struct.size());
    }


    @Test
    public void ensureAddCategoryWorks(){

        MarketStructure struct = new MarketStructure();

        for(int i = 0; i < 5; i++){

            String path = "1" + i + "DC";

            Category parent = new Category("Parent " + (i+1), path);

            struct.addCategory(parent);

            for(int j = 0; j < 2; j++){
                Category child = new Category("Child " + i + (j+1), path + "-1"+j+"UN");

                struct.addCategory(child);
            }
        }

        assertEquals("Market Structure's size should be 16", 16, struct.size());

        Category c = new Category("Category", "10DC");

        assertFalse("This category should not be added since an equal one has already been added.", struct.addCategory(c));
    }
    
    @Test
    public void ensureGetLeavesWorks() {
        
        MarketStructure struct = new MarketStructure();

        List<Category> expected = new LinkedList<>();

        Category root = new Category("Todos os Produtos", "RAIZ");

        expected.add(root);

        assertEquals("The Market Structure's leaves should only contain the root itself", expected, struct.getLeaves());


        Category c = new Category("Alimentar", "10DC");

        expected.clear();
        expected.add(c);

        struct.addCategory(c);

        assertEquals("The new category should be a leaf", expected, struct.getLeaves());

        Category c1 = new Category("Peixaria&Talho", "11DC");

        expected.clear();
        expected.add(c);
        expected.add(c1);

        struct.addCategory(c1);

        assertEquals("The new category should also be a leaf", expected, struct.getLeaves());

        Category c2 =  new Category("Vinho e Espirutuosas", "10DC-17UN");

        expected.clear();
        expected.add(c2);
        expected.add(c1);

        struct.addCategory(c2);

        assertEquals("There should only be two leaves", expected, struct.getLeaves());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCheckDirectlyConnectedDoesNotAllowNullParameters() {

        MarketStructure struct = new MarketStructure();

        struct.checkDirectlyConnected(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCheckDirectlyConnectedDoesNotAllowFirstParameterToBeNull(){

        MarketStructure struct = new MarketStructure();

        struct.checkDirectlyConnected(null, new Category("Category 2", "10DC"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCheckDirectlyConnectedDoesNotAllowSecondParameterToBeNull(){

        MarketStructure struct = new MarketStructure();

        struct.checkDirectlyConnected(new Category("Category 2", "10DC"), null);
    }

    @Test
    public void ensureNotInsertedCategoriesAreNotDirectlyConnected(){

        MarketStructure struct = new MarketStructure();

        Category c1 = new Category("Category 1", "10DC");

        Category c2 = new Category("Category 2", "11DC");

        assertFalse(struct.checkDirectlyConnected(c1,c2));

        struct.addCategory(c1);

        assertFalse(struct.checkDirectlyConnected(c1,c2));
    }


    @Test
    public void ensureThatSameCategoryIsNotDirectlyConnected() {

        MarketStructure struct = new MarketStructure();

        Category c = new Category("Higiene", "10DC");

        struct.addCategory(c);

        assertFalse(struct.checkDirectlyConnected(c,c));
    }


    @Test
    public void ensureCheckDirectlyConnectedWorks(){

        MarketStructure structure = new MarketStructure();

        Category c1 = new Category("Category 1", "10DC");

        Category c2 = new Category("Category 2", "10DC-15UN");


        structure.addCategory(c1);
        structure.addCategory(c2);

        assertTrue(structure.checkDirectlyConnected(c1,c2));


        Category root = new Category("Todos os Produtos", "RAIZ");

        assertTrue(structure.checkDirectlyConnected(root, c1));

        assertTrue(structure.checkDirectlyConnected(c1, root));


        Category c3 = new Category("Category 3", "10DC-15UN-20CAT");
        structure.addCategory(c3);

        assertTrue(structure.checkDirectlyConnected(c2,c3));
        assertTrue(structure.checkDirectlyConnected(c3,c2));
        assertFalse(structure.checkDirectlyConnected(c1,c3));
        assertFalse(structure.checkDirectlyConnected(c3,c1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void ensureAddProductDoesNotAllowNullParameters() {

        MarketStructure struct = new MarketStructure();

        struct.addProduct(null, null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void ensureAddProductDoesNotAllowNullCategory(){

        MarketStructure struct =  new MarketStructure();

        struct.addProduct(new Product("New Product", new SKU("3423432"), "1 L"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAddProductDoesNotAllowNullProduct(){

        MarketStructure struct = new MarketStructure();

        struct.addProduct(null, new Category("Category", "10DC"));
    }


    @Test
    public void ensureAddProductDoesNotWorkForNotAddedCategories(){

        MarketStructure structure = new MarketStructure();

        Category c = new Category("Category", "10DC");

        Product p = new Product("Product", new SKU("1421213"), "1 L");

        assertFalse(structure.addProduct(p, c));
    }

    @Test
    public void ensureAddProductOnlyAddsToLeaves() {

        MarketStructure struct = new MarketStructure();

        Category c1 = new Category("Parent Category", "10DC");

        struct.addCategory(c1);

        Category c2 = new Category("Child Category", "10DC-10UN");

        struct.addCategory(c2);

        Product p = new Product("Produto", new SKU("1"), "1 L");

        assertFalse("Can't add product, since the category is no longer a leaf", struct.addProduct(p, c1));

        assertTrue("Since the category is a leaf the product can be added", struct.addProduct(p, c2));


        //TODO: what happens to the products added to a node that was previously a leaf, but no longer is?
    }

    /**
     * Test of getAllCategories method, of class MarketStructure
     */
    @Test
    public void testGetAllCategories() {
        System.out.println("getAllCategories");
        MarketStructure struct = new MarketStructure();

        Category c1 = new Category("Alimentar", "10DC");
        struct.addCategory(c1);
        Category c2 = new Category("Bens Essenciais", "20DC");
        struct.addCategory(c2);
        Category c3 = new Category("Gorduras Liquidas", "10DC-10UN");
        struct.addCategory(c3);
        Category c4 = new Category("Sub-Categoria", "10DC-10UN-100CAT");
        struct.addCategory(c4);
        Category c5 = new Category("Outra sub-categoria", "10DC-10UN-200CAT");
        struct.addCategory(c5);
        Category c6 = new Category("Pteras", "20DC-10UN");
        struct.addCategory(c6);

        List<Category> lc = new LinkedList<>();

        lc.add(c1);
        lc.add(c3);
        lc.add(c4);
        lc.add(c5);
        lc.add(c2);
        lc.add(c6);

        List<Category> categories = struct.getAllCategories();

        assertTrue(lc.equals(categories));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void ensureRemoveCategoryThrowsException(){
        new MarketStructure().removeCategory(new Category("Category", "10DC"));
    }
    
    @Test
    public void ensureUpdateProductUpdatesOldProduct(){
        Category cat = new Category("Cat","10938DC");
        
        MarketStructure struct = new MarketStructure();
        
        Product oldProduct = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));
        Product newProduct = new Product("ProdutoTeste 2.0", new SKU("544261234"), "1 L", new QRCode("4324235"));
        
        cat.addProduct(oldProduct);
        
        struct.addCategory(cat);
        
        Iterator<Product> oldProductSet = cat.getProductSetIterator();
        
        struct.updateProduct(cat, newProduct);
        
        Iterator<Product> newProductSet = cat.getProductSetIterator();
        
        assertNotEquals(oldProductSet,newProductSet);
    }
    
    @Test
    public void ensureUpdateProductDoesNotUpdateSameProduct(){
        Category cat = new Category("Cat","10938DC");
        
        MarketStructure struct = new MarketStructure();
        
        Product sameProduct = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));
        
        cat.addProduct(sameProduct);
        
        struct.addCategory(cat);
        
        Iterator<Product> oldProductSet = cat.getProductSetIterator();
        
        struct.updateProduct(cat, sameProduct);
        
        Iterator<Product> newProductSet = cat.getProductSetIterator();
        
        assertNotEquals(oldProductSet,newProductSet);
    }
    
    @Test
    public void ensureUpdateProductMutationsAreKilled(){
        Category cat = new Category("Cat","10938DC");
        
        MarketStructure struct = new MarketStructure();
        
        struct.addCategory(cat);
        
        //Kill while negated conditional mutator
        struct.updateProduct(cat, null);
        
        //How to kill if negated conditional mutator?
    }

    @Test
    public void ensureReturnsDirectChildrenCategories() {
        MarketStructure mk = new MarketStructure();
        Category cat1 = new Category("Cat1", "10DC");
        Category cat2 = new Category("Cat2", "10DC-10UN");
        Category cat3 = new Category("Cat3", "10DC-11UN");
        Category cat4 = new Category("Cat4", "10DC-12UN");
        Category cat5 = new Category("Cat5", "10DC-12UN-100CAT");

        mk.addCategory(cat1);
        mk.addCategory(cat2);
        mk.addCategory(cat3);
        mk.addCategory(cat4);
        mk.addCategory(cat5);

        List<Category> cat1DirectChildren = Arrays.asList(cat2, cat3, cat4);
        assertEquals(mk.getDirectChildren(cat1), cat1DirectChildren);

        List<Category> cat4DirectChildren = Arrays.asList(cat5);
        assertEquals(mk.getDirectChildren(cat4), cat4DirectChildren);

        Category nonExistantCat = new Category("null cat", "12DC");
        assertTrue(mk.getDirectChildren(nonExistantCat) == null);
    }
}
