package cdioil.domain;

import cdioil.domain.authz.GrupoUtilizadores;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 * Representa um inquerito de um produto.
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Inquerito implements Serializable, Evento {

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
     * Product associado ao inquerito
     */
    private Product produto;
    /**
     * Utilizadores aos quais o Inquerito se destina.
     */
    private GrupoUtilizadores publicoAlvo;
    /**
     * Data de realização do Inquerito.
     */
    @Temporal(TemporalType.DATE)
    private Calendar data;

    /**
     * Lista de questoes do inquerito
     */
    private List<Question> listaQuestoes;

    /**
     * Constroi uma instancia de inquerito recebendo um produto e uma lista de questoes
     *
     * @param produto produto associado ao inquerito
     * @param data  Data de realização do inqueito
     * @param publicoAlvo Utilizadores aos quais o Inquerito se destina
     */
    public Inquerito(Product produto, Calendar data, GrupoUtilizadores publicoAlvo) {
        if (produto == null) {
            throw new IllegalArgumentException("O inquérito tem que ter um produto");
        }
        this.produto = produto;
        this.listaQuestoes = new LinkedList<>();
        this.publicoAlvo = publicoAlvo;
        if (data == null) {
            throw new IllegalArgumentException("O inquérito tem que ter uma data");
        }
        this.data = data;
    }

    protected Inquerito() {
        //Para ORM
    }

    /**
     * Adiciona uma questão à lista de questões.
     *
     * @param questao Questão a adicionar
     * @return true, se for adicionada com sucesso. Caso contrário, retorna false
     */
    public boolean adicionarQuestao(Question questao) {
        if (questao == null || isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.add(questao);
    }

    /**
     * Remove uma questão da lista de questões.
     *
     * @param questao Questão a remover
     * @return true, se for removida com sucesso. Caso contrário, retorna false
     */
    public boolean removerQuestao(Question questao) {
        if (questao == null || !isQuestaoValida(questao)) {
            return false;
        }
        return listaQuestoes.remove(questao);
    }

    /**
     * Verifica se uma questão já existe na lista de questões.
     *
     * @param questao Questão a verificar
     * @return true, se já existir na lista. Caso contrário, retorna false
     */
    public boolean isQuestaoValida(Question questao) {
        return listaQuestoes.contains(questao);
    }

    /**
     * Devolve uma descricao do inquerito (Product e lista de questoes)
     *
     * @return descricao do inquerito
     */
    @Override
    public String toString() {
        return "Inquerito sobre o produto:\n" + produto.toString()
                + "\nData:\n" + data;
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
     * Devolve a informacao de um inquerito.
     *
     * @return info de um inquerito numa String
     */
    @Override
    public String info() {
        return toString();
    }

    /**
     * Devolve o grupo de utilizadores a quem o inquerito se destina
     *
     * @return grupo de utilizadores
     */
    @Override
    public GrupoUtilizadores publicoAlvo() {
        return publicoAlvo;
    }
}
