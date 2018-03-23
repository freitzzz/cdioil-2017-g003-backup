package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Class representing a Market Structure which is composed of various product
 * Categories, of which only the leaves have Products.
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
     * The Market Structure's first Node.
     */
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Node root;

    /**
     * The number of nodes/categories in the market structure.
     */
    private int size;

    /**
     * Public Constructor for instantiating a Market Structure.<p>
     * Also doubles as the JPA's default constructor since no parameters are
     * given.
     */
    public MarketStructure() {

        root = new Node(null, new Category("Todos os Produtos", "RAIZ","RAIZ"));
        size = 1;
    }

    /**
     * Retrieves the topmost Node in the Market Structure.
     *
     * @return root
     */
    public Node getRoot() {
        return root;
    }

    //TODO improve name since, the usage of the term "root" may cause some confusion.
    /**
     * Adds a new main category to the market structure.
     *
     * @param c new category
     * @return true - if it is possible to add the given category<p>
     * false - othewise
     */
    public boolean addRootCategory(Category c) {
        if (c == null) {
            throw new IllegalArgumentException("O argumento não pode ser null");
        }

        return addCategory(root.getElement(), c);
    }

    /**
     * Adds a new Sub-Category to the given parent Category if that parent
     * exists.
     *
     * @param parent the already existing parent category
     * @param c the new category
     * @return true - if possible to add the new category<p>
     * false - otherwise
     */
    public boolean addCategory(Category parent, Category c) {
        if (parent == null || c == null) {
            throw new IllegalArgumentException("O argumentos não podem ser null");
        }

        //Ler: "Se o pai estiver na estrutura, mas o filho nao"
        Node parentNode = searchNode(root, parent);

        if (parentNode != null) {

            Node childNode = searchNode(parentNode, c);

            if (childNode == null) {
                size++;
                return parentNode.addChild(new Node(parentNode, c));
            }
        }
        return false;
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
     * Private recursive method used for searching for a given Category in the
     * Market Structure.
     *
     * @param node node from which the lookup is initiated (usually the root
     * Node)
     * @param c the Category to be searched
     * @return the Node in which the given Category is stored if found in the
     * Market Structure; null otherwise
     */
    private Node searchNode(Node node, Category c) {
        if (node == null) {
            return null;
        }

        if (node.getElement().equals(c)) {
            return node;
        }

        List<Node> children = node.getChildren();

        for (Node n : children) {
            Node filho = searchNode(n, c);

            //Prevents returns if the first child was unable to find the Category
            if (filho != null) {
                return filho;
            }
        }

        return null;
    }

    /**
     * Checks if the given Node is a leaf in the Market Structure.<p>
     * A Node is a leaf when it does not hold any references to child Nodes,
     * thus being capable of holding Products.
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
     * @return an iterable containing all the Categories on the bottom of the
     * Market Structure
     */
    public Iterable<Category> getLeaves() {

        List<Category> leaves = new LinkedList<>();

        searchLeaves(leaves, root);

        return leaves;
    }

    /**
     * Recursively searches for all the nodes in the the Market Structure and
     * adds their elements to the list.
     *
     * @param leaves list of leaf Categories
     * @param node the current node
     */
    private void searchLeaves(List<Category> leaves, Node node) {

        List<Node> filhos = node.getChildren();

        if (isLeaf(node)) {
            leaves.add(node.getElement());
            return;
        }

        for (Node filho : filhos) {

            searchLeaves(leaves, filho);
        }
    }

    /**
     * Adds the given Product to the given Category, if that Category is a
     * Market Structure leaf.
     *
     * @param p
     * @param c
     * @return true - if the Category is a Market Structure leaf and does not
     * already contain an equal Product<p>
     * false - if the Category does not exist in the Market Structure or the
     * Category already holds an equal Product
     */
    public boolean addProduct(Product p, Category c) {

        if (p == null || c == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node node = searchNode(root, c);

        if (node != null) {
            if (isLeaf(node)) {
                //adicionar à categoria dentro da estrutura e não à parametrizada
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
        return size;
    }

    /**
     * Checks if two Categories are directly connected.
     *
     * @param parent the parent Category
     * @param child the child Category
     * @return true - if both nodes are directly connected to eachother<p>
     * false - if either of the categories do not exist in the market structure
     * or there is not a direct connection between them
     */
    public boolean checkDirectlyConnected(Category parent, Category child) {

        if (parent == null || child == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node parentNode = searchNode(root, parent);

        Node childNode = searchNode(root, child);

        if (parentNode == null || childNode == null) {
            return false;
        }

        boolean isChild = false;

        for (Node n : parentNode.getChildren()) {
            if (n == childNode) {
                isChild = true;
                break;
            }
        }

        return childNode.getParent() == parentNode && isChild;
    }

}
