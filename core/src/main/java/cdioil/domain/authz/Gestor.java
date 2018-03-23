package cdioil.domain.authz;

import cdioil.domain.Category;
import cdioil.persistence.Identifiable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Gestor de Inquéritos
 *
 * Responsável pela criação e configuração de inquéritos de uma dada estrutura
 * mercadológica
 */
@Entity
public class Gestor implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue
    private long id;
    @Version
    private Long version;
    /**
     * Conta de SystemUser associada a esta instância de Gestor
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private SystemUser su;
    /**
     * Lista de categorias pelas quais o gestor é responsável
     */
    private List<Category> categorias;

    /**
     * Cria uma instância de Gestor
     *
     * @param su conta de SystemUser a associar
     * @param categorias lista de categorias pelas quais o gestor é responsável
     */
    public Gestor(SystemUser su, List<Category> categorias) {
        this.su = su;
        this.categorias = categorias;
    }

    /**
     * Cria uma nova instância de Gestor
     *
     * @param su conta de SystemUser a associar
     */
    public Gestor(SystemUser su) {
        if (su == null) {
            throw new IllegalArgumentException("Instância de SystemUser atribuida é null");
        }

        this.su = su;
    }

    /**
     * Descreve textualmente o Gestor.
     *
     * @return a informação relativa ao seu SystemUser
     */
    @Override
    public String toString() {
        return su.toString();
    }

    /**
     * Compara esta instância de Gestor a outro Objeto arbitrário
     *
     * @param o outro objeto a comparar
     * @return true se os dois elementos da comparação forem iguais
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Gestor gestor = (Gestor) o;

        return su.equals(gestor.su);
    }

    /**
     * Calcula o hashcode desta instância de Gestor
     *
     * @return o valor inteiro do hashcode do SystemUser associado
     */
    @Override
    public int hashCode() {
        return su.hashCode();
    }

    protected Gestor() {
    }

    @Override
    public Long getID() {
        return id;
    }

    /**
     * Adiciona várias categorias à lista de categorias
     *
     * @param lc lsita de categorias a adicionar
     * @return true se foram adicionadas com sucesso, false se não forem
     * adicionadas
     */
    public boolean adicionarCategorias(List<Category> lc) {
        try {
            return categorias.addAll(lc);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove várias categorias à lista de categorias
     *
     * @param lc lista de categorias a remover
     * @return true se forem removidas com sucesso, false se não forem removidas
     */
    public boolean removerCategorias(List<Category> lc) {
        try {
            return categorias.removeAll(lc);
        } catch (Exception e) {
            return false;
        }
    }
}
