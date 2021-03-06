/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package JSONonDB;

import MoviePortal.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Loader {
    public static List<Person> load(Connection connection) {
        String pgstr = "";
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT content from personb");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                pgstr = result.getString("content");
                System.out.println(pgstr);
            }
            statement.close();
            Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
            Person[] personalities = gson.fromJson(pgstr, Person[].class);
            statement = connection.prepareStatement("SELECT content->0 as c from personb");

            result = statement.executeQuery();
            while (result.next()) {
                pgstr = result.getString("c");
                System.out.println(pgstr);
            }
            return personalities == null ? null : Arrays.asList(personalities);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static void save(List<Person> personalities, Connection connection) {

        try {
            Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
            String personsAsJson = gson.toJson(personalities);
            PreparedStatement statement = connection.prepareStatement(
                    "insert into person (content) values (cast( ? as json))");
            statement.setString(1, personsAsJson);

            int count = statement.executeUpdate();

            System.out.println(count + " records added!");

            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveb(List<Person> personalities, Connection connection) {

        try {
            Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
            String personsAsJson = gson.toJson(personalities);
            PreparedStatement statement = connection.prepareStatement(
                    "insert into personb (content) values (cast( ? as jsonb))");
            statement.setString(1, personsAsJson);

            int count = statement.executeUpdate();

            System.out.println(count + " records added!");

            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
