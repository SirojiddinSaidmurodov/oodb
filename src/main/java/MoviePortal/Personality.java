package MoviePortal;

import java.util.Date;

public class Personality {
    private String name;
    private Date dateOfBirth;
    private String bio;

    public Personality(String name, Date dateOfBirth, String bio) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Personality{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
