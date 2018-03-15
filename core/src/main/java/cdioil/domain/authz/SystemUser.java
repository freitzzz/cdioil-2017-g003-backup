/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Representa os utilizadores da app.
 *
 * @author Gil Durão
 */
@Entity
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    /**
     * Email do utilizador (username)
     */
    @EmbeddedId
    private Email email;
    /**
     * Nome do utilizador
     */
    private Nome nome;
    /**
     * Password do utilizador
     */
    @Embedded
    private Password password;

    /**
     * Constroi uma instancia de SystemUser com um email, nome e uma password
     *
     * @param email
     * @param nome
     * @param password
     */
    public SystemUser(Email email, Nome nome, Password password) {
        this.email = email;
        this.nome = nome;
        this.password = password; //Falta a encriptacao
    }

    protected SystemUser() {
        //Para ORM
    }

    /**
     * Devolve o email do utilizador
     *
     * @return email
     */
    public Email email() {
        return this.email;
    }

    /**
     * Verifica se uma password recebida é igual à de um utilizador
     *
     * @param password pwd recebida
     * @return true se forem iguals, false se forem diferentes
     */
    public boolean passwordIgual(Password password) {
        return this.password.equals(password);
    }

    /**
     * Hash code do utilizador
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    /**
     * Verifica se duas instancias de SystemUser sao iguais tendo em conta o seu
     * id (email)
     *
     * @param obj objeto a comparar
     * @return true se se tratar do mesmo utilizador, false se não
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemUser)) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;
        return this.email.equals(other.email);
    }

    /**
     * Devolve uma descricao de um utilizador (email)
     *
     * @return descricao de um utilizador
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\n" + "Email: " + email + "\n";
    }

}
