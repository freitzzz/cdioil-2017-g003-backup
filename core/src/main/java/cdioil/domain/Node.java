/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Node que se situa acima do node atual na estrutura.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Node pai;

    /**
     * Todos os nodes que se encontrem abaixo do atual.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Node> filhos = new LinkedList<>();

    /**
     * Category de produtos contida no node.
     */
    private Category elemento;

    /**
     * Construtor apenas para uso do JPA.
     */
    protected Node() {
    }

    protected Node(Node pai, Category elemento) {
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
     * Retorna a Category presente neste node.
     *
     * @return a categoria contida no node
     */
    public Category getElemento() {
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
     * Gera um índice a partir da Category do Node.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.elemento);
        return hash;
    }

    /**
     * Compara o Node com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem a mesma Category. Caso
 contrário, retorna false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Node other = (Node) obj;
        if (!Objects.equals(this.elemento, other.elemento)) {
            return false;
        }
        return true;
    }

}
