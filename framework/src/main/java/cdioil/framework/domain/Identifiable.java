package cdioil.framework.domain;

/**
 * Identifiable interface that serves as a way to check an object identity
 * <br>Based on professor <a href="">Paulo Gandra Sousa</a> Identifiable Interface
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @param <T> Generic Type with the type that represents the identity of the object 
 * implementing the interface
 */
public interface Identifiable<T> {
    /**
     * Method that returns the current identity value of the object implementing the interface
     * @return T with the current identity value of the object implementing the interface
     */
    public abstract T getID();
}