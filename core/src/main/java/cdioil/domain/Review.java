package cdioil.domain;

import cdioil.application.utils.Edge;
import cdioil.application.utils.Graph;
import cdioil.domain.authz.Suggestion;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Review class.
 *
 * @author João
 */
@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Database version number.
     */
    @Version
    private long version;

    /**
     * Copy of the survey's graph, defining the overall flow of the survey.
     */
    //@OneToOne(cascade = CascadeType.PERSIST)
    @Transient
    private Graph answerGraph;

    /**
     * Survey being answered.
     */
    //@ManyToOne(cascade = CascadeType.PERSIST)
    @Transient
    private Survey survey;

    /**
     *
     */
    @Transient
    private ReviewState state;

    /**
     * Map containing Questions and their respective Answers.
     */
    //@ManyToMany(cascade = CascadeType.PERSIST)
    @ElementCollection
    @ManyToMany(cascade = CascadeType.PERSIST)
    @MapKeyJoinColumn(name = "QUESTION_ID")
    private Map<Question, Answer> answers;

    /**
     * Question currently being answered.
     */
    //@OneToOne(cascade = CascadeType.PERSIST)
    @Transient
    private Question currentQuestion;

    /**
     * Items being reviewed.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<SurveyItem> itemList;

    /**
     * Review's final suggestion.
     */
    @Embedded
    private Suggestion suggestion;

    /**
     * Empty constructor of class Review
     */
    protected Review() {
    }

    /**
     * Constructs a new Review for a given Survey.
     *
     * @param survey survey being answered.
     */
    public Review(Survey survey) {
        if(survey == null){
            throw new IllegalArgumentException("O inquérito não pode ser "
                    + "null");
        }
        this.survey = survey;
        this.answerGraph = survey.getGraphCopy();
        //fetch items from survey
        this.answers = new TreeMap<>();
        this.currentQuestion = answerGraph.getFirstQuestion();
        state = ReviewState.PENDING;
    }

    /**
     * Fetches the question currently being answered
     * @return question being answered
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Checks if there are no more questions to answer in the survey
     * @return true if review is finished
     */
    public boolean isFinished() {
        return state.equals(ReviewState.FINISHED);
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
            state = ReviewState.FINISHED;
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
        currentQuestion = ((TreeMap<Question, Answer>)answers).lastKey();
    }

    /**
     * Submits a suggestion associated to this review.
     *
     * @param suggestionText the suggestion's text.
     */
    public void submitSuggestion(String suggestionText) {
        suggestion = new Suggestion(suggestionText);
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
        hash = 19 * hash + Objects.hashCode(this.itemList);
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        if (!Objects.equals(this.answerGraph, other.answerGraph)) {
            return false;
        }
        if (!Objects.equals(this.answers, other.answers)) {
            return false;
        }
        if (!Objects.equals(this.itemList, other.itemList)) {
            return false;
        }
        if (!Objects.equals(this.suggestion, other.suggestion)) {
            return false;
        }
        return true;
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

enum ReviewState {

    PENDING,FINISHED;
}