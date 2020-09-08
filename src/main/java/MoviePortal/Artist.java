package MoviePortal;

public class Artist {
    private Personality person;
    private String occupation;

    public Artist(Personality person, String occupation) {
        this.person = person;
        this.occupation = occupation;
    }

    public Personality getPerson() {
        return person;
    }

    public void setPerson(Personality person) {
        this.person = person;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "person=" + person +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}
