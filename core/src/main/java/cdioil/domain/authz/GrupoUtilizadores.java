package cdioil.domain.authz;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representa um grupo de utilizadores.
 *
 * @author Rita Gonçalves (1160912)
 */
@Entity
public class GrupoUtilizadores implements Serializable {

    /**
     * Identificador do grupo de utilizadores.
     */
    @Column(name = "ID_GRUPOUSERS")
    @Id
    private int id;

    /**
     * Gestor que criou o grupo.
     */
    private Gestor g;

    /**
     * Lista com os utilizadores do grupo.
     */
    @ElementCollection
    private List<UserRegistado> users;

    /**
     * Constrói uma instância de GrupoUtilizadores, recebendo o Gestor que o criou.
     *
     * @param g Gestor que criou o GrupoUtilizadores
     */
    public GrupoUtilizadores(Gestor g) {
        if (!isGestorValido(g)) {
            this.g = g;
            this.users = new LinkedList<>();
        } else {
            throw new IllegalArgumentException("A instância de Gestor é null");
        }
    }

    /**
     * Constrói uma instância vazia de GrupoUtilizadores (JPA).
     */
    protected GrupoUtilizadores() {
    }

    /**
     * Verifica se um Gestor é válido.
     *
     * @param g Gestor a verificar
     * @return true, caso o gestor seja válido. Caso contrário, retorna false
     */
    public boolean isGestorValido(Gestor g) {
        return g == null;
    }

    /**
     * Verifica se um UserRegistado pertence ao GrupoUtilizadores.
     *
     * @param u Utilizador a verificar
     * @return true, caso a lista de utilizadores contenha o user. Caso contrário, retorna false
     */
    public boolean isUserValido(UserRegistado u) {
        return users.contains(u);
    }

    /**
     * Adiciona um UserRegistado à lista de utilizadores do GrupoUtilizadores.
     *
     * @param u Utilizador a adicionar
     * @return true, se o UserRegistado for adicionado com sucesso. Caso contrário, retorna false
     */
    public boolean adicionarUser(UserRegistado u) {
        if (u == null || isUserValido(u)) {
            return false;
        }
        return users.add(u);
    }

    /**
     * Remove um UserRegistado da lista de utilizadores do GrupoUtilizadores.
     *
     * @param u Utilizador a remover
     * @return true, se o UserRegistado for removido com sucesso. Caso contrário, retorna false
     */
    public boolean removerUser(UserRegistado u) {
        if (u == null || !isUserValido(u)) {
            return false;
        }
        return users.remove(u);
    }

    /**
     * Lista os Utilizadores do GrupoUtilizadores e o seu criador.
     *
     * @return a descrição textual do GrupoUtilizadores
     */
    @Override
    public String toString() {
        return String.format("GESTOR RESPONSÁVEL:\n%s\nUSERS:\n", g, users);
    }

    /**
     * Gera um índice a partir do ID do GrupoUtilizadores.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

    /**
     * Compara o GrupoUtilizadores com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem o mesmo ID. Caso contrário, retorna false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GrupoUtilizadores other = (GrupoUtilizadores) obj;

        return this.id == other.id;
    }
}
