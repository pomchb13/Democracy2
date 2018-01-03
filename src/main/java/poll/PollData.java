package poll;

import java.time.LocalDate;

/**
 * Created by Patri on 03.01.2018.
 */
public class PollData {

    private String title;
    private LocalDate dateFrom;
    private LocalDate dateDue;
    private boolean showDiagram;

    public PollData(String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) {
        this.title = title;
        this.dateFrom = dateFrom;
        this.dateDue = dateDue;
        this.showDiagram = showDiagram;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public void setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
    }

    public boolean isShowDiagram() {
        return showDiagram;
    }

    public void setShowDiagram(boolean showDiagram) {
        this.showDiagram = showDiagram;
    }

    @Override
    public String toString() {
        return "PollData{" +
                "title='" + title + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateDue=" + dateDue +
                ", showDiagram=" + showDiagram +
                '}';
    }
}
