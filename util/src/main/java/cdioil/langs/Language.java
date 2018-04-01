package cdioil.langs;

/**
 * Enum containing currently supported languages.
 * @author Antonio Sousa
 */
public enum Language {
    
    pt_PT() {

        @Override
        public String toString() {
            return "Português (Portugal)";
        }
    },
    en_US() {
        @Override
        public String toString() {
            return "English (USA)";
        }
    }
}
