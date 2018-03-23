package cdioil.domain;

/**
 * Empty interface for indicating that a CLass is indeed a Value Object, despite
 * being tagged as an Entity. This is due to JPA's lack of Inheritance support
 * for Embeddable.
 *
 * @author António Sousa [1161371]
 */
public interface ValueObject {

}
