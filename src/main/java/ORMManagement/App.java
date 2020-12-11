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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/connection.properties"));
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        System.out.println(factory.isDbValid());

        IEntityManager<Long> entityManager = factory.createEM();

        Class<Person> personClass = Person.class;

        List<Person> collect = entityManager
                .findAll(personClass)
                .stream()
                .map(entity -> (Person) entity)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("\n\n");
        List<Entity<Long>> all = entityManager.findAll(Artist.class);

        all.stream().map(entity -> (Artist) entity).forEach(System.out::println);

        List<Entity<Long>> all1 = entityManager.findAll(Movie.class);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(all1.toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
        System.out.println("\n\n==========================PersistTest================================\n\n");
        System.out.println("BEFORE PERSISTING");
        ArrayList<Actor> actors = new ArrayList<>();
        actors.add(new Actor("TORRR", new Person(
                "Chris Hemsword",
                LocalDate.of(1985,
                        10,
                        14),
                "CHRIIIIIISSSSS")));
        Movie movie = new Movie("Tor",
                null,
                LocalDate.of(2018, 10, 15),
                actors,
                new ArrayList<>());
        System.out.println(gson.toJson(jp.parse(movie.toString())));
        System.out.println("\n\nAFTER PERSISTING");
        entityManager.persist(movie);

        System.out.println(gson.toJson(jp.parse(movie.toString())));
    }
}
