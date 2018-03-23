package cdioil.domain;

import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe que representa o preço de um produto
 * <br>Value Object de Produto
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Preco implements Serializable,ValueObject {

    /**
     * Constante que representa a mensagem que caracteriza um preço inválido
     */
    private static final String MENSAGEM_PRECO_INVALIDO = "Preço Inválido!";
    /**
     * Constante que representa a expressão regular que valida a unidade de um
     * preço
     */
    private static final String REGEX_UNIDADES = "[€$£]|[A-Za-z]{3}";
    /**
     * Constante que representa a expressão regular que valida o valor de um
     * preço
     */
    private static final String REGEX_VALORES = "([0-9]{1,}([.]|,)[0-9]{1,2})|([0-9]+)";
    /**
     * Constante que representa a expressão regular que identifica espaços
     */
    private static final String REGEX_SPACES = "\\s+";
    /**
     * Constante que representa um String vazia
     */
    private static final String EMPTY_STRING = "";
    /**
     * Constante que representa o ISO-4217 da moeda europeia
     */
    private static final String EURO_ISO = "EUR";
    /**
     * Constante que representa o ISO-4217 da moeda britanica
     */
    private static final String BRITAIN_ISO = "GBP";
    /**
     * Constante que representa o ISO-4217 da moeda americana
     */
    private static final String US_ISO = "USD";
    /**
     * Constante que representa o simbolo da moeda europeia
     */
    private static final char EURO_SYMBOL = '€';
    /**
     * Constante que representa o simbolo da moeda britanica
     */
    private static final char BRITAIN_SYMBOL = '£';
    /**
     * Constante que representa o simbolo da moeda americana
     */
    private static final char US_SYMBOL = '$';
    /**
     * Float com o valor do preço de um produto
     */
    private float valor;
    /**
     * String que representa a unidade do preço no padrão ISO-4217
     */
    @Column(name = "UNIDADE")
    private String unidadeISO;

    /**
     * Constrói uma nova instância de Preco com um determinado preço de um
     * produto
     *
     * @param preco String com o preço do produto
     */
    public Preco(String preco) {
        tratarPreco(preco);
    }

    /**
     * Método que verifica se dois Precos são iguais
     *
     * @param obj Preco com o preço a ser comparado com o Preco atual
     * @return boolean true se os precos são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return unidadeISO.equalsIgnoreCase(((Preco) obj).unidadeISO)
                && valor == ((Preco) obj).valor;
    }

    /**
     * Hashcode do Preco
     *
     * @return Integer com o hashcode do Preco atual
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Float.floatToIntBits(this.valor);
        hash = 71 * hash + Objects.hashCode(this.unidadeISO);
        return hash;
    }

    /**
     * Método que representa a informação textual de um Preco
     *
     * @return String com a informação textual do Preco atual
     */
    @Override
    public String toString() {
        return valor + " " + unidadeISO;
    }

    /**
     * Método que valida se um Preco é valido ou não e trata da sua construção
     * <br>Como o método valida e trata da construção do objeto atual, caso o
     * preço seja inválido é lançada uma excessão
     *
     * @param preco String com o preço a ser validado e tratado
     */
    private void tratarPreco(String preco) {
        if (preco == null || preco.isEmpty()) {
            throw new IllegalArgumentException(MENSAGEM_PRECO_INVALIDO);
        }
        String currency = preco.replaceAll(REGEX_SPACES, EMPTY_STRING).replaceAll(REGEX_VALORES, EMPTY_STRING);
        String valorX = preco.replaceAll(REGEX_UNIDADES, EMPTY_STRING);
        if (currency.isEmpty() || currency.length() > 3 || valorX.isEmpty()) {
            throw new IllegalArgumentException(MENSAGEM_PRECO_INVALIDO);
        }
        currency = checkOrConvertUnidade(currency);
        if (currency == null) {
            throw new IllegalArgumentException(MENSAGEM_PRECO_INVALIDO);
        }
        this.unidadeISO = currency;
        this.valor = Float.parseFloat(valorX);
    }

    /**
     * Método que verifica ou converte se uma determinade unidade/simbolo é
     * valida
     * <br>Caso seja uma unidade, retorna a unidade no formato ISO-4217
     * <br>Caso seja um simbolo, retorna o seu equivalente como unidade no
     * formato ISO-4217
     * <br><b>Complexidade:</b> O(1)
     * <br><b>Notas:</b>
     * <br>- Atualmente não é tratada a possibilidade de existir outros moedas
     * com simbolos (apenas é tratado o Euro (€), Libra (£) e Dólar ($)) pois
     * por norma apenas o Euro, Libra e Dólar são representados pelo seu simbolo
     *
     * @param unidade String com a unidade/simbolo
     * @see java.util.Currency
     * @return String com a unidade de preço no formato ISO-4217 de uma
     * determinada unidade/simbolo, ou null caso a unidade/simbolo não sejam
     * válidas
     */
    private String checkOrConvertUnidade(String unidade) {
        switch (unidade.hashCode()) {
            case EURO_SYMBOL:
                return EURO_ISO;
            case BRITAIN_SYMBOL:
                return BRITAIN_ISO;
            case US_SYMBOL:
                return US_ISO;
            default:
                if (unidade.length() == 1) {
                    return null;
                }
                return Currency.getInstance(unidade.toUpperCase()).getCurrencyCode();
        }
    }

    /**
     * Construtor protegido de modo a permitir a persistencia com JPA
     */
    protected Preco() {
    }
}
