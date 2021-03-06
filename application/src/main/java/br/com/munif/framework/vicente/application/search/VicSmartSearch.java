package br.com.munif.framework.vicente.application.search;

import br.com.munif.framework.vicente.application.search.dijkstra.Dijkstra;
import br.com.munif.framework.vicente.application.search.dijkstra.Graph;
import br.com.munif.framework.vicente.application.search.dijkstra.Node;
import br.com.munif.framework.vicente.domain.VicRevisionEntity;
import org.hibernate.query.internal.QueryImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;

@Service
public class VicSmartSearch {

    @PersistenceContext
    protected EntityManager em;

    private Map<String, List<Association>> entidades;
    private Map<String, Node> nodes;

    public VicSmartSearch() {

    }

    public EntityManager getEm() {
        return em;
    }

    public List<Map<String, Object>> smartSearch(String e1, String e2, String antes, String depois, int maxResults) {
        init();
        initDijkstra();
        Graph graph = new Graph();

        for (Node n : nodes.values()) {
            graph.addNode(n);
        }
        graph = Dijkstra.calculateShortestPathFromSource(graph, nodes.get(e1));
        List<Node> shortestPath = null;
        for (Node n : graph.getNodes()) {
            if (n.getName().equals(e2)) {
                shortestPath = n.getShortestPath();
            }
        }
        if (shortestPath != null) {
            List<String> caminho = new ArrayList<>();
            for (Node n : shortestPath) {
                caminho.add(n.getName());
            }
            caminho.add(e2);

            String hql = antes + " FROM " + e1;

            for (int i = 0; i < caminho.size() - 1; i++) {
                String nomeEntidade = caminho.get(i);
                String proximaEntidade = caminho.get(i + 1);
                String alisEntidade = nomeEntidade.toLowerCase();
                List<Association> associacoes = entidades.get(nomeEntidade);
                Association a = associacoes.stream().filter(p -> p.getDestinationEntity().equals(proximaEntidade)).findFirst().orElse(null);
                hql += " as " + alisEntidade + " inner join " + alisEntidade + "." + a.getAttribute();

            }
            hql += " as " + e2.toLowerCase() + " " + depois;
            QueryImpl query = (QueryImpl) em.createQuery(hql);
            query.setResultTransformer(new VicResultTransformer());
            query.setMaxResults(maxResults);
            List resultList = query.getResultList();
            return resultList;
        }
        return Collections.EMPTY_LIST;
    }
    //FROM Cliente as cliente inner join cliente.pedidos as pedido inner join pedido.itens as itempedido inner join itempedido.produto as produto inner join produto.categoria
    //from Cliente as cliente inner join cliente.pedidos as pedido inner join pedido.itens as itempedido inner join itempedido.produto as produto inner join produto.categoria as categoria "
//                + " where categoria.nome='egg'  group by cliente.nome,categoria.nome order by categoria.nome");

    public List<Map<String, Object>> smartSearch(String e1, String e2, String antes, String depois) {
        return smartSearch(e1, e2, antes, depois, Integer.MAX_VALUE);
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
            for (Association a : entidades.get(e)) {
                nodes.get(a.getSourceEntity()).addDestination(nodes.get(a.getDestinationEntity()), peso(a.getMultiplicity()));
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
            List<Association> associacoesDaEntidade = new ArrayList<>();
            entidades.put(e.getName(), associacoesDaEntidade);
            Set<SingularAttribute> singularAtributes = e.getSingularAttributes();
            for (SingularAttribute a : singularAtributes) {
                if (a.isAssociation()) {
                    associacoesDaEntidade.add(new Association(a.getName(), e.getName(), a.getJavaType().getSimpleName(), a.getPersistentAttributeType().toString()));
                }
            }

            Set<PluralAttribute> pluralAttributes = e.getDeclaredPluralAttributes();

            for (PluralAttribute a : pluralAttributes) {
                if (a.isAssociation()) {
                    associacoesDaEntidade.add(new Association(a.getName(), e.getName(), a.getElementType().getJavaType().getSimpleName(), a.getPersistentAttributeType().toString()));
                }
            }
        }
    }
}
