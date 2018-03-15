package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Representa um inquerito de um produto.
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Inquerito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @GeneratedValue
    /**
     * ID da base de dados
     */
    private int id;

    /**
     * Produto associado ao inquerito
     */
    private Produto produto;

    /**
     * Lista de questoes do inquerito
     */
    private List<Questao> listaQuestoes;

    /**
     * Constroi uma instancia de inquerito recebendo um produto e uma lista de
     * questoes
     *
     * @param produto produto associado ao inquerito
     * @param listaQuestoes questoes do inquerito
     */
    public Inquerito(Produto produto, List<Questao> listaQuestoes) {
        this.produto = produto;
        this.listaQuestoes = listaQuestoes;
    }

    protected Inquerito() {
        //Para ORM
    }

    /**
     * Hash code do inquerito
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return produto.hashCode() + listaQuestoes.hashCode();
    }

    /**
     * Verifica se duas instancias de Inquerito sao iguais
     *
     * @param obj objeto a comparar
     * @return true se as instancias forem iguais, false se não
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Inquerito)) {
            return false;
        }
        final Inquerito other = (Inquerito) obj;
        if (!this.produto.equals(other.produto)) {
            return false;
        }
        return this.listaQuestoes.equals(other.listaQuestoes);
    }

    /**
     * Devolve uma descricao do inquerito (Produto e lista de questoes)
     *
     * @return descricao do inquerito
     */
    @Override
    public String toString() {
        return "Inquerito sobre o produto:\n" + produto.toString()
                + "\nLista de Questões:\n" + listaQuestoes.toString();
    }

}
