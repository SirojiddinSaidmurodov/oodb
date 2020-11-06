package ObjModelAnalysis.graph;

import ObjModelAnalysis.annotations.Entity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GraphModel {

    private List<EntityNode> entityNodeList = new ArrayList<>();

    private List<Edge> edges = new ArrayList<>();

    public void addEntity(Class<?> c) {
        entityNodeList.add(new EntityNode(c));
    }

    public void fetchEntities(List<Class<?>> classList) {
        for (Class<?> aClass : classList) {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Entity.class)) {
                    this.addEntity(aClass);
                }
            }
        }
    }

    public void addEdge(Class<?> source, Class<?> target) {

    }

    public List<EntityNode> getEntityNodeList() {
        return entityNodeList;
    }

    public void setEntityNodeList(List<EntityNode> entityNodeList) {
        this.entityNodeList = entityNodeList;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
