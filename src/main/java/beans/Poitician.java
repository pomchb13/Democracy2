package beans;

import java.io.File;
import java.time.LocalDate;

/**
 * Created by Leonhard on 16.08.2017.
 * Not finished --> Only a suggestion
 */
public class Poitician{

    /* Forename of the politican */
    private String forename;
    /* surename of the politican */
    private String surname;
    /* birthday of the politican */
    private LocalDate birthday;
    /* party of the politican */
    private Party party;
    /* Solgen of the politican */
    private String slogan;
    /* Picture of the politican */
    private File portrait;

    public Poitician(String forename, String surname, LocalDate birthday, Party party, String slogan, File portrait) {
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "Poitician{" +
                "forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", party=" + party +
                ", slogan='" + slogan + '\'' +
                ", portrait=" + portrait +
                '}';
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

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public File getPortrait() {
        return portrait;
    }

    public void setPortrait(File portrait) {
        this.portrait = portrait;
    }
}