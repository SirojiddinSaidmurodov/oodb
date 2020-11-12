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

    public EntityNode getEntity(Class<?> aClass) {
        for (EntityNode entity :
                entityNodeList) {
            if (entity.getEntityClass().getSimpleName().equals(aClass.getSimpleName())) {
                return entity;
            }
        }
        return null;
    }

    public void fetchEdges() {
        ArrayList<String> types = new ArrayList<>();
        Arrays.stream(RelationType.values()).forEach(type -> {
            types.add(type.name());
        });
        for (EntityNode entityNode : entityNodeList) {
            for (Field field : entityNode.getEntityClass().getDeclaredFields()) {
                if (field.getType().isAnnotationPresent(Entity.class)) {
                    for (Annotation declaredAnnotation : field.getAnnotations()) {
                        if (types.contains(declaredAnnotation.annotationType().getSimpleName())) {
                            addEdge(entityNode, getEntity(field.getType()), declaredAnnotation);
                        }
                    }
                }
            }
        }
    }

    public void addEdge(EntityNode source, EntityNode target, Annotation annotation) {

        edges.add(new Edge(source, target, RelationType.valueOf(annotation.annotationType().getSimpleName())));
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

    @Override
    public String toString() {
//        return "GraphModel{" +
//                "entityNodeList=" + entityNodeList +
//                ", edges=" + edges +
//                '}';
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"  \n" +
                "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "\txsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns \n" +
                "\thttp://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n" +
                "\t<graph id=\"G\" edgedefault=\"undirected\">\n");
        for (EntityNode entityNode : entityNodeList) {
            builder.append("\t\t").append(entityNode.toString()).append("\n");
        }
        for (Edge edge : edges) {
            builder.append("\t\t").append(edge.toString()).append("\n");
        }
        builder.append("\t</graph>\n" +
                "</graphml>");
        return builder.toString();
    }
}
