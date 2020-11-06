package ObjModelAnalysis.graph;

import ObjModelAnalysis.annotations.Entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphModel {

    private ArrayList<EntityNode> entityNodeList = new ArrayList<>();

    private ArrayList<Edge> edges = new ArrayList<>();

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

    public void fetchEdges() {
        ArrayList<String> types = new ArrayList<>();
        Arrays.stream(RelationType.values()).forEach(type -> {
            types.add(type.name());
        });
        for (EntityNode entityNode : entityNodeList) {
            for (Field field : entityNode.getEntityClass().getDeclaredFields()) {
                if (field.getType().isAnnotationPresent(Entity.class)) {
                    for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                        if(types.contains(declaredAnnotation.toString())){
                            addEdge(entityNode.getClass(),field.getClass(),declaredAnnotation);
                        }
                    }

                }
            }
        }
    }

    public void addEdge(Class<?> source, Class<?> target, Annotation annotation) {
        EntityNode sourceNode = entityNodeList.stream().filter(entityNode -> entityNode.getEntityClass().equals(source)).findAny().orElse(null);
        EntityNode targetNode = entityNodeList.stream().filter(entityNode -> entityNode.getEntityClass().equals(target)).findAny().orElse(null);
        RelationType type = Arrays.stream(RelationType.values()).filter(type1 -> type1.name().equals(annotation.getClass().getName())).findAny().orElse(null);
        if (type != null && sourceNode != null && targetNode != null) {
            edges.add(new Edge(sourceNode, targetNode, type));
        }
    }

    public List<EntityNode> getEntityNodeList() {
        return entityNodeList;
    }

    public void setEntityNodeList(ArrayList<EntityNode> entityNodeList) {
        this.entityNodeList = entityNodeList;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}
