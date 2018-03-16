package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
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
     * Constroi uma instancia de inquerito recebendo um produto e uma lista de questoes
     *
     * @param produto produto associado ao inquerito
     */
    public Inquerito(Produto produto) {
        this.produto = produto;
        this.listaQuestoes = new LinkedList<>();
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
    public boolean adicionarQuestao(Questao questao) {
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
    public boolean removerQuestao(Questao questao) {
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
    public boolean isQuestaoValida(Questao questao) {
        return listaQuestoes.contains(questao);
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
}
