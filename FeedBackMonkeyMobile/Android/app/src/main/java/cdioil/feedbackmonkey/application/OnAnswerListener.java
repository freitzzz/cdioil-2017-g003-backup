package cdioil.feedbackmonkey.application;

/**
 * Interface used for warning a QuestionActivity that a question has been answered.
 * More information: https://developer.android.com/training/basics/fragments/
 */
public interface OnAnswerListener {

    /**
     * Used for setting behaviour when a question has been answered.
     * @param answer answer being provided
     */
    void onQuestionAnswered(String answer);
}
