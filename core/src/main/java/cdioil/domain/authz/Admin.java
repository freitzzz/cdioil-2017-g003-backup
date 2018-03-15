package cdioil.domain.authz;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Representa um administrador da app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    /**
     * Conta de utilizador associada ao Admin
     */
    @EmbeddedId
    private SystemUser sysUser;

    /**
     * Constroi uma instancia de Admin recebendo um SystemUser
     *
     * @param sysUser conta de utilizador associada ao admin
     */
    public Admin(SystemUser sysUser) {
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

}
