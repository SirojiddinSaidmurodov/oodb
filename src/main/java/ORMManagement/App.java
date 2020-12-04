/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import MoviePortal.Person;

import java.io.FileReader;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/connection.properties"));
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        System.out.println(factory.isDbValid());
        IEntityManager<Long> entityManager = factory.createEM();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(1973, Calendar.SEPTEMBER, 12);
        Date date = new Date(calendar.getTimeInMillis());
        Person person = new Person("asgdfg", date, "dshg");
        person.setID(1L);
        entityManager.remove(person);
    }
}
