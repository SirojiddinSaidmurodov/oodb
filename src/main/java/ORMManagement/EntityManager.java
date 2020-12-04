/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import java.sql.Connection;

public class EntityManager implements IEntityManager<Long> {

    public EntityManager(Connection connection) {

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

    }

    @Override
    public Entity<Long> find(Class<Entity<Long>> entityClass, Long id) {
        return null;
    }

    @Override
    public void refresh(Entity<Long> entity) {

    }
}
