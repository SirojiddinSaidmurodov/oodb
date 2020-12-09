/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.Id;
import ObjModelAnalysis.annotations.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Movie implements ORMManagement.Entity<Long> {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    @OneToMany
    private List<Rate> rates;
    @Column
    private LocalDate releaseDate;
    @Column
    @OneToMany
    private List<Actor> actors;
    @Column
    @OneToMany
    private List<Artist> artists;

    public Movie(String name, List<Rate> rates, LocalDate releaseDate, List<Actor> actors, List<Artist> artists) {
        this.name = name;
        this.rates = rates;
        this.releaseDate = releaseDate;
        this.actors = actors;
        this.artists = artists;
    }

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rates=" + rates +
                ", releaseDate=" + releaseDate +
                ", actors=" + actors +
                ", artists=" + artists +
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
