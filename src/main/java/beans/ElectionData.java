package beans;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Created by Leonhard on 13.08.2017.
 * Not finished --> Only a suggestion
 * Not sure how to handle the party, politician conflict
 */
public class ElectionData {

    /* Title of the ElectionDataOld */
    private String title;
    /* Startdate of the ElectionDataOld */
    private LocalDate date_from;
    /* Enddate of the ElectionDataOld */
    private LocalDate date_due;

    private boolean show_diagrams;
    /* List where all Candidates where saved */
    private LinkedList<CandidateData> liCandidates;

    public ElectionData(String title, LocalDate date_from, LocalDate date_due, boolean show_diagrams) {
        this.title = title;
        this.date_from = date_from;
        this.date_due = date_due;
        this.show_diagrams = show_diagrams;
        this.liCandidates = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "ElectionDataOld{" +
                "title='" + title + '\'' +
                ", date_from=" + date_from +
                ", date_due=" + date_due +
                ", show_diagrams=" + show_diagrams +
                ", liCandidates=" + liCandidates +
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

    public boolean isShow_diagrams() {
        return show_diagrams;
    }

    public void setShow_diagrams(boolean show_diagrams) {
        this.show_diagrams = show_diagrams;
    }

    public LinkedList<CandidateData> getLiCandidates() {
        return liCandidates;
    }

    public void setLiCandidates(LinkedList<CandidateData> liCandidates) {
        this.liCandidates = liCandidates;
    }
}