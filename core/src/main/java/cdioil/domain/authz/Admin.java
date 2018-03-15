package cdioil.domain.authz;

import cdioil.persistence.Identifiable;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Representa um administrador da app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(name="ADMINISTRADOR")
public class Admin implements Serializable,Identifiable<Long> {
    @Id
    @GeneratedValue
    private long id;
    private static final long serialVersionUID = 1L;
    
    @Version
    private Long version;

    /**
     * Conta de utilizador associada ao Admin
     */
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private SystemUser sysUser;

    /**
     * Constroi uma instancia de Admin recebendo um SystemUser
     *
     * @param sysUser conta de utilizador associada ao admin
     */
    public Admin(SystemUser sysUser) {
        if (sysUser == null) {
            throw new IllegalArgumentException("O utilizador atribuido ao admin "
                    + "não deve ser null.");
        }
        this.sysUser = sysUser;
    }

    protected Admin() {
        //Para ORM
    }

    /**
     * Hash code do admin
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return sysUser.hashCode();
    }

    /**
     * Verifica se duas instancias de Admin sao iguais tendo em conta as suas
     * contas de utilizador
     *
     * @param obj objeto a comparar
     * @return true se se tratar do mesmo admin, false se não
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Admin)) {
            return false;
        }
        final Admin other = (Admin) obj;
        return this.sysUser.equals(other.sysUser);
    }

    /**
     * Devolve uma descricao de um admin
     *
     * @return descricao de um admin
     */
    @Override
    public String toString() {
        return this.sysUser.toString();
    }

    @Override
    public Long getID(){
        return id;
    }

}
