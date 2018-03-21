package cdioil.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Representa um Produto presente numa Categoria da Estrutura Mercadologica.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Produto implements Serializable {

    /**
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PRODUTO")
    private Long id;

    /**
     * Nome do produto.
     */
    @Column(name = "NOME")
    private String nome;

    /**
     * Lista de códigos do produto.
     */
    @OneToMany
    List<Codigo> codigos = new ArrayList<>();

    /**
     * Construtor protegido apenas para uso de JPA.
     */
    protected Produto() {
    }

    /**
     * Constrói uma nova instância com um dado nome e 1 ou mais códigos.
     *
     * @param nome nome do produto
     * @param codigo codigo
     * @param codigos 0 ou mais codigos
     */
    public Produto(String nome, Codigo codigo, Codigo... codigos) {
        this.nome = nome;

        this.codigos.add(codigo);

        for (Codigo cod : codigos) {
            this.codigos.add(cod);
        }
    }

    /**
     * Descreve o Produto através dos seus atributos.
     *
     * @return a descrição textual do Produto.
     */
    @Override
    public String toString() {

        String result = String.format("Nome: %s\n", nome);

        result += "Códigos:\n";

        for (Codigo c : codigos) {

            result += c.getClass().getSimpleName() + " " + c.toString();
        }

        return result;
    }

    /**
     * Gera um índice através do código de barras do Produto.
     *
     * @return o código hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codigos);
        return hash;
    }

    /**
     * Compara o Produto com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem o mesmo código de barras. Caso
     * contrário, retorna false
     */
    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codigos, other.codigos)) {
            return false;
        }
        return true;
    }

}
