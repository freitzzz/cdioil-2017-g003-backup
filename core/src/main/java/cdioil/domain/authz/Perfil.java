/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import cdioil.domain.Avaliacao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Classe Perfil. Representa o perfil de um utilizador registado
 *
 * @author João
 */
@Entity
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * informação sobre o perfil do utilizador
     */
    private String informacao;
    /**
     * lista de avaliações feitas pelo utilizador
     */
    @OneToMany
    private List<Avaliacao> avaliacoes;

    /**
     * lista de badges do utilizador
     */
    //private List<Badge> badges;
    /**
     * lista de sugestões do utilizador
     */
    //private List<Sugestao> sugestoes;
    /**
     * construtor vazio da classe Perfil
     */
    protected Perfil() {
    }

    /**
     * Adiciona uma avaliação à lista de avaliações do perfil
     *
     * @param a avaliação a adicionar
     * @return true se a avaliação for adicionada com sucesso, false se não for
     * adicionada
     */
    public boolean adicionarAvaliacao(Avaliacao a) {
        return avaliacoes.add(a);
    }

    /**
     * Adiciona uma sugestão à lista de avaliações do perfil
     *
     * @param s sugestão a adicionar
     * @return true se a sugestão for adicionada com sucesso, false se não for
     * adicionada
     */
    /*public boolean adicionarSugestao(Sugestao s) {
        return sugestoes.add(s);
    }*/
    /**
     * Muda a informacao do perfil
     *
     * @param novaInformacao nova informacao do perfil
     */
    public void mudarInformacao(String novaInformacao) {
        informacao = novaInformacao;
    }

    /**
     * Hash code de um perfil
     *
     * @return hash code do perfil
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * verifica se dois perfis são iguais baseado nos id de cada um
     *
     * @param object objeto a ser comparado
     * @return true se ambos forem iguais, false se forem diferentes
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * devolve uma string com a informação relativa ao perfil de utilizador
     *
     * @return string com a informação relativa ao perfil de utilizador
     */
    @Override
    public String toString() {
        return "org.grupo3.cdioil.isep.feedback_monkey.domain.Perfil[ id=" + id + " ]";
    }

}
