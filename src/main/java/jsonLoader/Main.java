package jsonLoader;

import MoviePortal.Personality;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Personality> personalities = JsonDealer.loadPersonality();
        System.out.println(personalities);
        Personality personality = JsonDealer.findPersonByName(personalities, "Джет Ли");
        personality.setName("Акакий Акакиевич");
        JsonDealer.saveAll(personalities);
    }
}
