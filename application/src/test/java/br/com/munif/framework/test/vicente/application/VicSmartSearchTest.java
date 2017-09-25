/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.domain.model.smartsearch.Categoria;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.Cliente;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.GrupoClientes;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.ItemPedido;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.Pedido;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.Produto;
import br.com.munif.framework.vicente.application.search.VicAutoSeed;
import br.com.munif.framework.vicente.application.search.VicSmartSearch;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class VicSmartSearchTest {

    @Autowired
    protected VicSmartSearch vss;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private GrupoClientesRepository grupoClientesRepository;

    @Before
    @Transactional
    public void setUp() {
        try {
            VicThreadScope.ui.set("U1");
            VicThreadScope.gi.set("G1");
            loadSeedCategoria();
            loadSeedProduto();
            loadSeedGrupoClientes();
            loadSeedCliente();
            loadSeedPedido();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(VicSmartSearchTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Transactional
    public void emNotNull() {
        assertNotNull(categoriaRepository);
    }

    @Test
    @Transactional
    public void teste0() {
        List result = vss.normalSearch();
        assertTrue(!result.isEmpty());
    }

    @Test
    @Transactional
    public void teste1() {
        List<Map<String, Object>> smartSearch = vss.smartSearch("Cliente", "Categoria", "", "",10);
        //"select cliente.nome as nomCliente,categoria.nome as cat,count(categoria.nome) as quantidade",
        //"where categoria.nome='egg'  group by cliente.nome,categoria.nome order by categoria.nome");
        for (Object obj : smartSearch) {
            System.out.println(obj.toString());
        }
        assertTrue(!smartSearch.isEmpty());
    }

    @Test
    @Transactional
    public void teste2() {
        List<Map<String, Object>> smartSearch = vss.smartSearch("Cliente", "Categoria", "select cliente.nome as nomCliente,categoria.nome as cat,count(categoria.nome) as quantidade", "where categoria.nome='egg'  group by cliente.nome,categoria.nome order by categoria.nome");
        for (Object obj : smartSearch) {
            System.out.println(obj.toString());
        }
        assertTrue(!smartSearch.isEmpty());
    }


    @Transactional
    public void loadSeedCategoria() throws IOException {
        List<Categoria> inteligentInstances = VicAutoSeed.getInteligentInstances(new Categoria(), 10);
        for (Categoria cat : inteligentInstances) {
            categoriaRepository.saveAndFlush(cat);
        }
    }

    @Transactional
    public void loadSeedProduto() throws IOException {
        Produto exProduto = new Produto();
        exProduto.setCategoria(new Categoria());
        List<Produto> inteligentInstances = VicAutoSeed.getInteligentInstances(exProduto, 100);
        List<Categoria> values = categoriaRepository.findAll();
        int i = 0;
        for (Produto p : inteligentInstances) {
            p.setCategoria(values.get((i++) % values.size()));
            produtoRepository.save(p);
        }

    }

    @Transactional
    public void loadSeedPedido() throws IOException {

        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        for (int i = 0; i < 100; i++) {

            Pedido p = new Pedido();
            p.setCliente(clientes.get(VicAutoSeed.getRandomInteger(0, clientes.size())));
            p.setItens(new ArrayList<>());
            Integer nProdutos = VicAutoSeed.getRandomInteger(5, 10);
            for (int j = 0; j < nProdutos; j++) {
                ItemPedido ip = new ItemPedido();
                ip.setPedido(p);
                ip.setProduto(produtos.get(VicAutoSeed.getRandomInteger(0, produtos.size())));
                VicAutoSeed.randomFill(ip);
                p.getItens().add(itemPedidoRepository.save(ip));
            }
            pedidoRepository.save(p);
        }
    }

    @Transactional
    public void loadSeedCliente() throws IOException {
        List<GrupoClientes> grupoClientes = grupoClientesRepository.findAll();
        List<List> subLists = VicAutoSeed.subLists(1, 2, grupoClientes);
        Cliente example = new Cliente();
        example.setGrupoClientes(Collections.EMPTY_LIST);
        List<Cliente> clientes = VicAutoSeed.getInteligentInstances(example, 20);
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            cliente.setGrupoClientes(subLists.get(i % subLists.size()));
            clienteRepository.save(cliente);
        }

    }

    @Transactional
    public void loadSeedGrupoClientes() throws IOException {

        grupoClientesRepository.save(new GrupoClientes("TI"));
        grupoClientesRepository.save(new GrupoClientes("ELETRO"));
        grupoClientesRepository.save(new GrupoClientes("AUTO"));

    }

}