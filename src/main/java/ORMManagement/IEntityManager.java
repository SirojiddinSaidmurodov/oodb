/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IEntityManager<Identifier extends Number> {
    /**
     * Method for saving entity to DB
     *
     * @param entity entity object
     */
    void persist(Entity<Identifier> entity);

    /**
     * Method for updating the database record of this entity
     *
     * @param entity entity object
     * @param <E>    class of entity
     * @return updated entity object
     */
    <E extends Entity<Identifier>> E merge(E entity);

    /**
     * Method for deleting the database record of entity
     *
     * @param entity entity that you want to delete
     */
    void remove(Entity<Identifier> entity);

    /**
     * Method for getting all entities from table
     *
     * @return List of entities
     */
    List<Entity<Identifier>> findAll(Class<?> entityClass);

    /**
     * Method for getting an entity from database
     *
     * @param id          id of entity record in database
     * @param entityClass class object of entity class
     * @return found entity, null if not found
     */
    Entity<Identifier> find(Class<Entity<Identifier>> entityClass, Identifier id);

    /**
     * Refresh the object fields from database
     *
     * @param entity entity
     */
    void refresh(Entity<Identifier> entity);

    void setTables(HashMap<String, HashSet<String>> tables);
}
