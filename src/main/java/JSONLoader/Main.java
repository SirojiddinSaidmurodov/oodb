/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package JSONLoader;

import MoviePortal.Person;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> personalities = JsonDealer.loadPersonality();
        personalities.sort(Comparator.comparing(Person::getName));
        System.out.println(personalities);
        System.out.println("\n\n Found:  " + findByName("Акакий Акакиевич", personalities) + "\n\n");
//        Personality personality = JsonDealer.findPersonByName(personalities, "Джет Ли");
//        personality.setName("Акакий Акакиевич");
        JsonDealer.saveAll(personalities);

    }

    public static Person findByName(String name, List<Person> personalities) {
        for (Person person :
                personalities) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
}