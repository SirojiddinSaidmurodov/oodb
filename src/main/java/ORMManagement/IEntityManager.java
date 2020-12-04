/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

public interface IEntityManager<I> {
    /**
     * Method for saving entity to DB
     *
     * @param entity entity object
     */
    void persist(Object entity);

    /**
     * Method for updating the database record of this entity
     *
     * @param entity entity object
     * @param <T>    class of entity
     */
    <T> T merge(T entity);

    /**
     * Method for deleting the database record of entity
     *
     * @param entity entity that you want to delete
     */
    void remove(Object entity);

    /**
     * Method for getting an entity from database
     *
     * @param id          id of entity record in database
     * @param <T>         class of entity
     * @param entityClass class object of entity class
     */
    <T> T find(Class<T> entityClass, I id);

    /**
     * Refresh the object fields from database
     *
     * @param entity entity
     */
    void refresh(Object entity);
}
