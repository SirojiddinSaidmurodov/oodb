package jsonLoader;

import MoviePortal.Personality;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class JsonDealer {
    static Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
    public static List<Personality> loadPersonality() {
        File jsonfile = new File("persons.json");
        String jsonStr = "";
        if (jsonfile.exists()) {
            try {
                jsonStr = new String(Files.readAllBytes(jsonfile.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Personality[] personalities = gson.fromJson(jsonStr, Personality[].class);
        return Arrays.asList(personalities);
    }

    public static void saveAll(List<Personality> personalities){
        if (personalities != null && personalities.size()>0){
            String personsJSON =gson.toJson(personalities);
            System.out.println(personsJSON);
            try (OutputStream stream = new FileOutputStream(new File("persons.json"))) {
                stream.write(personsJSON.getBytes(StandardCharsets.UTF_8));
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Personality findPersonByName(List<Personality> persons, String searchName) {
        Personality result = null;

        for (Personality person : persons) {
            if (person.getName().equals(searchName)) {
                result = person;
            }
        }

        return result;
    }
}
