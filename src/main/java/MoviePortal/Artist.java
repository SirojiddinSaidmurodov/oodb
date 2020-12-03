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
public class Artist {
    @Id
    private long id;
    @Column
    @ManyToOne
    private Personality person;
    @Column
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
