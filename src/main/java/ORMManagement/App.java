/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import MoviePortal.Actor;
import MoviePortal.Artist;
import MoviePortal.Movie;
import MoviePortal.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/connection.properties"));
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        System.out.print("Is database valid? - ");
        System.out.println(factory.isDbValid());

        IEntityManager<Long> entityManager = factory.createEM();

        System.out.println(gson.toJson(entityManager.findAll(Person.class)));
        System.out.println("\n\n");
        List<Entity<Long>> allArtists = entityManager.findAll(Artist.class);
        System.out.println(gson.toJson(allArtists));

        List<Entity<Long>> allMovies = entityManager.findAll(Movie.class);

        System.out.println(gson.toJson(allMovies));
        System.out.println("\n\n\u001B[01m\u001B[32m========================== Persist Test ================================\u001B[0m\n\n");
        System.out.println("\u001B[36mBEFORE PERSISTING\u001B[0m");
        ArrayList<Actor> actors = new ArrayList<>();
        actors.add(new Actor("TORRR", new Person(
                "Chris Hemsworth",
                LocalDate.of(1985,
                        10,
                        14),
                "CHRIIIIIISSSSS")));
        Movie movie = new Movie("Thor",
                null,
                LocalDate.of(2018, 10, 15),
                actors,
                new ArrayList<>());
        System.out.println(gson.toJson(movie));
        System.out.println("\n\n\u001B[36mAFTER PERSISTING\u001B[0m");
        entityManager.persist(movie);

        System.out.println(gson.toJson(movie));
        System.out.println("\n\n\u001B[01m\u001B[32m========================== Editing Test ================================\u001B[0m\n\n");
        movie.setName("Thor: Rognarok");
        entityManager.merge(movie);
        System.out.println(gson.toJson(entityManager.findAll(Movie.class)));
        System.out.println("\n\n\u001B[01m\u001B[32m========================== Deleting Test ================================\u001B[0m\n\n");
        entityManager.remove(movie);
        System.out.println(gson.toJson(entityManager.findAll(Movie.class)));
    }
}
