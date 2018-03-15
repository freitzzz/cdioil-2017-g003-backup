/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain.authz;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

/**
 * Representa o nome de um utilizador.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Embeddable
public class Nome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    public static final Pattern NOME_VALIDO_REGEX = Pattern.compile("^[A-Z]+[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);

    /**
     * Primeiro nome do utilizador
     */
    private String primeiroNome;
    /**
     * Apelido do utilizador;
     */
    private String apelido;

    /**
     * Constroi uma instancia de Nome recebendo um nome e um apelido.
     *
     * @param primeiroNome primeiro nome do utilizador
     * @param apelido apelido do utilizador
     */
    public Nome(String primeiroNome, String apelido) {
        if (primeiroNome == null || apelido == null || primeiroNome.isEmpty() || apelido.isEmpty()) {
            throw new IllegalArgumentException("O primeiro nome e o apelido não devem ser vazios.");
        }

        Matcher matcher = NOME_VALIDO_REGEX.matcher(primeiroNome);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Primeiro nome inválido: " + primeiroNome);
        }

        matcher = NOME_VALIDO_REGEX.matcher(apelido);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Apelido inválido: " + apelido);
        }

        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
    }

    protected Nome() {
        //Para ORM
    }

    /**
     * Hash code de um nome
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return primeiroNome.hashCode() + apelido.hashCode();
    }

    /**
     * Verifica se duas instancias de nome sao iguais.
     *
     * @param obj objeto a comparar
     * @return verdadeiro se forem iguais, falso se não
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Nome)) {
            return false;
        }
        final Nome other = (Nome) obj;
        if (!this.primeiroNome.equals(other.primeiroNome)) {
            return false;
        }
        return this.apelido.equals(other.apelido);
    }

    /**
     * Devolve o nome e o apelido do utilizador
     *
     * @return nome do utilizador
     */
    @Override
    public String toString() {
        return primeiroNome + "  " + apelido;
    }
}
