/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import java.sql.Connection;

public class EntityManager implements IEntityManager {

    public EntityManager(Connection connection) {

    }

    @Override
    public void persist(Object entity) {

    }

    @Override
    public <T> T merge(T entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }

    @Override
    public <T> T find(Class<T> entityClass, Object entity) {
        return null;
    }

    @Override
    public void refresh(Object entity) {

    }
}
