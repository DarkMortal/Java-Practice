package modules;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.io.Serializable;

public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private String question;
    private int correctOption;
    private ArrayList<String> options = new ArrayList<>();

    public Question(String question_, String[] options_, int correctOption_) throws IndexOutOfBoundsException {
        if(correctOption_ < 1 || correctOption_ >= options_.length + 1) {
            throw new IndexOutOfBoundsException("Correct option index is out of bounds");
        }

        this.question = question_;
        this.options.addAll(Arrays.asList(options_));
        this.correctOption = correctOption_;
    }

    public void Display() {
        System.out.println("\n" + this.question);
        for (int i = 0; i < this.options.size(); i++) {
            System.out.println((i + 1) + ". " + this.options.get(i));
        }
    }

    public float CheckAnswer(int answer) {
        if(answer == this.correctOption){
            System.out.println("Correct answer!");
            return 1;
        }
        else{
            System.out.println("Incorrect answer!\nCorrect answer is: " + this.correctOption + ". " + this.options.get(this.correctOption - 1));
            return (float) -0.5;
        }
    }

    public void updateQuestion(String question_){
        this.question = question_;
    }

    public void addOptions(String[] options_){
        this.options.addAll(Arrays.asList(options_));
    }

    public void deleteOption(int index) throws IndexOutOfBoundsException {
        if(index < 1 || index > this.options.size()) {
            throw new IndexOutOfBoundsException("Option index is out of bounds");
        }
        if(this.correctOption == index) {
            System.out.println("Correct option cannot be deleted"); return;
        }
        if(this.correctOption > index) this.correctOption--;
        this.options.remove(index - 1);
    }

    public void updateCorrectOption(int correctOption_) throws IndexOutOfBoundsException {
        if(correctOption_ < 1 || correctOption_ >= this.options.size() + 1) {
            throw new IndexOutOfBoundsException("Correct option index is out of bounds");
        }
        this.correctOption = correctOption_;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(Objects.isNull(obj)) return false;
        if(!(obj instanceof Question)) return false;
        Question q = (Question) obj;
        return this.question.equals(q.question) && this.options.equals(q.options) && this.correctOption == q.correctOption;
    }
}