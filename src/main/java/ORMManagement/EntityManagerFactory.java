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
    /**
     * HashMap for keeping database structure:
     * <p>
     * key — table name,
     * <p>
     * value — HashSet of table columns name
     */
    private final HashMap<String, HashSet<String>> tables = new HashMap<>();
    /**
     * Connection to database
     */
    private Connection connection;
    /**
     * Properties of database connection
     */
    private Properties properties;

    /**
     * EntityManager Factory default constructor
     *
     * @param properties properties of database connection
     */
    public EntityManagerFactory(Properties properties) {
        this.properties = properties;
    }

    public HashMap<String, HashSet<String>> getTablesScheme() {
        return tables;
    }

    /**
     * Method for creating {@linkplain EntityManager}
     *
     * @return new {@linkplain EntityManager} instance
     */
    public IEntityManager<Long> createEM() throws Exception {
        if (isDbValid()) {
            IEntityManager<Long> entityManager = new EntityManager(connection);
            entityManager.setTables(tables);
            return entityManager;
        } else throw new Exception("The database is not correct");
    }

    /**
     * Method to connect to database server
     */
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

    /**
     * Method for checking the database correctness.
     *
     * @return true if database is correct, else — false
     */
    public boolean isDbValid() {
        connect();
        analyzeDB();

        List<Class<?>> classes = find()
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                .collect(Collectors.toList());
        for (Class<?> entity : classes) {
            for (Field field : entity.getDeclaredFields()) {
                String fieldName = field.getName().toLowerCase();
                if (field.isAnnotationPresent(OneToMany.class)) {
                    if (!isForeignKeyPresent(entity, field)) return false;
                } else if (field.isAnnotationPresent(ManyToOne.class)) {
                    if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName + "_id")) return false;
                } else if (!tables.get(entity.getSimpleName().toLowerCase()).contains(fieldName)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method for checking whether the foreign key that refers to entity exists in the table that represents field
     *
     * @param entity class of entity that has field annotated with {@linkplain ManyToOne} annotation
     * @param field  Field instance collection that keeps instances of entities
     * @return boolean, true if foreign key exists
     */
    private boolean isForeignKeyPresent(Class<?> entity, Field field) {
        Class<?> fieldClass = field.getType();
        if (Collection.class.isAssignableFrom(fieldClass)) {
            Type fieldGenericType = field.getGenericType();
            if (fieldGenericType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) fieldGenericType;
                String genericClassName = ((Class<?>) paramType.getActualTypeArguments()[0]).getSimpleName().toLowerCase();
                return tables.get(genericClassName).contains(entity.getSimpleName().toLowerCase() + "_id");
            }
        }
        return false;
    }

    /**
     * Method for analyzing database, creates connection to DB and fills {@linkplain EntityManagerFactory#tables}
     */
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
