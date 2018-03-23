package cdioil.domain;

/**
 * Interface that contains methods that are common
 * to all types of Answers.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public interface Answer {

    /**
     * Returns the content of the answer.
     *
     * @return string with the contents of the answer
     */
    abstract String content();

    /**
     * Returns the type of the answer.
     *
     * @return string with type of the answer.
     */
    abstract String type();
}
