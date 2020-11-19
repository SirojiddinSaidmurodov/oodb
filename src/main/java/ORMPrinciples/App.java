/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMPrinciples;


import ObjModelAnalysis.annotations.Entity;

import java.sql.*;
import java.util.ArrayList;

import static ObjModelAnalysis.App.find;

public class App {

    public static void main(String[] args) {

        System.out.println(is_entities_present_on_table());
    }

    public static boolean is_entities_present_on_table() {
        ArrayList<String> tables = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/OODB";
            Connection connection = DriverManager.getConnection(dbURL, "postgres", "131214");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT table_name " +
                            "FROM information_schema.tables " +
                            "WHERE table_type = 'BASE TABLE' AND " +
                            "table_schema NOT IN ('pg_catalog', 'information_schema')" +
                            "ORDER BY table_name");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> classNames = new ArrayList<>();
        for (Class<?> c :
                find()) {
            if (c.isAnnotationPresent(Entity.class)) {
                classNames.add(c.getSimpleName());
            }
        }
        System.out.println(classNames);
        System.out.println(tables);
        if (classNames.size() != tables.size()) {
            return false;
        } else {
            for (int i = 0; i < tables.size(); i++) {
                if (!classNames.get(i).toLowerCase().equals(tables.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
