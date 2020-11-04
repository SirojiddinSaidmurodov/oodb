package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.ManyToOne;

@Entity
public class Actor {
    @Column
    private String role;
    @Column
    @ManyToOne
    private Personality person;

    public Actor(String role, Personality person) {
        this.role = role;
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Personality getPerson() {
        return person;
    }

    public void setPerson(Personality person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "role='" + role + '\'' +
                ", person=" + person +
                '}';
    }
}
