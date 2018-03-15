package cdioil.domain.authz;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Gestor de Inquéritos
 *
 * Responsável pela criação e configuração de inquéritos
 * de uma dada estrutura mercadológica
 */
@Entity
public class Gestor implements Serializable {
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
     * Cria uma nova instância de Gestor
     * @param su conta de SystemUser a associar
     */
    public Gestor(SystemUser su) {
        if (su == null) {
            throw new IllegalArgumentException("Instância de SystemUser atribuida é null");
        }

        this.su = su;
    }

    /**
     * Compara esta instância de Gestor a outro Objeto arbitrário
     * @param o outro objeto a comparar
     * @return true se os dois elementos da comparação forem iguais
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gestor gestor = (Gestor) o;

        return su.equals(gestor.su);
    }

    /**
     * Calcula o hashcode desta instância de Gestor
     * @return o valor inteiro do hashcode do SystemUser associado
     */
    @Override
    public int hashCode() {
        return su.hashCode();
    }
    protected Gestor(){}
}
