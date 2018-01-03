package election;

import java.time.LocalDate;

/**
 * Created by Patri on 03.01.2018.
 */
public class ElectionData {

    private String title;
    private LocalDate dateFrom;
    private LocalDate dateDue;
    private Boolean showDiagram;

    public ElectionData(String title, LocalDate dateFrom, LocalDate dateDue, Boolean showDiagram) {
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

    public Boolean getShowDiagram() {
        return showDiagram;
    }

    public void setShowDiagram(Boolean showDiagram) {
        this.showDiagram = showDiagram;
    }

    @Override
    public String toString() {
        return "ElectionData{" +
                "title='" + title + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateDue=" + dateDue +
                ", showDiagram=" + showDiagram +
                '}';
    }
}
