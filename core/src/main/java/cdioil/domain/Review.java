package cdioil.domain;

import cdioil.application.utils.Edge;
import cdioil.application.utils.Graph;
import cdioil.domain.authz.Suggestion;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH})
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
    @Embedded
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
        this.survey = survey;
        this.answerGraph = survey.getGraphCopy();
        this.answers = new TreeMap<>();
        this.currentQuestion = answerGraph.getFirstQuestion();
        this.reviewState = ReviewState.PENDING;
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
        Iterable<Edge> outgoingEdges = answerGraph.outgoingEdges(currentQuestion);
        // If there are no outgoing edges, maps the last answer and finishes
        if (!outgoingEdges.iterator().hasNext()) {
            answers.put(currentQuestion, new Answer(option));
            reviewState = ReviewState.FINISHED; //state must also be updated
            return false;
        }

        for (Edge edge : outgoingEdges) {
            if (edge.getElement().equals(option)) {
                answers.put(currentQuestion, new Answer(option));
                currentQuestion = edge.getDestinationVertexElement();
            }
        }

        return true;
    }

    /**
     * Removes the answer to current question and updates current question to
     * the previous.
     */
    public void undoAnswer() {
        if (answers.isEmpty()) {
            return;
        }
        answers.remove(currentQuestion);
        currentQuestion = (((TreeMap<Question, Answer>) answers).lastKey());
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
     * Method that returns all questions and respective answers of the current
     * Review
     *
     * @return Map with all questions and respective answers of the current
     * Review
     */
    public Map<Question, Answer> getReviewQuestionAnswers() {
        return new TreeMap<>(answers);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.answerGraph);
        hash = 19 * hash + Objects.hashCode(this.answers);
        hash = 19 * hash + Objects.hashCode(this.currentQuestion);
        hash = 19 * hash + Objects.hashCode(this.suggestion);
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
