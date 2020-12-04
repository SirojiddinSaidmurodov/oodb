/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EntityManager implements IEntityManager<Long> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void persist(Entity<Long> entity) {

    }

    @Override
    public <T> T merge(T entity) {
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
    public List<Entity<Long>> findAll() {

        return null;
    }

    @Override
    public Entity<Long> find(Class<Entity<Long>> entityClass, Long id) {
        return null;
    }

    @Override
    public void refresh(Entity<Long> entity) {

    }
}
