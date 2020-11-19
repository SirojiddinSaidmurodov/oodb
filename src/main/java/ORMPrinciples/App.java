/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMPrinciples;


import ObjModelAnalysis.annotations.Entity;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ObjModelAnalysis.App.find;

public class App {

    public static void main(String[] args) {
        ArrayList<String> tables = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/OODB";
            connection = DriverManager.getConnection(dbURL, "postgres", "131214");
            tables = getTables(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        ArrayList<String> classNames = new ArrayList<>();
        List<Class<?>> classList = new ArrayList<>();
        for (Class<?> c :
                find()) {
            if (c.isAnnotationPresent(Entity.class)) {
                classNames.add(c.getSimpleName());
                classList.add(c);
            }
        }
        System.out.println(classNames);
        System.out.println(tables);
        if (classNames.size() != tables.size()) {
            System.out.println("Amount of tables and entities is not equal");
        } else {
            for (int i = 0; i < tables.size(); i++) {
                if (!classNames.get(i).toLowerCase().equals(tables.get(i))) {
                    System.out.println("Table is not present");
                }
            }
            System.out.println("That's alright!");
        }
        List<String> fields = null;
        for (int i = 0, tablesSize = tables.size(); i < tablesSize; i++) {
            String table = tables.get(i);
            Class<?> aClass = classList.get(i);
            if (connection != null) {
                try {
                    fields = getFields(connection, table);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println(table + "  " + aClass.getSimpleName());
                System.out.println("--------------------");
                System.out.println(fields);
                System.out.println(Arrays.toString(Arrays.stream(aClass.getDeclaredFields()).map(Field::getName).toArray()));
                System.out.println("\n\n");

            }
        }
    }

    public static ArrayList<String> getTables(Connection connection) {
        ArrayList<String> tables = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public static List<String> getFields(Connection connection, String tableName) throws SQLException {

        List<String> lst = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement(
                "SELECT a.attname " +
                        "FROM pg_catalog.pg_attribute a " +
                        "WHERE a.attrelid = (SELECT c.oid FROM pg_catalog.pg_class c " +
                        "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                        " WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ? )" +
                        " AND a.attnum > 0 AND NOT a.attisdropped");

        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            String s = resultSet.getString("attname");
            lst.add(s);
        }
        st.close();
        return lst;
    }
}
