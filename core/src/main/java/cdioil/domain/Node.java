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
    private Node pai;

    /**
     * A list of references to following Nodes in the structure.
     */
    @OneToMany(mappedBy = "pai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Node> filhos = new LinkedList<>();

    /**
     * The element contained in this instance.
     */
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Categoria elemento;

    /**
     * Empty Constructor for JPA.
     */
    protected Node() {
    }

    /**
     * Instantiates a Node with a given parent Node and element.
     * @param pai
     * @param elemento 
     */
    protected Node(Node pai, Categoria elemento) {
        this.pai = pai;
        this.elemento = elemento;
    }

    /**
     * Retorna o Node pai.
     *
     * @return Node pai
     */
    public Node getPai() {
        return pai;
    }

    /**
     * Retorna a Categoria presente neste node.
     *
     * @return a categoria contida no node
     */
    public Categoria getElemento() {
        return elemento;
    }

    /**
     * Retorna o conjunto de nodes filhos deste node.
     *
     * @return conjunto de nodes filhos
     */
    public List<Node> getFilhos() {
        return filhos;
    }

    /**
     * Adiciona um node ao conjunto de filhos deste node.
     *
     * @param filho node que se pretende adicionar aos filhos
     * @return true - caso tenha sido possível adicionar ao conjunto de
     * filhos<p>
     * false - caso contrario
     */
    public boolean addFilho(Node filho) {
        if (getFilhos().contains(filho)) {
            return false;
        }
        return filhos.add(filho);
    }

    /**
     * Gera um índice a partir da Categoria do Node.
     *
     * @return o valor de hash gerado
     */
    @Override    
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.pai);
        hash = 97 * hash + Objects.hashCode(this.elemento);
        return hash;
    }

    /**
     * Compara o Node com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem a mesma Categoria. Caso
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
        if (!Objects.equals(this.pai, other.pai)) {
            return false;
        }
        if (!Objects.equals(this.elemento, other.elemento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "elemento=" + elemento + '}';
    }



    

}
