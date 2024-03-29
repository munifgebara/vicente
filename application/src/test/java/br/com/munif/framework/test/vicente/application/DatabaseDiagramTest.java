package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.application.repository.*;
import br.com.munif.framework.test.vicente.domain.model.smartsearch.*;
import br.com.munif.framework.vicente.application.DatabaseDiagramBuilder;
import br.com.munif.framework.vicente.application.search.VicAutoSeed;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2SpringConfig.class})
public class DatabaseDiagramTest {

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
    public void generateDiagram() {
//        try {
//            List<BaseEntity> umMonte = new ArrayList<>();
//            umMonte.addAll(categoriaRepository.findAllNoTenancy());
//            umMonte.addAll(produtoRepository.findAllNoTenancy());
//            umMonte.addAll(itemPedidoRepository.findAllNoTenancy());
//            umMonte.addAll(pedidoRepository.findAllNoTenancy());
//            umMonte.addAll(clienteRepository.findAllNoTenancy());
//            umMonte.addAll(grupoClientesRepository.findAllNoTenancy());
//            for (BaseEntity be : umMonte) {
//                FileUtils.writeStringToFile(new File("/home/munif/diagramas/" + be.getId() + ".svg"), new DatabaseDiagramBuilder().draw(be), Charset.defaultCharset());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        String draw = new DatabaseDiagramBuilder().draw(pedidoRepository.findAllNoTenancy().get(0));
        Assert.assertNotNull(draw);

    }

    @Transactional
    public void loadSeedCategoria() throws IOException {
        BaseEntity.useSimpleId = true;
        if (categoriaRepository.count() > 0) {
            return;
        }
        List<Categoria> inteligentInstances = VicAutoSeed.getInteligentInstances(new Categoria(), 4);
        for (Categoria cat : inteligentInstances) {
            categoriaRepository.saveAndFlush(cat);
        }
    }

    @Transactional
    public void loadSeedProduto() throws IOException {
        BaseEntity.useSimpleId = true;
        if (produtoRepository.count() > 0) {
            return;
        }
        Produto exProduto = new Produto();
        exProduto.setCategoria(new Categoria());
        List<Produto> inteligentInstances = VicAutoSeed.getInteligentInstances(exProduto, 10);
        List<Categoria> values = categoriaRepository.findAll();
        int i = 0;
        for (Produto p : inteligentInstances) {
            p.setCategoria(values.get((i++) % values.size()));
            produtoRepository.save(p);
        }
    }

    @Transactional
    public void loadSeedPedido() throws IOException {
        BaseEntity.useSimpleId = true;
        if (pedidoRepository.count() > 0) {
            return;
        }

        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        for (int i = 0; i < 3; i++) {
            Pedido p = new Pedido();
            p.setCliente(clientes.get(VicAutoSeed.getRandomInteger(0, clientes.size())));
            p.setItens(new ArrayList<>());
            Integer nProdutos = VicAutoSeed.getRandomInteger(1, 3);
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
        BaseEntity.useSimpleId = true;
        if (clienteRepository.count() > 0) {
            return;
        }
        List<GrupoClientes> grupoClientes = grupoClientesRepository.findAll();
        List<List> subLists = VicAutoSeed.subLists(1, 2, grupoClientes);
        Cliente example = new Cliente();
        example.setGrupoClientes(Collections.EMPTY_LIST);
        List<Cliente> clientes = VicAutoSeed.getInteligentInstances(example, 4);
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            cliente.setGrupoClientes(subLists.get(i % subLists.size()));
            clienteRepository.save(cliente);
        }

    }

    @Transactional
    public void loadSeedGrupoClientes() throws IOException {
        if (grupoClientesRepository.count() > 0) {
            return;
        }
        grupoClientesRepository.save(new GrupoClientes("TI"));
        grupoClientesRepository.save(new GrupoClientes("ELETRO"));
        grupoClientesRepository.save(new GrupoClientes("AUTO"));

    }

}
