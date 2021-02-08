/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import ObjModelAnalysis.annotations.ManyToOne;
import ObjModelAnalysis.annotations.OneToMany;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EntityManager implements IEntityManager<Long> {
    private Connection connection;
    private HashMap<String, HashSet<String>> tables;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public void setTables(HashMap<String, HashSet<String>> tables) {
        this.tables = tables;
    }

    @Override
    public void persist(Entity<Long> entity) {
        persist(entity, null, null);
    }

    private void persist(Entity<Long> entity, String foreignColumnName, Long foreignId) {
        entity.setId(null);

        HashMap<String, Long> manyToOneIds = null;
        if (hasManyToOne(entity.getClass())) {
            manyToOneIds = new HashMap();

            for (Field manyToOneField : getManyToOneFields(entity.getClass())) {
                String name = manyToOneField.getName();
                name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Class<?> type = manyToOneField.getType();
                try {
                    Method getter = entity.getClass().getMethod(name);
                    Entity<Long> foreign = (Entity<Long>) getter.invoke(entity);
                    persist(foreign);

                    manyToOneIds.put(
                            foreign.getClass().getSimpleName().toLowerCase() + "_id",
                            foreign.getId());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<Field> simpleFields = getSimpleFields(entity.getClass());
        Long id = null;
        StringBuilder columns = new StringBuilder();
        StringBuilder valueSQL = new StringBuilder();
        ArrayList<Object> values = new ArrayList<>();
        if (foreignColumnName != null) {
            columns.append(foreignColumnName + ",");
            valueSQL.append("?,");
            values.add(foreignId);
        }

        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (!(declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToOne.class))) {
                String name = declaredField.getName();
                if (name.equals("id")) {
                    continue;
                }
                String s = declaredField.getName().toLowerCase();
                columns.append(s);
                columns.append(",");
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(","));
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (!(declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToOne.class))) {
                String name = declaredField.getName();
                if (name.equals("id")) {
                    continue;
                }
                name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method getter = null;
                try {
                    getter = entity.getClass().getMethod(name);
                    Object invoke = getter.invoke(entity);
                    valueSQL.append("?,");
                    values.add(invoke);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (manyToOneIds != null) {
            columns.append(",");
            for (String columnname : manyToOneIds.keySet()) {
                columns.append(columnname + ",");
                valueSQL.append("?,");
                values.add(manyToOneIds.get(columnname));
            }
            columns.deleteCharAt(columns.lastIndexOf(","));
        }
        valueSQL.deleteCharAt(valueSQL.lastIndexOf(","));


        try {
            String table = entity.getClass().getSimpleName().toLowerCase();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table +
                            " (" + columns + ") VALUES" +
                            " (" + valueSQL + ") RETURNING id");
            for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
                Object value = values.get(i);
                preparedStatement.setObject(i + 1, value);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        entity.setId(id);


        if (hasOneToMany(entity.getClass())) {
            ArrayList<Field> oneToManyFields = getOneToManyFields(entity.getClass());
            for (Field oneToManyField : oneToManyFields) {
                String name = oneToManyField.getName();
                name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                try {
                    Method getter = entity.getClass().getMethod(name);
                    List entities = (List) getter.invoke(entity);
                    if (entities != null) {
                        for (Object o : entities) {
                            Entity<Long> item = (Entity<Long>) o;

                            String fColumnName = entity.getClass().getSimpleName().toLowerCase() + "_id";
                            persist(
                                    item,
                                    fColumnName,
                                    id);
                        }
                    }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public <E extends Entity<Long>> E merge(E entity) {
        Long id = entity.getId();
        ArrayList<Field> fields = getSimpleFields(entity.getClass());
        String tableName = entity.getClass().getSimpleName().toLowerCase();
        StringBuilder columns = new StringBuilder();
        ArrayList<Object> values = new ArrayList<>();
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (!(declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToOne.class))) {
                String name = declaredField.getName();
                if (name.equals("id")) {
                    continue;
                }
                columns.append(name.toLowerCase());
                columns.append(" = ?");

                name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);

                try {
                    Method getter = entity.getClass().getMethod(name);
                    Object invoke = getter.invoke(entity);
                    values.add(invoke);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                columns.append(",");
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(","));

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableName +
                            " SET " + columns + " WHERE " + " id = " + id);
            for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
                Object value = values.get(i);
                preparedStatement.setObject(i + 1, value);
            }
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Entity<Long> entity) {
        Number id = entity.getId();
        String tableName = entity.getClass().getSimpleName().toLowerCase();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id=?");
            preparedStatement.setLong(1, id.longValue());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Entity<Long>> findAll(Class<?> entityClass) {
        return findAll(entityClass, "");
    }

    private List<Entity<Long>> findAll(Class<?> entityClass, String whereExpression) {
        ArrayList<Field> simpleFields = getSimpleFields(entityClass);
        String simpleColumns = getSimpleColumns(entityClass);
        ArrayList<Entity<Long>> simpleEntities = getSimpleEntitiesFromDB(entityClass, simpleFields, simpleColumns, whereExpression);
        if (hasManyToOne(entityClass)) {
            ArrayList<Field> manyToOneFields = getManyToOneFields(entityClass);
            for (Entity<Long> simpleEntity : simpleEntities) {
                for (Field field : manyToOneFields) {
                    setFkObjectToEntity(
                            simpleEntity,
                            entityClass,
                            field);
                }
            }
        }
        if (hasOneToMany(entityClass)) {
            ArrayList<Field> oneToManyFields = getOneToManyFields(entityClass);
            for (Entity<Long> entity : simpleEntities) {
                for (Field field : oneToManyFields) {
                    setListToField(
                            entity,
                            entityClass,
                            field);
                }
            }
        }
        return simpleEntities;
    }

    @Override
    public Entity<Long> find(Class<Entity<Long>> entityClass, Long id) {
        ArrayList<Field> simpleFields = getSimpleFields(entityClass);
        String simpleColumns = getSimpleColumns(entityClass);
        Entity<Long> simpleEntity = getSimpleEntityFromDB(entityClass, simpleFields, simpleColumns, id);
        if (hasManyToOne(entityClass)) {
            ArrayList<Field> manyToOneFields = getManyToOneFields(entityClass);
            for (Field field : manyToOneFields) {
                setFkObjectToEntity(
                        simpleEntity,
                        entityClass,
                        field);
            }
        }
        if (hasOneToMany(entityClass)) {
            ArrayList<Field> oneToManyFields = getOneToManyFields(entityClass);
            for (Field oneToManyField : oneToManyFields) {
                setListToField(
                        simpleEntity,
                        entityClass,
                        oneToManyField);
            }
        }
        return simpleEntity;
    }

    private void setListToField(Entity<Long> entity, Class<?> entityClass, Field field) {
        ParameterizedType listGenericType = (ParameterizedType) field.getGenericType();
        Class<?> foreignEntitiesClass = (Class<?>) listGenericType.getActualTypeArguments()[0];

        String foreignTableName = (foreignEntitiesClass).getSimpleName().toLowerCase();
        String entityTableName = entityClass.getSimpleName().toLowerCase();

        List<Entity<Long>> foreignEntities = findAll(
                foreignEntitiesClass,
                " WHERE " + entityTableName + "_id = " + entity.getId());

        String name = field.getName();
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Class<?> type = field.getType();
        try {
            Method setter = entityClass.getMethod(
                    name,
                    type);
            setter.invoke(entity, foreignEntities);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void setFkObjectToEntity(Entity<Long> entity, Class<?> entityType, Field fieldWithFK) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT " + fieldWithFK.getName().toLowerCase() + "_id" +
                            " FROM " + entityType.getSimpleName().toLowerCase() +
                            " WHERE id=" + entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long fk = resultSet.getLong(1);
            String name = fieldWithFK.getName();
            name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Class<?> type = fieldWithFK.getType();
            Method setter = entityType.getMethod(
                    name,
                    type);
            setter.invoke(
                    entity,
                    find(
                            (Class<Entity<Long>>) type,
                            fk));
        } catch (SQLException | NoSuchMethodException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Entity<Long> getSimpleEntityFromDB(Class<Entity<Long>> entityClass, ArrayList<Field> fields, String columns, Long id) {
        Entity<Long> entity = null;
        try {
            String tableName = entityClass.getSimpleName().toLowerCase();
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT " + columns + " FROM " + tableName + " WHERE id=" + id);
            ResultSet resultSet = selectStatement.executeQuery();
            Constructor<?> constructor = entityClass.getDeclaredConstructor(null);
            entity = getSimpleEntityFromResultSet(entityClass, fields, resultSet, constructor);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private ArrayList<Entity<Long>> getSimpleEntitiesFromDB(Class<?> entityClass, ArrayList<Field> fields, String columnsSql, String where) {
        ArrayList<Entity<Long>> result = new ArrayList<>();
        try {
            String tableName = entityClass.getSimpleName().toLowerCase();
            StringBuilder select_ = new StringBuilder().append("SELECT ").append(columnsSql).append(" FROM ").append(tableName);
            if (!where.isEmpty()) {
                select_.append(where);
            }
            PreparedStatement selectStatement = connection.prepareStatement(select_.toString());
            ResultSet resultSet = selectStatement.executeQuery();

            Constructor<?> constructor = entityClass.getDeclaredConstructor(null);

            Entity<Long> entity = getSimpleEntityFromResultSet(entityClass, fields, resultSet, constructor);
            while (entity != null) {
                result.add(entity);
                entity = getSimpleEntityFromResultSet(entityClass, fields, resultSet, constructor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Entity<Long> getSimpleEntityFromResultSet(Class<?> entityClass, ArrayList<Field> fields, ResultSet resultSet, Constructor<?> constructor) {
        Entity<Long> entity = null;
        try {
            if (resultSet.next()) {
                entity = (Entity<Long>) constructor.newInstance(null);
                for (int i = 0; i < fields.size(); i++) {
                    String name = fields.get(i).getName();
                    name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Class<?> type = fields.get(i).getType();
                    Method setter = entityClass.getMethod(
                            name,
                            type);
                    setter.invoke(
                            entity,
                            resultSet.getObject(i + 1, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    private ArrayList<Field> getSimpleFields(Class<?> entityClass) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (!(declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToOne.class))) {
                fields.add(declaredField);
            }
        }
        return fields;
    }

    private ArrayList<Field> getManyToOneFields(Class<?> entityClass) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(ManyToOne.class)) {
                fields.add(declaredField);
            }
        }
        return fields;
    }

    private ArrayList<Field> getOneToManyFields(Class<?> entityClass) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(OneToMany.class)) {
                fields.add(declaredField);
            }
        }
        return fields;
    }

    private String getSimpleColumns(Class<?> entityClass) {
        StringBuilder columns = new StringBuilder();
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (!(declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToOne.class))) {
                String s = declaredField.getName().toLowerCase();
                columns.append(s);
                columns.append(",");
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(","));
        return columns.toString();
    }

    private boolean hasOneToMany(Class<?> entityClass) {
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(OneToMany.class)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasManyToOne(Class<?> entityClass) {
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(ManyToOne.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void refresh(Entity<Long> entity) {

    }
}
