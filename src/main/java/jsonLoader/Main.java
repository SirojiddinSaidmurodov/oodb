package jsonLoader;

import MoviePortal.Personality;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Personality> personalities = JsonDealer.loadPersonality();
        personalities.sort(Comparator.comparing(Personality::getName));
        System.out.println(personalities);
        System.out.println("\n\n Found:  " + findByName("Акакий Акакиевич", personalities) + "\n\n");
//        Personality personality = JsonDealer.findPersonByName(personalities, "Джет Ли");
//        personality.setName("Акакий Акакиевич");
        JsonDealer.saveAll(personalities);

    }

    public static Personality findByName(String name, List<Personality> personalities) {
        for (Personality person :
                personalities) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
}