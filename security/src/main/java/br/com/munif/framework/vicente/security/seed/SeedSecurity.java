/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.seed;

import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import br.com.munif.framework.vicente.security.domain.Grupo;
import br.com.munif.framework.vicente.security.domain.Organizacao;
import br.com.munif.framework.vicente.security.domain.Usuario;
import br.com.munif.framework.vicente.security.repository.GrupoRepository;
import br.com.munif.framework.vicente.security.repository.OrganizacaoRepository;
import br.com.munif.framework.vicente.security.repository.UsuarioRepository;
import java.util.HashSet;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author munif
 */
@Component
public class SeedSecurity {

    private final Logger log = Logger.getLogger("SeedSecurity");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public void seedSecurity() {
        if (usuarioRepository.count() > 0) {
            return;
        }

        VicThreadScope.gi.set("SEED");
        VicThreadScope.ui.set("SEED");
        VicThreadScope.oi.set("SEED.");
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL + RightsHelper.GROUP_READ_UPDATE + RightsHelper.OTHER_READ);

        log.info("Inserting Security Data");

        Grupo g0 = new Grupo();
        g0.setCodigo("SEED");
        g0.setNome("SEED");
        grupoRepository.save(g0);

        Grupo g1 = new Grupo();
        g1.setCodigo("G1");
        g1.setNome("Grupo 1");
        grupoRepository.save(g1);
        Grupo g2 = new Grupo();
        g2.setCodigo("G2");
        g2.setNome("Grupo 2");
        grupoRepository.save(g2);

        Organizacao o1 = new Organizacao();
        o1.setCodigo("empresa");
        o1.setNome("Empresa");
        organizacaoRepository.save(o1);

        Organizacao o2 = new Organizacao();
        o2.setCodigo("departamento");
        o2.setNome("Departamento");
        o2.setSuperior(o1);
        organizacaoRepository.save(o2);

        Usuario admin = new Usuario("admin@munif.com.br", "qwe123");
        admin.setGrupos(new HashSet<>());
        admin.getGrupos().add(g0);
        admin.getGrupos().add(g2);
        admin.setOrganizacao(o1);
        usuarioRepository.save(admin);

        Usuario usuario = new Usuario("munif@munif.com.br", "qwe123");
        usuario.setGrupos(new HashSet<>());
        usuario.getGrupos().add(g1);
        usuario.setOrganizacao(o2);
        usuarioRepository.save(usuario);
    }

}
