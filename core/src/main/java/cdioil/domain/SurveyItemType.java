package cdioil.domain;

/**
 * Enum listing all types of SurveyItem.
 *
 * @author António Sousa [1161371]
 */
public enum SurveyItemType {

    CATEGORY {
        @Override
        public String toString() {
            return "Category";
        }
    },
    PRODUCT {
        @Override
        public String toString() {
            return "Product";
        }
    }

}
