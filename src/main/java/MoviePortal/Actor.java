/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.Id;
import ObjModelAnalysis.annotations.ManyToOne;

@Entity
public class Actor implements ORMManagement.Entity<Long> {
    @Id
    private long id;
    @Column
    private String role;
    @Column
    @ManyToOne
    private Person person;

    public Actor(String role, Person person) {
        this.role = role;
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "role='" + role + '\'' +
                ", person=" + person +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
