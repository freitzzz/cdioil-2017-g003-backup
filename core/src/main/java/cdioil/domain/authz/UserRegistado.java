package cdioil.domain.authz;

import cdioil.persistence.Identifiable;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * O utilizador que pertence ao publico-alvo da aplicação
 *
 * É o responsável por avaliar produtos e fornecer feedback
 */
@Entity
public class UserRegistado implements Serializable,Identifiable<Long> {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private Long version;
    /**
     * Conta de SystemUser associada a esta instância de UserRegistado
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private SystemUser su;

    /**
     * Cria uma nova instância de UserRegistado
     * @param su conta de SystemUser a associar
     */
    public UserRegistado(SystemUser su) {
        if (su == null) {
            throw new IllegalArgumentException("Instância de SystemUser atribuida é null");
        }
        this.su = su;
    }

    /**
     * Compara esta instância de UserRegistado a outro Objeto arbitrário
     * @param o outro objeto a comparar
     * @return true se os dois elementos da comparação forem iguais
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegistado that = (UserRegistado) o;

        return su.equals(that.su);
    }

    /**
     * Calcula o hashcode desta instancia de UserRegistado
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return su.hashCode();
    }
    protected UserRegistado(){}

    @Override
    public Long getID() {
        return id;
    }
}
