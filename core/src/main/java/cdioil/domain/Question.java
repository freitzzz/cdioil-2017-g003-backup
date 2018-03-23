package cdioil.domain;

/**
 * Interface that contains methods that are common to all types of
 * Questions.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public interface Question {

    /**
     * Returns the text of the question
     *
     * @return string with the question itself
     */
    abstract String content();

    /**
     * Returns the type of the question
     *
     * @return string with the question type
     */
    abstract String type();
}
