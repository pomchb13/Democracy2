package beans;

public class PollAnswer {

    /* Title of the answer */
    private String title;
    /* Description of the answer */
    private String description;

    public PollAnswer(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "PollAnswer{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
