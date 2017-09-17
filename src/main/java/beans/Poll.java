package beans;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Created by Leonhard on 16.08.2017.
 * Not finished --> Only a suggestion
 */
public class Poll {

    /* Tite of the Poll */
    private String title;
    /* Startdate of the Poll */
    private LocalDate date_from;
    /* Enddate of the Poll */
    private LocalDate date_due;
    /* Anwsers of the Poll */
    private LinkedList<String> pollOptions;

    public Poll(String title, LocalDate date_from, LocalDate date_due, LinkedList<String> pollOptions) {
        this.title = title;
        this.date_from = date_from;
        this.date_due = date_due;
        this.pollOptions = pollOptions;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "title='" + title + '\'' +
                ", date_from=" + date_from +
                ", date_due=" + date_due +
                ", pollOptions=" + pollOptions +
                '}';
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

    public LinkedList<String> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(LinkedList<String> pollOptions) {
        this.pollOptions = pollOptions;
    }
}
