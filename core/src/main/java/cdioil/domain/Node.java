package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

/**
 *
 * @author António Sousa [1161371]
 */
/**
 * Clase interna que representa um nó na estrutura mercadologica.
 * <p>
 * Um nó contém uma categoria, assim como referencias para outros nós.
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
     * Reference to a preceding Node in the structure.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Node parent;

    /**
     * A list of references to following Nodes in the structure.
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Node> children = new LinkedList<>();

    /**
     * The element contained in this instance.
     */
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Category element;

    /**
     * Empty Constructor for JPA.
     */
    protected Node() {
    }

    /**
     * Instantiates a Node with a given parent Node and element.
     *
     * @param pai
     * @param elemento
     */
    protected Node(Node pai, Category elemento) {
        this.parent = pai;
        this.element = elemento;
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
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Adiciona um node ao conjunto de children deste node.
     *
     * @param filho node que se pretende adicionar aos children
     * @return true - caso tenha sido possível adicionar ao conjunto de
     * children<p>
     * false - caso contrario
     */
    public boolean addChild(Node filho) {
        if (getChildren().contains(filho)) {
            return false;
        }
        return children.add(filho);
    }

    /**
     * Gera um índice a partir da Category do Node.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.parent);
        hash = 97 * hash + Objects.hashCode(this.element);
        return hash;
    }

    /**
     * Compara o Node com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem a mesma Category. Caso
     * contrário, retorna false
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
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.element, other.element)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "elemento=" + element + '}';
    }

}
