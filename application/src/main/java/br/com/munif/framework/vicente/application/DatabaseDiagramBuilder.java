/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.GraphUtil;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.Hibernate;
import org.reflections.Reflections;

/**
 *
 * @author munif
 */
public class DatabaseDiagramBuilder {

    private Set<BaseEntity> generated;

    public String draw(BaseEntity entity) {
        return draw(Arrays.asList(new BaseEntity[]{entity}));
    }

    public <S extends BaseEntity> String draw(Iterable<S> entities) {
        generated = new HashSet<>();
        List<String> associassoes = new ArrayList<>();
        StringWriter fw = new StringWriter();
        try {
            escreveCabecalho(fw);
            for (BaseEntity be : entities) {
                associassoes.addAll(criaEntidade(be, fw));
            }
            fw.write("\n\n");
            for (String s : associassoes) {
                fw.write(s + "\n");
            }
            fw.write("\n}\n\n");
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return GraphUtil.sh(new String[]{"/usr/bin/dot", "-T" + "svg"}, fw.toString());
    }

    private void escreveCabecalho(StringWriter fileWriter) throws IOException {
        fileWriter.write("//Gerado automaticamente  munif@munifgebara.com.br\n\n"
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

    private List<String> criaEntidade(BaseEntity be, StringWriter fw) throws Exception {
        List<String> associacoes = new ArrayList<String>();
        if (generated.contains(be)) {
            return associacoes;
        }
        generated.add(be);
        Class entidade = be.getClass();

        String cor = "";
        fw.write(be.getId() + " [" + cor + "label = \"{" + entidade.getSimpleName() + " (" + be.getId() + ") |");
        Field atributos[] = entidade.getDeclaredFields();
        int i = 0;
        Set<String> metodosExcluidosDoDiagrama = new HashSet<>();
        metodosExcluidosDoDiagrama.add("equals");
        metodosExcluidosDoDiagrama.add("hashCode");
        metodosExcluidosDoDiagrama.add("toString");

        for (Field f : atributos) {
            i++;
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            Class tipo = f.getType();
            f.setAccessible(true);
            String nomeAtributo = f.getName();
            if (f.getType().equals(List.class) || f.getType().equals(Set.class) || f.getType().equals(Map.class)) {
                ParameterizedType type = (ParameterizedType) f.getGenericType();
                Type[] typeArguments = type.getActualTypeArguments();
                Class tipoGenerico = (Class) typeArguments[f.getType().equals(Map.class) ? 1 : 0];
                if (f.isAnnotationPresent(ManyToMany.class) || f.isAnnotationPresent(OneToMany.class)) {
                    Hibernate.initialize(f.get(be));
                    Collection<BaseEntity> others = ((Collection) f.get(be));
                    if (others != null) {
                        for (BaseEntity other : others) {
                            StringWriter sw = new StringWriter();
                            associacoes.addAll(criaEntidade(other, sw));
                            associacoes.add(sw.toString());
                            associacoes.add("edge [arrowhead = \"none\" ] " + be.getId() + " -> " + other.getId() + " [label = \"" + nomeAtributo + "\"]");
                        }
                    }
                }

            } else if (f.isAnnotationPresent(ManyToOne.class) || f.isAnnotationPresent(OneToOne.class)) {
                BaseEntity other = ((BaseEntity) f.get(be));
                if (other != null) {
                    StringWriter sw = new StringWriter();
                    associacoes.addAll(criaEntidade(other, sw));
                    associacoes.add(sw.toString());
                    associacoes.add("edge [arrowhead = \"none\"  ] " + be.getId() + " -> " + other.getId() + " [label = \"" + nomeAtributo + "\"]");
                }
            } else {
                String vString = "" + f.get(be);
                vString = vString.substring(0, vString.length() > 15 ? 15 : vString.length());
                fw.write(nomeAtributo + ":" + vString + "\\l");
            }
        }

//        fw.write("|");
//        Method metodos[] = entidade.getDeclaredMethods();
//        for (Method m : metodos) {
//            if (!metodosExcluidosDoDiagrama.contains(m.getName())) {
//                fw.write(m.getName() + ":" + m.getReturnType().getSimpleName() + "\\l");
//            }
//        }
        fw.write("}\"]\n");
        return associacoes;
    }

}
