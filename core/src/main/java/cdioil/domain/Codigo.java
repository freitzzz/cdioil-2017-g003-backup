package cdioil.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Classe abstrata para códigos de um produto.
 *
 * @author António Sousa [1161371]
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Codigo<T> implements Serializable {

    /**
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Chave primária gerada automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CODIGO", updatable = false, nullable = false)
    protected int id;

    /**
     * Valor do código.
     */
    @Column(unique = true)
    protected T codigo;

    /**
     * Construtor para JPA.
     */
    protected Codigo() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codigo) + Objects.hashCode(this.getClass());
        /*Uma vez que isto será usado por todas as subclasses, o tipo de classe deverá contribuir para um hash diferente*/
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Codigo other = (Codigo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    /**
     * Descreve a Instância através do seu código.
     *
     * @return a descrição textual do código.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + codigo.toString() + "\n";
    }

}
