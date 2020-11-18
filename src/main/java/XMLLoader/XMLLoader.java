/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package XMLLoader;

import MoviePortal.Movie;
import MoviePortal.MoviePortal;
import MoviePortal.Rate;
import MoviePortal.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class XMLLoader {
    public static void main(String[] args) {
        ArrayList<User> userSet = new ArrayList<User>();
        userSet.add(new User(
                "Маша",
                new ArrayList<Rate>(),
                "masha@example.com",
                "klkwgualsbrg"));
        userSet.add(new User(
                "Алекс",
                new ArrayList<Rate>(),
                "mail@example.com",
                "asdad"));
        ArrayList<Movie> movieSet = new ArrayList<Movie>();
        MoviePortal portal = new MoviePortal("КиноПоиск", userSet, movieSet);
        save(portal);

        MoviePortal unmarshalled = load();
        ArrayList<User> sorted = (ArrayList<User>) unmarshalled.getUsers();
        sorted.sort(Comparator.comparing(User::getName));
        System.out.println(unmarshalled);
        System.out.println("\n\n" + sorted);

        System.out.println("\n\n" + findByName(sorted, "Маша"));
    }

    public static void save(MoviePortal portal) {
        try {
            JAXBContext context = JAXBContext.newInstance(MoviePortal.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(portal, new File("portal.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static MoviePortal load() {
        try {
            JAXBContext context = JAXBContext.newInstance(MoviePortal.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (MoviePortal) unmarshaller.unmarshal(new File("portal.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User findByName(ArrayList<User> users, String name) {
        for (User user :
                users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
