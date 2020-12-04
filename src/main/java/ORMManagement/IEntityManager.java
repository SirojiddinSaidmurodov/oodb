/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

public interface IEntityManager<I extends Number> {
    /**
     * Method for saving entity to DB
     *
     * @param entity entity object
     */
    void persist(Entity<I> entity);

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
    void remove(Entity<I> entity);

    /**
     * Method for getting an entity from database
     *
     * @param id          id of entity record in database
     * @param entityClass class object of entity class
     */
    Entity<I> find(Class<Entity<I>> entityClass, I id);

    /**
     * Refresh the object fields from database
     *
     * @param entity entity
     */
    void refresh(Entity<I> entity);
}
