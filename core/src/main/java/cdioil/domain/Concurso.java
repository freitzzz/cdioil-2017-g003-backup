package cdioil.domain;

import cdioil.domain.authz.GrupoUtilizadores;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;

/**
 * Representa um concurso que ocorre para um grupo de utilizadores da app
 * durante um período de tempo.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Concurso extends Event implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Id da base de dados para persistir concursos.
     */
    private Long id;

    /**
     * Descricao do concurso.
     */
    private String descricao;
    /**
     * Utilizadores aos quais o concurso se destina.
     */
    private GrupoUtilizadores publicoAlvo;
    /**
     * Data de abertura do concurso.
     */
    @Temporal(TemporalType.DATE)
    private Calendar dataInicio;
    /**
     * Data de fecho do concurso.
     */
    @Temporal(TemporalType.DATE)
    private Calendar dataFim;

    /**
     * Constroi uma instancia de Concurso recebendo uma descicao, um grupo de
     * utilizadores, uma data de inicio e uma data de fim
     *
     * @param descricao descricao do concurso
     * @param publicoAlvo grupo de utilizadores ao qual o concurso se destina
     * TODO discutir como identificar quando uma instancia de GrupoUtilizadores
     * se refere a TODOS os utilizadores da aplicação
     * @param dataInicio data de inicio do concurso
     * @param dataFim data de fim do concurso
     */
    public Concurso(String descricao, GrupoUtilizadores publicoAlvo,
            Calendar dataInicio, Calendar dataFim) {
        if (descricao == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma "
                    + "descrição.");
        }

        if (dataInicio == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma data"
                    + " de inicio.");
        }

        if (dataFim == null) {
            throw new IllegalArgumentException("O concurso tem que ter uma data"
                    + "de fecho.");
        }
        this.descricao = descricao;
        this.publicoAlvo = publicoAlvo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;

    }

    protected Concurso() {
        //Para ORM
    }

    /**
     * Hash code do concurso. TODO discutir sobre a identidade do concurso.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return descricao.hashCode();
    }

    /**
     * Verifica se duas instancias de Concurso sao iguais pelo seu id da base de
     * dados. TODO discutir sobre a identidade do concurso
     *
     * @param object instancia a verificar se é igual
     * @return verdadeiro se as duas instancias forem iguais, falso se não
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Concurso)) {
            return false;
        }
        Concurso other = (Concurso) object;
        return this.descricao.equals(other.descricao);
    }

    /**
     * Devolve a descricao do concurso.
     *
     * @return informacao de um concurso numa String
     */
    @Override
    public String toString() {
        return descricao;
    }

    /**
     * Devolve a informacao de um concurso.
     *
     * @return info de um concurso numa String
     */
    @Override
    public String info() {
        return toString();
    }

    /**
     * Devolve o grupo de utilizadores a quem o concurso se destina
     *
     * @return grupo de utilizadores
     */
    @Override
    public GrupoUtilizadores targetAudience() {
        return publicoAlvo;
    }

}
