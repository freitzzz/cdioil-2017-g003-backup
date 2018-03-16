package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
     * Código de barras do produto.
     */
    private EAN codigoBarras;

    /**
     * Código QR do produto.
     */
    private CodigoQR codigoQR;

    /**
     * Preço unitário do produto.
     */
    @Column(name = "PRECOUNITARIO")
    private Preco precoUnitario;

    /**
     * Construtor protegido apenas para uso de JPA.
     */
    protected Produto() {
    }

    /**
     * Constrói uma nova instância com um dado nome, preço unitário e código de barras.
     *
     * @param nome nome do produto
     * @param preco preço unitário do produto
     * @param codBarras código de barras
     */
    public Produto(String nome, Preco preco, EAN codBarras) {
        this.nome = nome;
        this.precoUnitario = preco;
        this.codigoBarras = codBarras;
        this.codigoQR = null;
    }

    /**
     * Constrói uma nova instância com um dado nome, preço unitário, código de barras e código QR.
     *
     * @param nome nome do produto
     * @param preco preço unitário do produto
     * @param codBarras código de barras
     * @param codQR código QR
     */
    public Produto(String nome, Preco preco, EAN codBarras, CodigoQR codQR) {
        this.nome = nome;
        this.precoUnitario = preco;
        this.codigoBarras = codBarras;
        this.codigoQR = codQR;
    }

    /**
     * Descreve o Produto através dos seus atributos.
     *
     * @return a descrição textual do Produto.
     */
    @Override
    public String toString() {
        return String.format("Nome: %s\nPreço: %s\nCódigo de Barras: \n%sCódigo QR: \n%s",
                nome, precoUnitario, codigoBarras.toString(), codigoQR != null ? codigoQR.toString()
                : "Sem código QR\n");
    }

    /**
     * Gera um índice através do código de barras do Produto.
     *
     * @return o código hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codigoBarras);
        return hash;
    }

    /**
     * Compara o Produto com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem o mesmo código de barras. Caso contrário, retorna false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codigoBarras, other.codigoBarras)) {
            return false;
        }
        return true;
    }

}
