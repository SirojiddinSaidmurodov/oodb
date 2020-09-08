package MoviePortal;

import java.util.Set;

public class MoviePortal {
    private String name;
    private Set<User> users;
    private Set<Movie> movies;

    @Override
    public String toString() {
        return "MoviePortal{" +
                "name='" + name + '}';
    }

    public MoviePortal(String name, Set<User> users, Set<Movie> movies) {
        this.name = name;
        this.users = users;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
