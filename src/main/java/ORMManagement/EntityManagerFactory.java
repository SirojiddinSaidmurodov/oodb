/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.ManyToOne;
import ObjModelAnalysis.annotations.OneToMany;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static ORMPrinciples.App.getFields;
import static ORMPrinciples.App.getTables;
import static ObjModelAnalysis.App.find;

public class EntityManagerFactory {
    private final HashMap<String, HashSet<String>> tables = new HashMap<>();
    private Properties properties;
    private Connection connection;

    public EntityManagerFactory(Properties properties) {
        this.properties = properties;
    }

    public HashMap<String, HashSet<String>> getTablesScheme() {
        return tables;
    }

    public IEntityManager createEM() {
        return null;
    }

    public void connect() {
        if (connection == null) {
            try {
                Class.forName(properties.getProperty("driver"));
                connection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("user"),
                        properties.getProperty("password"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDbValid() {
        connect();
        analyzeDB();
        List<Class<?>> classes = find()
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                .collect(Collectors.toList());
        if (classes.size() != tables.size()) {
            return false;
        } else {
            for (Class<?> entity : classes) {
                for (Field field : entity.getDeclaredFields()) {
                    String fieldName = field.getName().toLowerCase();

                    if (field.isAnnotationPresent(OneToMany.class)) {
                        Class<?> fieldClass = field.getType();
                        if (Collection.class.isAssignableFrom(fieldClass)) {
                            Type fieldGenericType = field.getGenericType();
                            if (fieldGenericType instanceof ParameterizedType) {
                                ParameterizedType paramType = (ParameterizedType) fieldGenericType;
                                String genericClassName = ((Class<?>) paramType.getActualTypeArguments()[0]).getSimpleName().toLowerCase();
                                if (!tables.get(genericClassName).contains(entity.getSimpleName().toLowerCase() + "_id")){
                                    return false;
                                }
                            }
                        }
                    } else if (field.isAnnotationPresent(ManyToOne.class)) {
                        if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName + "_id")) {
                            return false;
                        }
                    } else {
                        if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    private void analyzeDB() {
        connect();
        List<String> tablesList = getTables(connection);
        for (String table : tablesList) {
            List<String> fields = null;
            try {
                fields = getFields(connection, table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert fields != null;
            HashSet<String> columns = new HashSet<>(fields);
            tables.put(table, columns);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() {
        connect();
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
