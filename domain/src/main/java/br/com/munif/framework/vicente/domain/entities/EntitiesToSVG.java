/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.entities;

import br.com.munif.framework.vicente.core.GraphUtil;
import br.com.munif.framework.vicente.core.Utils;

import javax.persistence.Entity;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
public class EntitiesToSVG implements IEntities {

    public static String gera() {
        try {
            String generatedDiagram = new EntitiesToSVG().generateDiagram();

            String sh = GraphUtil.sh(new String[]{"/usr/bin/dot", "-T" + "svg"}, generatedDiagram);

            return sh;
        } catch (IOException ex) {
            Logger.getLogger(EntitiesToSVG.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    private List<Class> onlyEntities(List<Class> scanClasses) {
        List<Class> toReturn = new ArrayList<>();
        for (Class c : scanClasses) {
            if (c.isAnnotationPresent(Entity.class)) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public String generateDiagram() throws IOException {
        List<Class> onlyEntities = onlyEntities(Utils.scanClasses());
        return processClasses(onlyEntities);
    }

    private String processClasses(List<Class> classes) {
        StringWriter fw = new StringWriter();
        Map<String, List<Class>> packages = EntitiesCommon.searchPackages(classes);
        try {
            EntitiesCommon.writeAssociations(this, fw, packages);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fw.toString();
    }

    @Override
    public List<String> createClass(Class entidade, Writer fw) throws Exception {
        List<String> associacoes = new ArrayList<String>();

        if (!entidade.getSuperclass().equals(Object.class)) {
            if (entidade.getSuperclass().getSimpleName().equals("BaseEntity") || entidade.getSuperclass().getSimpleName().equals("VicTemporalBaseEntity")) {
                //COLOCAR apenas uma marca
            } else {
                associacoes.add("edge [ arrowhead = \"empty\" headlabel = \"\" taillabel = \"\"] " + entidade.getSimpleName() + " -> " + entidade.getSuperclass().getSimpleName());
            }
        }

        return EntitiesCommon.getAssociationsFromEntity(entidade, fw, associacoes);
    }

}
