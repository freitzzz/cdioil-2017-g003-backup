package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class that represents the price of a product
 * <br>Value object of Product
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Price implements Serializable,ValueObject {
    /**
     * Constant that represents the message that ocures if a price is invalid
     */
    private static final String INVALID_PRICE_MESSAGE = "Preço Inválido!";
    /**
     * Constant that represents the regular expression used to validate a price unit
     */
    private static final String UNITS_REGEX = "[€$£]|[A-Za-z]{3}";
    /**
     * Constant that represents the regular expression used to validate the value of 
     * a price
     */
    private static final String VALUES_REGEX = "([0-9]{1,}([.]|,)[0-9]{1,2})|([0-9]+)";
    /**
     * Constant that represents the regular expression used to identify spaces
     */
    private static final String REGEX_SPACES = "\\s+";
    /**
     * Constant that represents an empty String
     */
    private static final String EMPTY_STRING = "";
    /**
     * Constant that represents the ISO-4217 identifer for the European coin
     */
    private static final String EURO_ISO = "EUR";
    /**
     * Constant that represents the ISO-4217 identifer for the Britain coin
     */
    private static final String BRITAIN_ISO = "GBP";
    /**
     * Constant that represents the ISO-4217 identifer for the American coin
     */
    private static final String US_ISO = "USD";
    /**
     * Constant that represents the symbol used to identify the European coin
     */
    private static final char EURO_SYMBOL = '€';
    /**
     * Constant that represents the symbol used to identify the Britain coin
     */
    private static final char BRITAIN_SYMBOL = '£';
    /**
     * Constant that represents the symbol used to identify the American coin
     */
    private static final char US_SYMBOL = '$';
    /**
     * Float with the price value of a product
     */
    private float priceValue;
    /**
     * String that represents the price unit as with the ISO-4127 standard
     */
    @Column(name = "UNIDADE")
    private String ISOUnit;
    /**
     * Builds a new Price with a certain product price
     * @param price String with the product rice
     */
    public Price(String price) {
        checkPrice(price);
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Price() {}
    /**
     * Method that verifies if two Prices are the same
     * @param obj Price with the price being compared to the current one
     * @return boolean true if both prices are the same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return ISOUnit.equalsIgnoreCase(((Price) obj).ISOUnit)
                && Double.compare(priceValue, ((Price) obj).priceValue) == 0;
    }
    /**
     * Price Hash Code
     * @return Integer with the current Price hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Float.floatToIntBits(this.priceValue);
        hash = 71 * hash + Objects.hashCode(this.ISOUnit);
        return hash;
    }
    /**
     * Method that represents the textual information of a Price
     * @return String with the textual representation of a Price
     */
    @Override
    public String toString() {
        return priceValue + " " + ISOUnit;
    }
    /**
     * Method that checks if a Price is valid or not
     * <br>Due to validations, if the Price is invalid an exception is thrown 
     * (<b>IllegalArgumentException</b>)
     * @param price String with the price being validated
     */
    private void checkPrice(String price) {
        if (price == null || price.isEmpty()) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        String currency = price.replaceAll(REGEX_SPACES, EMPTY_STRING).replaceAll(VALUES_REGEX, EMPTY_STRING);
        String valorX = price.replaceAll(UNITS_REGEX, EMPTY_STRING);
        if (currency.isEmpty() || currency.length() > 3 || valorX.isEmpty()) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        currency = checkOrConvertUnit(currency);
        if (currency == null) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        this.ISOUnit = currency;
        this.priceValue = Float.parseFloat(valorX);
    }
    /**
     * Method that verifies/converts if a Price unit/symbol is valid
     * <br>If an unit, returns the unit on the ISO-4217 standard format
     * <br>If a symbool, returns the equivalent as an unit on the ISO-4217 standard 
     * format
     * <br><b>Complexity:</b> O(1)
     * <br><b>Notes:</b>
     * <br>- At this moment only the three global coins are being validated, being these 
     * ones the <b>Euro (€)</b>, the <b>Pound (£)</b> and the <b>Dollar ($)</b>
     * @param unit String with the unit being validated
     * @see java.util.Currency
     * @return String with the price unit on the ISO-4217 standard format, null if an exception 
     * ocured
     */
    private String checkOrConvertUnit(String unit) {
        switch (unit.hashCode()) {
            case EURO_SYMBOL:
                return EURO_ISO;
            case BRITAIN_SYMBOL:
                return BRITAIN_ISO;
            case US_SYMBOL:
                return US_ISO;
            default:
                return unit.length() == 1 ? null : Currency.getInstance(unit.toUpperCase()).getCurrencyCode();
        }
    }
}
