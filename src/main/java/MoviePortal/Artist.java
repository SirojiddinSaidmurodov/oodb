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
public class Artist implements ORMManagement.Entity<Long> {
    @Id
    private long id;
    @Column
    @ManyToOne
    private Person person;
    @Column
    private String occupation;

    public Artist(Person person, String occupation) {
        this.person = person;
        this.occupation = occupation;
    }

    public Artist() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
