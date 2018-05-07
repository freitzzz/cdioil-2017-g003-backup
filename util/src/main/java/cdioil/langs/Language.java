package cdioil.langs;

/**
 * Enum containing currently supported languages.
 * @author Antonio Sousa
 */
public enum Language {
    
    PT() {

        @Override
        public String toString() {
            return "Português (Portugal)";
        }
    },
    EN_US() {
        @Override
        public String toString() {
            return "English (USA)";
        }
    }
}
