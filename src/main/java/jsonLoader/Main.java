package jsonLoader;

import MoviePortal.Personality;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Personality> personalities = JsonDealer.loadPersonality();
        personalities.sort(new Comparator<Personality>() {
            @Override
            public int compare(Personality o1, Personality o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println(personalities);
//        Personality personality = JsonDealer.findPersonByName(personalities, "Джет Ли");
//        personality.setName("Акакий Акакиевич");
        JsonDealer.saveAll(personalities);

    }
}
