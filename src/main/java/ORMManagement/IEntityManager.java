/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

public interface IEntityManager {
    void persist(Object entity);

    <T> T merge(T entity);

    void remove(Object entity);

    <T> T find(Class<T> entityClass, Object entity);

    void refresh(Object entity);
}
