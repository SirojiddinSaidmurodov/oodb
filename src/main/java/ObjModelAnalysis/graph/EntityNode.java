/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ObjModelAnalysis.graph;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityNode {

    private Class entityClass;
    private String entityName;
    private List<EntityAttribute> atributes = new ArrayList<>();

    public EntityNode(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.entityName = entityClass.getName();
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            atributes.add(new EntityAttribute(declaredField.getName(), declaredField.getType().getName()));
        }

    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<EntityAttribute> getAtributes() {
        return atributes;
    }

    public void setAtributes(List<EntityAttribute> atributes) {
        this.atributes = atributes;
    }

    @Override
    public String toString() {
        return "<node id=\"" + entityName + "\"/>";
    }
}
