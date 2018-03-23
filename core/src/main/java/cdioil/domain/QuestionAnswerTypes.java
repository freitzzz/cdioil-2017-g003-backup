package cdioil.domain;

/**
 * Enum that contains all types of answers and questions.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public enum QuestionAnswerTypes {

    /**
     * Binary Question (Yes or No answer)
     */
    BINARY {
        @Override
        public String toString() {
            return "Binária";
        }
    },
    /**
     * Multiple Choice Question (e.g. choices A, B, C or D)
     */
    MULTIPLE_CHOICE {
        @Override
        public String toString() {
            return "Escolha Múltipla";
        }
    },
    /**
     * Quantitative Question (e.g. on a scale from 0 to 4)
     */
    QUANTITATIVE {
        @Override
        public String toString() {
            return "Quantitativa";
        }
    };

}
