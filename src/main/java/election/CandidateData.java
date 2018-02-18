package election;

import java.time.LocalDate;

/**
 * Created by Patri on 03.01.2018.
 */
public class CandidateData {

    private String title;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String party;
    private String slogan;

    public CandidateData(String title, String firstname, String lastname, LocalDate birthday, String party, String slogan) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Override
    public String toString() {
        return "CandidateData{" +
                "title='" + title + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", party='" + party + '\'' +
                ", slogan='" + slogan + '\'' +
                '}';
    }
}
