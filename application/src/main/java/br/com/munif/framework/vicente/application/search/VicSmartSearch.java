package br.com.munif.framework.vicente.application.search;

import br.com.munif.framework.vicente.application.search.dijkstra.Dijkstra;
import br.com.munif.framework.vicente.application.search.dijkstra.Graph;
import br.com.munif.framework.vicente.application.search.dijkstra.Node;
import br.com.munif.framework.vicente.domain.VicRevisionEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import org.hibernate.metamodel.internal.AbstractAttribute;
import org.springframework.stereotype.Service;

@Service
public class VicSmartSearch {

    @PersistenceContext
    protected EntityManager em;

    private Map<String,List<Associassoes>> entidades;

    public VicSmartSearch() {
        entidades = new HashMap<>();
    }

    public EntityManager getEm() {
        return em;
    }

    public String smartSearch(String e1, String e2, String w) {

        Metamodel metamodel = em.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        for (EntityType e : entities) {
            Class javaType = e.getJavaType();
            if (javaType == null) {
                continue;
            }
            if (javaType.equals(VicRevisionEntity.class)) {
                continue;
            }
            List<Associassoes> associacoesDaEntidade=new ArrayList<>();
            entidades.put(e.getName(),associacoesDaEntidade);
            System.out.println("--->" + e.getName() + " " + e.getPersistenceType().name() + " " + e.getJavaType());
            Set<SingularAttribute> singularAtributes = e.getSingularAttributes();
            for (SingularAttribute a : singularAtributes) {
                if (a.isAssociation()) {
                    //System.out.println("-------->" + a.getName() + " " + a.getPersistentAttributeType().toString()+" "+a.getJavaType().getSimpleName());
                    associacoesDaEntidade.add(new Associassoes(a.getName(),e.getName(),a.getJavaType().getSimpleName(),a.getPersistentAttributeType().toString()));
                }
            }

            Set<PluralAttribute> pluralAttributes = e.getDeclaredPluralAttributes();

            for (PluralAttribute a : pluralAttributes) {
                if (a.isAssociation()){
//                    System.out.println("-------->" + a.getName() + " " + a.getPersistentAttributeType().toString()+" "+a.getJavaType().getSimpleName()+" "+a.getJavaMember().getName()+" "+a.getElementType().getJavaType().getSimpleName());
                    associacoesDaEntidade.add(new Associassoes(a.getName(),e.getName(),a.getElementType().getJavaType().getSimpleName(),a.getPersistentAttributeType().toString()));
                }

            }
        }
        for (String e:entidades.keySet()){
            System.out.println("e"+e);
            for (Associassoes a:entidades.get(e)){
                System.out.println(""+a);
            }
        }
        return "select * from pessoa";
    }

    public static void main(String args[]) {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        //graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);
        System.out.println("--->" + graph.toString());
        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeD);
        System.out.println("--->" + graph.toString());

    }

}
