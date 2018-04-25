package cdioil.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 * Abstract class that represents a Question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "QUESTIONTYPE")
public abstract class Question implements Serializable, Comparable {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long databaseID;

    /**
     * The question's type.
     */
    @Enumerated
    protected QuestionTypes type;

    /**
     * The question itself.
     */
    private String questionText;

    /**
     * The question's ID.
     */
//    @Id
    private String questionID;

    public void setQuestionID(String newQuestionID){
        this.questionID = newQuestionID;
    }
    public void bro(int ok){
        databaseID=ok;
    }
    /**
     * List of options that the question has.
     */
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "QUESTION_OPTIONLIST",
            joinColumns = {@JoinColumn(name = "QUESTIONDATABASEID")}
    )
    private List<QuestionOption> optionList;

    public Question(String questionText, String questionID, List<QuestionOption> optionList) {
        if (questionText == null || questionText.isEmpty()) {
            throw new IllegalArgumentException("O texto da questão não "
                    + "pode ser null ou vazio.");
        }
        if (questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("O id da questão não pode "
                    + "ser null ou vazio.");
        }
        if (optionList == null || optionList.isEmpty()) {
            throw new IllegalArgumentException("A lista de opções não pode "
                    + "ser null ou vazia");
        }
        this.questionText = questionText;
        this.questionID = questionID;
        this.optionList = optionList;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected Question() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionID() {
        return questionID;
    }

    /**
     * Get the question type
     *
     * @return question type
     */
    public QuestionTypes getType() {
        return type;
    }

    /**
     * Retrieves the Question's list of currently available options.
     *
     * @return all available options.
     */
    public List<QuestionOption> getOptionList() {
        if(optionList==null)return new ArrayList<>();
        return optionList;
    }

    /**
     * Returns an hash value based on the attributes and class type.
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.getClass());
        hash = 29 * hash + Objects.hashCode(this.questionText);
        hash = 29 * hash + Objects.hash(this.questionID);
        return hash;
    }

    /**
     * Verifies object equality based on the Question's attributes and class
     * type.
     *
     * @param obj object to be compared to
     * @return true if the objects are truly equal, false otherwise
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
        final Question other = (Question) obj;
        if (!Objects.equals(this.questionText, other.questionText)) {
            return false;
        }
        if (!Objects.equals(this.questionID, other.questionID)) {
            return false;
        }
        return true;

    }

    /**
     * Returns the text of the question
     *
     * @return string containg the question
     */
    @Override
    public String toString() {
        return questionText;
    }

    /**
     * Compares current question to anothe question
     *
     * @param o question to be compared
     * @return 0 if they're equal, 1 or - 1 if they're different
     */
    @Override
    public int compareTo(Object o) {
        return Integer.compare(hashCode(), o.hashCode());
    }
}
