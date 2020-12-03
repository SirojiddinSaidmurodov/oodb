/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package JSONLoader;

import MoviePortal.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class JsonDealer {
    static Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

    public static List<Person> loadPersonality() {
        File jsonfile = new File("persons.json");
        String jsonStr = "";
        if (jsonfile.exists()) {
            try {
                jsonStr = new String(Files.readAllBytes(jsonfile.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Person[] personalities = gson.fromJson(jsonStr, Person[].class);
        return Arrays.asList(personalities);
    }

    public static void saveAll(List<Person> personalities) {
        if (personalities != null && personalities.size() > 0) {
            String personsJSON = gson.toJson(personalities);
            System.out.println(personsJSON);
            try (OutputStream stream = new FileOutputStream(new File("persons.json"))) {
                stream.write(personsJSON.getBytes(StandardCharsets.UTF_8));
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Person findPersonByName(List<Person> persons, String searchName) {
        Person result = null;

        for (Person person : persons) {
            if (person.getName().equals(searchName)) {
                result = person;
            }
        }

        return result;
    }
}
