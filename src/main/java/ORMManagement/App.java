/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import MoviePortal.Person;

import java.io.FileReader;
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
        Entity<Long> e = new Person();
        Class<Person> entityClass = Person.class;
        List<Person> collect = entityManager.findAll(entityClass).stream().map(entity -> (Person) entity).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(entityManager.findAll(entityClass).size());
    }
}
