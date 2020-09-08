package MoviePortal;

import java.util.Date;

public class Rate {
    private byte value;
    private Movie movie;
    private Date dateOfChange;

    public Rate(byte value, Movie movie, Date dateOfChange) {
        this.value = value;
        this.movie = movie;
        this.dateOfChange = dateOfChange;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "value=" + value +
                ", movie=" + movie +
                ", dateOfChange=" + dateOfChange +
                '}';
    }
}
