package MoviePortal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "moviePortal")
public class MoviePortal {
    private String name;
    private Set<User> users;
    private Set<Movie> movies;

    public MoviePortal(String name, Set<User> users, Set<Movie> movies) {
        this.name = name;
        this.users = users;
        this.movies = movies;
    }
    public MoviePortal(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
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
