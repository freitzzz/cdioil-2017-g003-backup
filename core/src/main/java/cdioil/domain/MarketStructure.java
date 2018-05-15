package cdioil.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Class representing a Market Structure which is composed of various product Categories, of which only the leaves have Products.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class MarketStructure implements Serializable {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MKTSTRUCT_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database Version number.
     */
    @Version
    private Long version;

    /**
     * The Market Structure's first Node.
     */
    @OneToOne(cascade = {CascadeType.ALL})
    private Node root;

    /**
     * The number of nodes/categories in the market structure.
     */
    private int marketSize;

    /**
     * Public Constructor for instantiating a Market Structure.<p>
     * Also doubles as the JPA's default constructor since no parameters are given.
     */
    public MarketStructure() {

        root = new Node(new Category("Todos os Produtos", "RAIZ"));
        marketSize = 1;
    }

    /**
     * Removes the given Category from the Market Structure.
     *
     * @param c the Category to be removed
     */
    public void removeCategory(Category c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Update the product in Market Structure
     *
     * @param cat Category
     * @param pro Product
     */
    public void updateProduct(Category cat, Product pro) {

        Iterator<Product> iterator = cat.getProductSetIterator();

        while (iterator.hasNext()) {
            Product removable = iterator.next();
            if (removable.equals(pro)) {
                cat.removeProduct(removable);
                cat.addProduct(pro);
                break;
            }
        }
    }

    /**
     * Adds a given <code>Category</code> to the <code>MarketStructure</code>.
     *
     * @param c <code>Category</code> to be added.
     * @return true - if the new Category's parent has also been inserted into the <code>MarketStructure</code> and has yet been inserted into the parent's set of children<p>
     * false - if the parent is not in the <code>MarketStructure</code> or the parent's set of children already contains the category
     */
    public boolean addCategory(Category c) {
        if (c == null) {
            throw new IllegalArgumentException("O argumentos não podem ser null");
        }

        Node parentNode = searchParentNode(c);

        if (parentNode != null && parentNode.addChild(new Node(c))) {
                marketSize++;
                return true;
        }
        return false;
    }

    /**
     * Searches for the instance of <code>Node</code> that contains the parent <code>Category</code> of the given <code>Category</code>.
     *
     * @param c - instance of <code>Category</code>
     * @return the <code>Node</code> containing the parent <code>Category</code>
     */
    private Node searchParentNode(Category c) {

        //Check if c's identifier is equal to "RAIZ"
        if (root.getElement().categoryIdentifier().equals(c.categoryIdentifier())) {
            return null;
        }

        LinkedList<String> parentPathIdentifiers = new LinkedList<>(c.categoryPathIdentifiers());
        parentPathIdentifiers.removeLast();

        //The list will only be empty if it's DC category
        if (parentPathIdentifiers.isEmpty()) {
            return root;
        }

        return searchNodeByPathIdentifiers(parentPathIdentifiers);
    }

    /**
     * Retrieve the <code>Node</code> containing a given <code>Category</code>.
     *
     * @param c instance of <code>Category</code> to search for.
     * @return <code>Node</code> containing the given <code>Category</code>.
     */
    private Node searchNode(Category c) {

        //Check if c's identifier is equal to "RAIZ"
        if (root.getElement().categoryIdentifier().equals(c.categoryIdentifier())) {
            return root;
        }

        return searchNodeByPathIdentifiers(new LinkedList<>(c.categoryPathIdentifiers()));
    }

    /**
     * Uses a stack of Category path identifiers for searching for a Category in the <code>MarketStructure</code>
     *
     * @param stack stack of <code>String</code>
     * @return <code>Node</code> if it has been inserted in the <code>MarketStructure</code> otherwise null
     */
    private Node searchNodeByPathIdentifiers(LinkedList<String> stack) {

        Node node = root;

        boolean hasParent = true;

        while (!stack.isEmpty() && hasParent) {

            String identifier = stack.removeFirst();

            Set<Node> children = node.getChildren();

            if (children.isEmpty()) {
                hasParent = false;
                break;
            }

            for (Node child : children) {
                if (child.getElement().categoryIdentifier().equals(identifier)) {
                    node = child;
                    hasParent = true;
                    break;
                }
                hasParent = false;
            }
        }

        return hasParent ? node : null;
    }

    /**
     * Checks if the given Node is a leaf in the Market Structure.<p>
     * A Node is a leaf when it does not hold any references to child Nodes, thus being capable of holding Products.
     *
     * @param node
     * @return true if node does not possess any children<p>
     * false otherwise
     */
    private boolean isLeaf(Node node) {
        return node.getChildren().isEmpty();
    }

    /**
     * Returns a collection of all the leaf Categories in the Market Structure.
     *
     *
     * @return an iterable containing all the Categories on the bottom of the Market Structure
     */
    public Iterable<Category> getLeaves() {

        List<Category> leaves = new LinkedList<>();

        searchLeaves(leaves, root);

        return leaves;
    }

    /**
     * Recursively searches for all the nodes in the the Market Structure and adds their elements to the list.
     *
     * @param leaves list of leaf Categories
     * @param node the current node
     */
    private void searchLeaves(List<Category> leaves, Node node) {

        if (isLeaf(node)) {
            leaves.add(node.getElement());
            return;
        }

        Set<Node> children = node.getChildren();

        for (Node child : children) {

            searchLeaves(leaves, child);
        }
    }

    //TODO: what happens to the products added to a node that was previously a leaf, but no longer is?
    /**
     * Adds the given Product to the given Category, if that Category is a Market Structure leaf.
     *
     * @param p
     * @param c
     * @return true - if the Category is a Market Structure leaf and does not already contain an equal Product<p>
     * false - if the Category does not exist in the Market Structure or the Category already holds an equal Product
     */
    public boolean addProduct(Product p, Category c) {

        if (p == null || c == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node node = searchNode(c);

        if (node != null) {
            if (isLeaf(node)) {
                //Add to the category inside the structure
                return node.getElement().addProduct(p);
            }
        }
        return false;
    }

    /**
     * Returns the number of categories/nodes in the Market Structure.
     *
     * @return the Market Structure's size
     */
    public int size() {
        return marketSize;
    }

    /**
     * Checks if two Categories are directly connected.
     *
     * @param c1 directly connected category
     * @param c2 directly connected category
     * @return true - if both nodes are directly connected to each other<p>
     * false - if either of the categories do not exist in the market structure or there is not a direct connection between them
     */
    public boolean checkDirectlyConnected(Category c1, Category c2) {

        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node node1 = searchNode(c1);

        if (node1 == null) {
            return false;
        }

        Node node2 = searchNode(c2);

        if (node2 == null) {
            return false;
        }

        //Check if either of them are root
        if (node1.getParent() == null) {
            return node2.getParent().equals(node1);
        }

        if (node2.getParent() == null) {
            return node1.getParent().equals(node2);
        }

        return node2.getParent().equals(node1) || node1.getParent().equals(node2);
    }

    /**
     * Lists all the categories in the structure, call recursive method of the same name
     *
     * @return list of the categories in the structure
     */
    public List<Category> getAllCategories() {
        List<Category> lc = new LinkedList<>();
        getAllCategories(lc, root);
        lc.remove(root.getElement());

        return lc;
    }

    /**
     * Recursive getAllCategories, adds Category in node to the list
     *
     * @param lc list of categories to fill
     * @param node current node
     */
    private void getAllCategories(List<Category> lc, Node node) {
        lc.add(node.getElement());

        for (Node child : node.getChildren()) {
            getAllCategories(lc, child);
        }
    }
}
