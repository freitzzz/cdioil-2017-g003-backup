package cdioil.domain;

import java.io.Serializable;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Class representing a Node in the Market Structure. A Node is composed of a
 * Category as well as references to its parent and its children.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Node implements Serializable {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @Column(name = "ID_NODE", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database Version number.
     */
    @Version
    private Long version;

    /**
     * Reference to a preceding Node in the structure.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Node parent;

    /**
     * A list of references to following Nodes in the structure.
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Node> children = new LinkedHashSet<>();

    /**
     * The element contained in this instance.
     */
    @OneToOne(cascade = {CascadeType.ALL})
    private Category element;

    /**
     * Empty Constructor for JPA.
     */
    protected Node() {
    }

    /**
     * Instantiates a Node with a given parent Node and element.
     *
     * @param element
     */
    protected Node(Category element) {
        this.element = element;
    }

    /**
     * Returns the parent Node.
     *
     * @return parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Returns the Category contained in this Node.
     *
     * @return element
     */
    public Category getElement() {
        return element;
    }

    /**
     * Returns a list with all the Node's child Nodes.
     *
     * @return child Nodes list
     */
    public Set<Node> getChildren() {
        return children;
    }

    /**
     * Adds a Node to the collection of children.
     *
     * @param child Node to be added to this instance's collection of child
     * Nodes
     * @return true - if child is not null nor is it contained in the collection
     * of child Nodes
     * <p>
     * false - otherwise
     */
    public boolean addChild(Node child) {

        if (child != null) {
            child.parent = this;
            return children.add(child);
        } else {
            return false;
        }
    }

    /**
     * Generates an hash value based on the Node's Category.
     *
     * @return the generated hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.element);
        return hash;
    }

    /**
     * Compares this instance to another Object.
     *
     * @param obj
     * @return true - if both are instances of Node and they hold an equal
     * Category<p>
     * false - if obj is not an instance of Node, nor does it hold an equal
     * Category
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.element, other.element);
    }

    @Override
    public String toString() {
        return "Node{" + "element=" + element + '}';
    }

}
