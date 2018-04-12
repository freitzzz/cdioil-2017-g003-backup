package cdioil.application.utils;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class whose purpose is to convert LocalDate instances into Date instances so
 * that they can be persisted using JPA 2.1.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    /**
     * Converts a LocalDate instance to a Date instance.
     *
     * @param localDateToConvert localDate to be converted
     * @return localDate in Date object
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate localDateToConvert) {
        return localDateToConvert == null ? null : Date.valueOf(localDateToConvert);
    }

    /**
     * Converts a Date instance to a LocalDate instance.
     *
     * @param dateToConvert date to be converted
     * @return date in LocalDate object
     */
    @Override
    public LocalDate convertToEntityAttribute(Date dateToConvert) {
        return dateToConvert == null ? null : dateToConvert.toLocalDate();
    }

}
