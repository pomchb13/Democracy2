package beans;

import java.time.LocalDate;

/**
 * Created by Leonhard on 16.08.2017.
 * Not finished --> Only a suggestion
 */
public class Poll {

    /* Tite of the PollContract */
    private String title;
    /* Startdate of the PollContract */
    private LocalDate date_from;
    /* Enddate of the PollContract */
    private LocalDate date_due;
    /* Anwsers of the PollContract */
    private String[] pollOptions;
    /* Option to show or not to show diagrams */
    private boolean diagramOption;

    public Poll(String title, LocalDate date_from, LocalDate date_due, String[] pollOptions, boolean diagramOption) {
        this.title = title;
        this.date_from = date_from;
        this.date_due = date_due;
        this.pollOptions = pollOptions;
        this.diagramOption = diagramOption;
    }

    @Override
    public String toString() {
        return "PollContract{" +
                "title='" + title + '\'' +
                ", date_from=" + date_from +
                ", date_due=" + date_due +
                ", pollOptions=" + pollOptions +
                ", diagramOption=" +diagramOption +
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

    public String[] getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(String[] pollOptions) {
        this.pollOptions = pollOptions;
    }
}
