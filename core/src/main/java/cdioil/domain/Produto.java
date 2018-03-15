package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representa um Produto presente numa Categoria da Estrutura Mercadologica.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDPRODUTO")
    private Long id;

    /**
     * Nome do produto.
     */
    @Column(name = "NOME")
    private String nome;

    /**
     * Codigo de barras do produto.
     */
    private CodigoQR codigoBarras;

    /**
     * Preco unitario do produto.
     */
    @Column(name = "PRECOUNITARIO")
    private Preco precoUnitario;

    /**
     * Construtor protegido apenas para uso de JPA.
     */
    protected Produto() {

    }

    /**
     * Constroi uma nova instancia com um dado nome e preco unitario
     *
     * @param nome nome do produto
     * @param preco preco unitario do produto
     */
    public Produto(String nome, Preco preco) {
        this.nome = nome;
        this.precoUnitario = preco;
    }

}
