package beans;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Created by Leonhard on 16.08.2017.
 * Not finished --> Only a suggestion
 */
public class PollData {

    /* Tite of the PollContract */
    private String title;
    /* Startdate of the PollContract */
    private LocalDate date_from;
    /* Enddate of the PollContract */
    private LocalDate date_due;
    /* Option to show or not to show diagrams */
    private boolean diagramOption;
    /* List of all Answers */
    private LinkedList<PollAnswer> answerList;

    /**
     *
     * @param title
     * @param date_from
     * @param date_due
     * @param diagramOption
     */
    public PollData(String title, LocalDate date_from, LocalDate date_due, boolean diagramOption) {
        this.title = title;
        this.date_from = date_from;
        this.date_due = date_due;
        this.diagramOption = diagramOption;
        this.answerList = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "PollContract{" +
                "title='" + title + '\'' +
                ", date_from=" + date_from +
                ", date_due=" + date_due +
                ", diagramOption=" +diagramOption +
                ", answerList="+answerList +
                '}';
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
}
