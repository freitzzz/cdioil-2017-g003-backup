package cdioil.domain;

import cdioil.application.utils.Graph;
import cdioil.domain.authz.Suggestion;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * Review class.
 *
 * @author João
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author António Sousa [1161371]
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Entity
@SequenceGenerator(name = "reviewSeq", initialValue = 1, allocationSize = 1)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewSeq")
    private long id;

    /**
     * Database version number.
     */
    @Version
    private long version;

    /**
     * Time (in seconds) that a User took to fully answer a Review.
     */
    private long timeToAnswer;

    /**
     * Copy of the survey's graph, defining the overall flow of the survey.
     */
    /*The Graph stored in this instance of Review is a copy of the Survey's Graph, 
    however, since it's a copy and not a direct reference to the Survey's Graph 
    it is, therefore, a new object and must be persisted with the Review*/
    @OneToOne(cascade = {CascadeType.ALL})
    private Graph answerGraph;

    /**
     * Survey being answered.
     */
    //A survey can be reviewed multiple times and changes made to the review should not cascade to the survey
    //A review only exists if a Survey exists, so it should only cascade as refresh
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private Survey survey;

    /**
     * State of the Review
     */
    @Enumerated(EnumType.ORDINAL)
    private ReviewState reviewState;

    /*
     * Map containing Questions IDs and their respective Answers.
     */
    //The review answers can be either persisted or updated
    //Answers can also be removed if the user wishes to undo them
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Map<Question, Answer> answers;

    /**
     * Question ID of the question currently being answered.
     */
    //Question has to be persisted before the Review
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Question currentQuestion;

    /**
     * Review's suggestion.
     */
    @OneToOne(cascade = {CascadeType.ALL})
    private Suggestion suggestion;

    /**
     * Empty constructor of class Review for JPA.
     */
    protected Review() {
    }

    /**
     * Constructs a new Review for a given Survey.
     *
     * @param survey survey being answered.
     */
    public Review(Survey survey) {
        if (survey == null) {
            throw new IllegalArgumentException("O inquérito não pode ser "
                    + "null");
        }
        this.timeToAnswer = 0;
        this.survey = survey;
        this.answerGraph = survey.getGraphCopy();
        this.answers = new LinkedHashMap<>();
        this.currentQuestion = answerGraph.getFirstQuestion();
        this.reviewState = ReviewState.PENDING;
    }

    /**
     * Sets the time that a User took to answer the Review.
     *
     * @param timeToAnswer time that the user took
     */
    public void setTimeToAnswer(long timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    /**
     * Fetches the question currently being answered
     *
     * @return question being answered
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Returns the survey the review is associated with.
     *
     * @return Survey the review is associated with
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Checks if there are no more questions to answer in the survey
     *
     * @return true if review is finished
     */
    public boolean isFinished() {
        return reviewState.equals(ReviewState.FINISHED);
    }

    /**
     * Answers the current question with a given option and updates the current
     * question.
     *
     * @param option selected option.
     * @return false if current question is the last one
     */
    public boolean answerQuestion(QuestionOption option) {

        Question nextQuestion = answerGraph.adjacentQuestion(currentQuestion, option);

        // If the option leads to nothing the review ends
        if (nextQuestion == null) {
            answers.put(currentQuestion, new Answer(option));
            reviewState = ReviewState.FINISHED; //state must also be updated
            return false;
        }

        answers.put(currentQuestion, new Answer(option));
        currentQuestion = nextQuestion;

        return true;
    }

    /**
     * Removes the answer to current question and updates current question to
     * the previous.
     *
     * @return true - if answer can be undone
     * <p>
     * false - otherwise
     */
    public boolean undoAnswer() {
        if (answers.isEmpty()) {
            return false;
        }

        LinkedList<Question> answeredQuestions = new LinkedList<>(answers.keySet());
        Question previouslyAnsweredQuestion = answeredQuestions.removeLast();
        answers.remove(previouslyAnsweredQuestion);

        currentQuestion = previouslyAnsweredQuestion;

        return true;
    }

    /**
     * Submits a suggestion associated to this review.
     *
     * @param suggestionText the suggestion's text.
     * @return always returns true because the suggestion can never be null.
     * (the boolean return is to control some aspects of the UI of US405)
     */
    public boolean submitSuggestion(String suggestionText) {
        suggestion = new Suggestion(suggestionText);
        return true;
    }

    /**
     *
     * Submits a suggestion with an image associated to this review.
     *
     * @param suggestionText the suggestion's text
     * @param suggestionImage the suggestion's image
     */
    public void submitSuggestionWithImage(String suggestionText, Image suggestionImage) {
        suggestion = new Suggestion(suggestionText, suggestionImage);
    }

    /**
     * Method that returns all questions and respective answers of the current
     * Review
     *
     * @return Map with all questions and respective answers of the current
     * Review
     */
    public Map<Question, Answer> getReviewQuestionAnswers() {
        return new TreeMap<>(answers);
    }

    /**
     * Checks if review already has a suggestion
     *
     * @return true if a suggestion exists, false if not
     */
    public boolean hasSuggestion() {
        return suggestion != null;
    }

    /**
     * Returns the Review suggestion
     *
     * @return String with review suggestion
     */
    public String getSuggestion() {
        return suggestion != null ? suggestion.toString() : null;
    }

    /**
     * Returns the Review's graph.
     *
     * @return review's graph.
     */
    public Graph getAnswerGraph() {
        return answerGraph;
    }

    /**
     * Review's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.answerGraph);
        hash = 19 * hash + Objects.hashCode(this.answers);
        return hash;
    }

    /**
     * Review's hash code.
     *
     * @return review's hash code
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Review)) {
            return false;
        }
        final Review other = (Review) obj;
        if (!this.answerGraph.equals(other.answerGraph)) {
            return false;
        }
        return this.answers.equals(other.answers);
    }

    /**
     * Returns a string containing the review's data
     *
     * @return string containing the review's data
     */
    @Override
    public String toString() {
        return "Avaliação:\nOpinião: " + suggestion;
    }

}
