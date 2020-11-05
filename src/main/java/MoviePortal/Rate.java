package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.OneToOne;

import java.util.Date;

@Entity
public class Rate {
    @Column
    private byte value;
    @Column
    @OneToOne
    private Movie movie;
    @Column
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
