package cdioil.domain;

import java.util.Collection;

/**
 * Question Library Interface. Contains methods that are common to all question
 * libraries.
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public interface QuestionLibrary {

    /**
     * Adds a question to a question library
     *
     * @param question question to be added
     * @return true if the question was added, false if it wasn't
     */
    abstract boolean addQuestion(Question question);

    /**
     * Removes a question from a question library
     *
     * @param question question to be removed
     * @return true if the question was removed, false if it wasn't
     */
    abstract boolean removeQuestion(Question question);

    /**
     * Checks if a question already exists in a question library
     *
     * @param question question to be checked
     * @return true if the question already exists, false if it doesn't
     */
    abstract boolean doesQuestionExist(Question question);

    /**
     * Returns the number of questions in a question library
     *
     * @return number of questions in a question library
     */
    abstract int size();

}
