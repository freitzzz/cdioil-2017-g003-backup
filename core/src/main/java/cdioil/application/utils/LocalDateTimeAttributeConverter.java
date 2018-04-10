package cdioil.application.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class whose purpose is to convert LocalDateTime instances into Timestamp
 * instances so that they can be persisted using JPA 2.1.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Converts a LocalDateTime instance into a timestamp so that it can be
     * persisted in the database.
     *
     * @param x localDateTime to be converted
     * @return timestamp that will be persisted in the database
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime x) {
        return x == null ? null : Timestamp.valueOf(x);
    }

    /**
     * Converts a Timestamp instance that's loaded from the dabatase into a
     * LocalDateTime instance to rebuild any entity that uses LocalDateTime.
     *
     * @param y timestamp to be converted
     * @return localDateTime that will be used to rebuild an entity
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp y) {
        return y == null ? null : y.toLocalDateTime();
    }

}
