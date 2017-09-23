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
import javax.persistence.Query;
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

    private Map<String, List<Associassoes>> entidades;
    private Map<String, Node> nodes;

    public VicSmartSearch() {

    }

    public EntityManager getEm() {
        return em;
    }
    
    public void normalSearch(){
        System.out.println("-----");
        Query createQuery = em.createQuery("from Cliente c");
        List resultList = createQuery.getResultList();
        for (Object o:resultList){
            System.out.println(o.getClass().getSimpleName()+" "+o);
        }
        System.out.println("-----");
        
    }

    public String smartSearch(String e1, String e2, String w) {
        init();
        initDijkstra();
         Graph graph = new Graph();

        for (Node n : nodes.values()) {
         graph.addNode(n);
        }
        graph = Dijkstra.calculateShortestPathFromSource(graph, nodes.get(e1));
        List<Node> shortestPath=null;
        for (Node n:graph.getNodes()){
            if (n.getName().equals(e2)){
                shortestPath = n.getShortestPath();
            }
        }
        if (shortestPath!=null){
            List<String> caminho=new ArrayList<>();
            for (Node n:shortestPath){
                caminho.add(n.getName());
            }
            caminho.add(e2);
            System.out.println(caminho);
        }
        System.out.println("--->"+shortestPath);
        return "select 1=1";
    }

    private int peso(String multiplicidade) {
        switch (multiplicidade) {
            case "MANY_TO_MANY":
                return 5;
            case "ONE_TO_MANY":
                return 4;
            case "MANY_TO_ONE":
                return 2;
            case "ONE_TO_ONE":
                return 1;
        }
        return 999999999;
    }

    public void initDijkstra() {
        nodes = new HashMap<>();
        for (String e : entidades.keySet()) {
            nodes.put(e, new Node(e));
        }
        for (String e : entidades.keySet()) {
            for (Associassoes a : entidades.get(e)) {
                nodes.get(a.getEntidadeOrigem()).addDestination(nodes.get(a.getEntidadeDestino()), peso(a.getMultiplicidade()));
            }
        }

    }

    public void init() {
        if (entidades != null) {
            return;
        }
        entidades = new HashMap<>();
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
            List<Associassoes> associacoesDaEntidade = new ArrayList<>();
            entidades.put(e.getName(), associacoesDaEntidade);
            Set<SingularAttribute> singularAtributes = e.getSingularAttributes();
            for (SingularAttribute a : singularAtributes) {
                if (a.isAssociation()) {
                    associacoesDaEntidade.add(new Associassoes(a.getName(), e.getName(), a.getJavaType().getSimpleName(), a.getPersistentAttributeType().toString()));
                }
            }

            Set<PluralAttribute> pluralAttributes = e.getDeclaredPluralAttributes();

            for (PluralAttribute a : pluralAttributes) {
                if (a.isAssociation()) {
                    associacoesDaEntidade.add(new Associassoes(a.getName(), e.getName(), a.getElementType().getJavaType().getSimpleName(), a.getPersistentAttributeType().toString()));
                }
            }
        }
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


        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeD);
        System.out.println("--->" + graph.toString());

    }

}
