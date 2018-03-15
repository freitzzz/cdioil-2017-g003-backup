package cdioil.domain.authz;

/**
 * O utilizador que pertence ao publico-alvo da aplicação
 *
 * É o responsável por avaliar produtos e fornecer feedback
 */
public class UserRegistado {

    /**
     * Conta de SystemUser associada a esta instância de UserRegistado
     */
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
}
