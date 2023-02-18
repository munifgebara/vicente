/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.GraphUtil;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.Hibernate;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author munif
 */
public class DatabaseDiagramBuilder {

    private Set<BaseEntity> generated;

    public String draw(BaseEntity entity) {
        return draw(Collections.singletonList(entity));
    }

    public <S extends BaseEntity> String draw(Iterable<S> entities) {
        generated = new HashSet<>();
        List<String> associations = new ArrayList<>();
        StringWriter fw = new StringWriter();
        try {
            writeHeader(fw);
            for (BaseEntity be : entities) {
                associations.addAll(createEntity(be, fw));
            }
            fw.write("\n\n");
            for (String s : associations) {
                fw.write(s + "\n");
            }
            fw.write("\n}\n\n");
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return GraphUtil.sh(new String[]{"/usr/bin/dot", "-T" + "svg"}, fw.toString());
    }

    private void writeHeader(Writer fileWriter) throws IOException {
        fileWriter.write("//Gerado automaticamente  munif@vicentegebara.com.br\n\n"
                + ""
                + "digraph G{\n"
                + "fontname = \"Bitstream Vera Sans\"\n"
                + "fontsize = 8\n\n"
                + "node [\n"
                + "        fontname = \"Bitstream Vera Sans\"\n"
                + "        fontsize = 8\n"
                + "        shape = \"record\"\n"
                + "]\n\n"
                + "edge [\n"
                + "        fontname = \"Bitstream Vera Sans\"\n"
                + "        fontsize = 8\n"
                + "]\n\n");
    }

    private List<String> createEntity(BaseEntity be, StringWriter fw) throws Exception {
        List<String> associations = new ArrayList<String>();
        if (generated.contains(be)) {
            return associations;
        }
        generated.add(be);
        Class entity = be.getClass();

        String cor = "";
        fw.write(be.getId() + " [" + cor + "label = \"{" + entity.getSimpleName() + " (" + be.getId() + ") |");
        Field[] attrs = entity.getDeclaredFields();

        for (Field f : attrs) {
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            f.setAccessible(true);
            String attrName = f.getName();
            if (f.getType().equals(List.class) || f.getType().equals(Set.class) || f.getType().equals(Map.class)) {
                if (f.isAnnotationPresent(ManyToMany.class) || f.isAnnotationPresent(OneToMany.class)) {
                    Hibernate.initialize(f.get(be));
                    Collection<BaseEntity> others = ((Collection) f.get(be));
                    if (others != null) {
                        for (BaseEntity other : others) {
                            StringWriter sw = new StringWriter();
                            associations.addAll(createEntity(other, sw));
                            associations.add(sw.toString());
                            associations.add("edge [arrowhead = \"none\" ] " + be.getId() + " -> " + other.getId() + " [label = \"" + attrName + "\"]");
                        }
                    }
                }

            } else if (f.isAnnotationPresent(ManyToOne.class) || f.isAnnotationPresent(OneToOne.class)) {
                BaseEntity other = ((BaseEntity) f.get(be));
                if (other != null) {
                    StringWriter sw = new StringWriter();
                    associations.addAll(createEntity(other, sw));
                    associations.add(sw.toString());
                    associations.add("edge [arrowhead = \"none\"  ] " + be.getId() + " -> " + other.getId() + " [label = \"" + attrName + "\"]");
                }
            } else {
                String vString = "" + f.get(be);
                vString = vString.substring(0, Math.min(vString.length(), 15));
                fw.write(attrName + ":" + vString + "\\l");
            }
        }
        fw.write("}\"]\n");
        return associations;
    }

}
