package beans;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     beans class responsible for the PollData objects.
 *                  The object contains a list of all PollAnswers of a poll.
 */
public class PollData {

    private String title;
    private LocalDate date_from;
    private LocalDate date_due;
    private boolean diagramOption;
    private LinkedList<PollAnswer> answerList;

    public PollData(String title, LocalDate date_from, LocalDate date_due, boolean diagramOption) {
        this.title = title;
        this.date_from = date_from;
        this.date_due = date_due;
        this.diagramOption = diagramOption;
        this.answerList = new LinkedList<>();
    }



    public boolean isDiagramOption() {
        return diagramOption;
    }

    public void setDiagramOption(boolean diagramOption) {
        this.diagramOption = diagramOption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate_from() {
        return date_from;
    }

    public void setDate_from(LocalDate date_from) {
        this.date_from = date_from;
    }

    public LocalDate getDate_due() {
        return date_due;
    }

    public void setDate_due(LocalDate date_due) {
        this.date_due = date_due;
    }

    public LinkedList<PollAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(LinkedList<PollAnswer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "PollData{" +
                "title='" + title + '\'' +
                ", date_from=" + date_from +
                ", date_due=" + date_due +
                ", diagramOption=" + diagramOption +
                ", answerList=" + answerList +
                '}';
    }
}
