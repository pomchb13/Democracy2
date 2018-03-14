package beans;

/**
 * Author:          Patrick Windegger
 * Description:     beans class responsible for the PollAnswer object.
 */
public class PollAnswer {

    private String title;
    private String description;
    private int voteCount;


    public PollAnswer(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public PollAnswer(String title, String description, int voteCount) {
        this.title = title;
        this.description = description;
        this.voteCount = voteCount;
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }


    @Override
    public String toString() {
        return "PollAnswer{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", voteCount=" + voteCount +
                '}';
    }
}
