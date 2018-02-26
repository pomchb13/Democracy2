package beans;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;

/**
 * Created by Leonhard on 16.08.2017.
 * Not finished --> Only a suggestion
 */
public class Politician {

    /* academic title of the politician */
    private String title;
    /* Forename of the politican */
    private String forename;
    /* surename of the politican */
    private String surname;
    /* birthday of the politican */
    private LocalDate birthday;
    /* party of the politican */
    private String party;
    /* Solgen of the politican */
    private String slogan;
    /* Picture of the politican */
    private BufferedImage portrait;

    public Politician(String title, String forename, String surname, LocalDate birthday, String party, String slogan, BufferedImage portrait) {
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "Politician{" +
                "title='" + title + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", party='" + party + '\'' +
                ", slogan='" + slogan + '\'' +
                ", portrait=" + portrait +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public BufferedImage getPortrait() {
        return portrait;
    }

    public void setPortrait(BufferedImage portrait) {
        this.portrait = portrait;
    }
}
