package cdioil.domain.authz;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * O utilizador que pertence ao publico-alvo da aplicação
 *
 * É o responsável por avaliar produtos e fornecer feedback
 */
@Entity
public class RegisteredUser implements Serializable, AggregateRoot<SystemUser>,User{

    @Id
    @GeneratedValue
    @Column(name = "REGISTEREDUSER_ID")
    private long id;
    @Version
    private Long version;
    /**
     * Conta de SystemUser associada a esta instância de RegisteredUser
     */
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "SYSTEMUSER")
    private SystemUser su;

    /**
     * Cria uma nova instância de UserRegistado
     *
     * @param su conta de SystemUser a associar
     */
    public RegisteredUser(SystemUser su) {
        if (su == null) {
            throw new IllegalArgumentException("Instância de SystemUser atribuida é null");
        }
        this.su = su;
    }

    /**
     * Compara esta instância de RegisteredUser a outro Objeto arbitrário
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

        RegisteredUser that = (RegisteredUser) o;

        return su.equals(that.su);
    }

    /**
     * Descreve textualmente o RegisteredUser.
     *
     * @return a informação relativa ao seu SystemUser
     */
    @Override
    public String toString() {
        return su.toString();
    }

    /**
     * Calcula o hashcode desta instancia de RegisteredUser
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return su.hashCode();
    }

    protected RegisteredUser() {
    }

    @Override
    public SystemUser getID() {
        return su;
    }
}
