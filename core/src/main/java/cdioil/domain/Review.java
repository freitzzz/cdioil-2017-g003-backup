package cdioil.domain;

import cdioil.application.utils.Edge;
import cdioil.application.utils.Graph;
import cdioil.domain.authz.Suggestion;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Review class.
 *
 * @author João
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author António Sousa [1161371]
 */
@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database version number.
     */
    @Version
    private long version;

    /**
     * Copy of the survey's graph, defining the overall flow of the survey.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Graph answerGraph;

    /**
     * Survey being answered.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Survey survey;

    /**
<<<<<<< Updated upstream
     *
     */
    @Enumerated
    private ReviewState reviewState;


    /*
     * Map containing Questions IDs and their respective Answers.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    private Map<Question, Answer> answers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questionList;

    /**
     * Question ID of the question currently being answered.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Question currentQuestion;

    /**
     * Items being reviewed.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<SurveyItem> itemList;

    /**
     * Review's final suggestion.
     */
    @Embedded
    private Suggestion suggestion;

    public void setUpQuestionIDs() {
        int i = 0;
        for (Question question : questionList) {
            question.setQuestionID(question.getQuestionID()+ i);
            System.out.println(question.getQuestionID());
            i++;
        }
        i++;
        currentQuestion.setQuestionID(currentQuestion.getQuestionID()+ i);
        System.out.println(currentQuestion.getQuestionID());
    }

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
        if (survey == null) {
            throw new IllegalArgumentException("O inquérito não pode ser "
                    + "null");
        }
        System.out.println("?");
        this.survey = survey;
        System.out.println("??");
        this.answerGraph = survey.getGraphCopy();
        System.out.println("???");
        //fetch items from survey
        this.answers = new TreeMap<>();
        System.out.println("????");
        this.currentQuestion = buildQuestion(answerGraph.getFirstQuestion());
        System.out.println("?????");
        this.questionList = new LinkedList<>();
        System.out.println("??????");
        questionList.add(buildQuestion(currentQuestion));
        System.out.println("???????");
        this.reviewState = ReviewState.PENDING;
        System.out.println("????????");
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
     * Checks if there are no more questions to answer in the survey
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
            answers.put(buildQuestion(currentQuestion), new Answer(option));
            questionList.add(buildQuestion(currentQuestion));
            return false;
        }

        for (Edge edge : outgoingEdges) {
            if (buildQuestionOption(edge.getElement()).equals(option)) {
                answers.put(buildQuestion(currentQuestion), new Answer(option));
                currentQuestion = buildQuestion(edge.getDestinationVertexElement());
                questionList.add(buildQuestion(currentQuestion));
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
        questionList.remove(currentQuestion);
        currentQuestion = buildQuestion(((TreeMap<Question,Answer>) answers).lastKey());
    }

    /**
     * Submits a suggestion associated to this review.
     *
     * @param suggestionText the suggestion's text.
     * @return true if the suggestion was created, false if otherwise
     */
    public boolean submitSuggestion(String suggestionText) {
        suggestion = new Suggestion(suggestionText);
        return suggestion != null;
    }

    /**
     * Method that returns all questions and respective answers of the current
     * Review
     *
     * @return Map with all questions and respective answers of the current
     * Review
     */
    public Map<Question,Answer> getReviewQuestionAnswers() {
        return new TreeMap<>(answers);
    }

    private Question buildQuestion(Question question) {
        if (question instanceof BinaryQuestion) {
            return new BinaryQuestion(question);
        }
        if (question instanceof MultipleChoiceQuestion) {
            return new MultipleChoiceQuestion(question);
        }
        if (question instanceof QuantitativeQuestion) {
            return new QuantitativeQuestion(question);
        }
        return null;
    }

    private QuestionOption buildQuestionOption(QuestionOption option) {
        if (option instanceof BinaryQuestionOption) {
            return new BinaryQuestionOption(option);
        }
        if (option instanceof MultipleChoiceQuestionOption) {
            return new MultipleChoiceQuestionOption(option);
        }
        if (option instanceof QuantitativeQuestionOption) {
            return new QuantitativeQuestionOption(option);
        }
        return null;
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