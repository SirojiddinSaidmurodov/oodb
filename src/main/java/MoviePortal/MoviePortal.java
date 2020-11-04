package MoviePortal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "moviePortal")
public class MoviePortal {
    private String name;
    private List<User> users;
    private List<Movie> movies;

    public MoviePortal(String name, List<User> users, List<Movie> movies) {
        this.name = name;
        this.users = users;
        this.movies = movies;
    }

    public MoviePortal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MoviePortal{" +
                "name='" + name + '\'' +
                ", users=" + users +
                ", movies=" + movies +
                '}';
    }
}
