package cdioil.framework.dto;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

/**
 * A generic DTO that implements the Map interface. This class can be used when you don't want to build your own custom DTO classes.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class GenericDTO implements DTO, Map<String, Object> {

    /**
     * Map that stores the DTO data.
     */
    private final Map<String, Object> data = new HashMap<>();

    /**
     * Type of the DTO
     */
    private final String type;

    /**
     * Builds an instance of GenericDTO.
     *
     * @param type Type of the DTO
     */
    public GenericDTO(String type) {
        if (type == null || type.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        this.type = type;
    }

    /**
     * Builds a DTO from an object using reflection.
     *
     * @param o Object in question
     * @return the created GenericDTO
     */
    public static GenericDTO of(Object o) {
        final GenericDTO out = new GenericDTO(o.getClass().getName());
        final List<Field> fields = getInheritedFields(o.getClass());
        fields.forEach((aField)
                -> ofField(o, out, aField)
        );
        return out;
    }

    /**
     * Identifies the field type and builds the correspondent DTO.
     *
     * @param o Object in question
     * @param out GenericDTO to be built
     * @param aField Field in question
     */
    @SuppressWarnings("unchecked")
    private static void ofField(Object o, final GenericDTO out, final Field aField) {
        try {
            aField.setAccessible(true);
            if (aField.getType().isPrimitive() || aField.getType() == String.class) {
                out.put(aField.getName(), aField.get(o));
            } else if (aField.getType().isArray()) {
                if (aField.getType().getComponentType().isPrimitive()
                        || aField.getType().getComponentType() == String.class) {
                    buildDtoForArray(aField.getType().getComponentType(), aField.getName(), aField.get(o), out);
                } else {
                    buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(o)), out);
                }
            } else if (Collection.class.isAssignableFrom(aField.getType())) {
                buildDtoForIterable(aField.getName(), (Iterable<Object>) (aField.get(o)), out);
            } else {
                out.put(aField.getName(), of(aField.get(o)));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            ExceptionLogger.logException(LoggerFileNames.FRAMEWORK_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Returns a list with DTOs.
     *
     * @param col Iterable in question
     * @return an Iterable of GenericDTOs
     */
    public static Iterable<GenericDTO> ofMany(Iterable<?> col) {
        final List<GenericDTO> data = new ArrayList<>();
        for (final Object member : col) {
            data.add(of(member));
        }
        return data;
    }

    /**
     * Builds a DTO for an iterable field.
     *
     * @param name Name of the DTO
     * @param col Iterable in question
     * @param out DTO being built
     */
    private static void buildDtoForIterable(String name, Iterable<Object> col, final GenericDTO out) {
        final Iterable<GenericDTO> data = ofMany(col);
        out.put(name, data);
    }

    /**
     * Builds a DTO for an array.
     *
     * @param type Type of the DTO
     * @param name Name of the DTO
     * @param array Array in question
     * @param out DTO being built
     */
    private static void buildDtoForArray(Class<?> type, String name, Object array, final GenericDTO out) {
        final int length = Array.getLength(array);
        Object data = null;
        if (type == int.class) {
            data = Arrays.copyOf((int[]) array, length);
        } else if (type == long.class) {
            data = Arrays.copyOf((long[]) array, length);
        } else if (type == boolean.class) {
            data = Arrays.copyOf((boolean[]) array, length);
        } else if (type == double.class) {
            data = Arrays.copyOf((double[]) array, length);
        } else if (type == float.class) {
            data = Arrays.copyOf((float[]) array, length);
        } else if (type == char.class) {
            data = Arrays.copyOf((char[]) array, length);
        } else if (type == String.class) {
            data = Arrays.copyOf((String[]) array, length);
        }

        out.put(name, data);
    }

    /**
     * Returns the inherited fields of a type.
     *
     * @param type Type to check
     * @return a list with all inherited fields
     */
    private static List<Field> getInheritedFields(Class<?> type) {
        final List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Returns the name of the type contained in this DTO. Might be helpful for client code to parse the DTO.
     *
     * @return a String containing the type of the DTO
     */
    public String type() {
        return this.type;
    }

    /**
     * Clears all entries of the map containing the data of the DTO.
     */
    @Override
    public void clear() {
        this.data.clear();
    }

    /**
     * Checks if the map containing the data of the DTO contains a certain key.
     *
     * @param arg0 Object to check
     * @return true, if the map contains the key. Otherwise, returns false
     */
    @Override
    public boolean containsKey(Object arg0) {
        return this.data.containsKey(arg0);
    }

    /**
     * Checks if the map containing the data of the DTO contains a certain value.
     *
     * @param arg0 Object to check
     * @return true, if the map contains the value. Otherwise, returns false
     */
    @Override
    public boolean containsValue(Object arg0) {
        return this.data.containsValue(arg0);
    }

    /**
     * Returns the entries of the map containing the data of the DTO.
     *
     * @return all entries of the map
     */
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.data.entrySet();
    }

    /**
     * Gets a certain entry of the map containing the data of the DTO.
     *
     * @param arg0 Object to find
     * @return the object of the map
     */
    @Override
    public Object get(Object arg0) {
        return this.data.get(arg0);
    }

    /**
     * Checks if the map containing the data of the DTO is empty.
     *
     * @return true, if the map is empty. Otherwise, returns false
     */
    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    /**
     * Returns the key set of the map containing the data of the DTO.
     *
     * @return the keys of the map
     */
    @Override
    public Set<String> keySet() {
        return this.data.keySet();
    }

    /**
     * Puts an entry in the map containing the data of the DTO.
     *
     * @param arg0 Entry key
     * @param arg1 Entry value
     * @return the new entry
     */
    @Override
    public Object put(String arg0, Object arg1) {
        return this.data.put(arg0, arg1);
    }

    /**
     * Puts all entries of the received map in the map containing the data of the DTO.
     *
     * @param arg0 map to append to the map containing the data of the DTO
     */
    @Override
    public void putAll(Map<? extends String, ? extends Object> arg0) {
        this.data.putAll(arg0);
    }

    /**
     * Removes a specific Object of the map containing the data of the DTO.
     *
     * @param arg0 Object to remove
     * @return the removed Object
     */
    @Override
    public Object remove(Object arg0) {
        return this.data.remove(arg0);
    }

    /**
     * Returns the size of the map containing the data of the DTO.
     *
     * @return the size of the map
     */
    @Override
    public int size() {
        return this.data.size();
    }

    /**
     * Returns all the values of the map containing the data of the DTO.
     *
     * @return a Collection with all values
     */
    @Override
    public Collection<Object> values() {
        return this.data.values();
    }

    /**
     * Generates a hash code according to the DTO's data and type.
     *
     * @return the generated hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.data);
        hash = 71 * hash + Objects.hashCode(this.type);
        return hash;
    }

    /**
     * Compares the GenericDTO with a received Object.
     *
     * @param obj Object to compare
     * @return true if the Objects are equal. Otherwise, returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        GenericDTO dto = (GenericDTO) obj;
        return type.equals(dto.type) ? data.equals(dto.data) : false;
    }
}
