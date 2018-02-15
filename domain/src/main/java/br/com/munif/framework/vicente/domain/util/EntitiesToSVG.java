/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.util;

import br.com.munif.framework.vicente.core.GraphUtil;
import br.com.munif.framework.vicente.core.Utils;
import java.io.File;
import java.io.StringWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author munif
 */
public class EntitiesToSVG {

    public static String gera() {
        try {
            String geraDiagrama = new EntitiesToSVG().geraDiagrama();

            String sh = GraphUtil.sh(new String[]{"/usr/bin/dot", "-T" + "svg"}, geraDiagrama);

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

    public String geraDiagrama() throws IOException {
        List<Class> onlyEntities = onlyEntities(Utils.scanClasses());
        return processClasses(onlyEntities);
    }

    private String processClasses(List<Class> classes) {
        StringWriter fw = new StringWriter();
        Map<String, List<Class>> pacotes = pesquisaPacotes(classes);
        try {
            Set<String> associcaos = new HashSet<>();

            escreveCabecalho(fw);
            for (String pacote : pacotes.keySet()) {
                fw.write("subgraph cluster" + pacote.replaceAll("\\.", "_") + "\n{\n");
                fw.write("label=\"" + pacote + "\";\n");
                for (Class entidade : pacotes.get(pacote)) {
                    associcaos.addAll(criaClasse(entidade, fw));
                }
                fw.write("\n}\n\n");
            }
            for (String s : associcaos) {
                fw.write(s + "\n");
            }
            fw.write("\n}\n\n");
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fw.toString();
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

    private List<String> criaClasse(Class entidade, StringWriter fw) throws Exception {
        List<String> associacoes = new ArrayList<String>();

        if (!entidade.getSuperclass().equals(Object.class)) {
            if (entidade.getSuperclass().getSimpleName().equals("BaseEntity")) {
                //COLOCAR apenas uma marca
            } else {
                associacoes.add("edge [ arrowhead = \"empty\" headlabel = \"\" taillabel = \"\"] " + entidade.getSimpleName() + " -> " + entidade.getSuperclass().getSimpleName());
            }
        }

        String cor = "";
        fw.write(entidade.getSimpleName() + " [" + cor + "label = \"{" + entidade.getSimpleName() + "|");
        Field atributos[] = entidade.getDeclaredFields();
        int i = 0;
        Set<String> metodosExcluidosDoDiagrama = new HashSet<>();
        metodosExcluidosDoDiagrama.add("equals");
        metodosExcluidosDoDiagrama.add("hashCode");
        metodosExcluidosDoDiagrama.add("toString");

        for (Field f : atributos) {
            i++;
            Class tipoAtributo = f.getType();
            String tipo = tipoAtributo.getSimpleName();
            String nomeAtributo = f.getName();
            String naA = nomeAtributo.substring(0, 1).toUpperCase() + nomeAtributo.substring(1);
            metodosExcluidosDoDiagrama.add("set" + naA);
            metodosExcluidosDoDiagrama.add("get" + naA);

            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            if (f.getType().equals(List.class) || f.getType().equals(Set.class) || f.getType().equals(Map.class)) {
                ParameterizedType type = (ParameterizedType) f.getGenericType();
                Type[] typeArguments = type.getActualTypeArguments();
                Class tipoGenerico = (Class) typeArguments[f.getType().equals(Map.class) ? 1 : 0];

                if (f.isAnnotationPresent(ManyToMany.class)) {
                    ManyToMany mm = f.getAnnotation(ManyToMany.class);
                    if (!mm.mappedBy().isEmpty()) {
                        continue;
                    }
                    associacoes.add("edge [arrowhead = \"none\" headlabel = \"*\" taillabel = \"*@\"] " + entidade.getSimpleName() + " -> " + tipoGenerico.getSimpleName() + " [label = \"" + nomeAtributo + "\"]");
                } else if (f.isAnnotationPresent(OneToMany.class)) {
                    OneToMany oo = f.getAnnotation(OneToMany.class);
                    if (!oo.mappedBy().isEmpty()) {
                        continue;
                    }
                    associacoes.add("edge [arrowhead = \"none\" headlabel = \"*\" taillabel = \"1@\"] " + entidade.getSimpleName() + " -> " + tipoGenerico.getSimpleName() + " [label = \"" + nomeAtributo + "\"]");
                }

            } else if (f.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne mo = f.getAnnotation(ManyToOne.class);
                associacoes.add("edge [arrowhead = \"none\" headlabel = \"1\" taillabel = \"*@\"] " + entidade.getSimpleName() + " -> " + tipo + " [label = \"" + nomeAtributo + "\"]");
            } else if (f.isAnnotationPresent(OneToOne.class)) {
                OneToOne oo = f.getAnnotation(OneToOne.class);
                if (!oo.mappedBy().isEmpty()) {
                    continue;
                }
                associacoes.add("edge [arrowhead = \"none\" headlabel = \"1\" taillabel = \"1@\"] " + entidade.getSimpleName() + " -> " + tipo + " [label = \"" + nomeAtributo + "\"]");

            } //else
            {
                fw.write(nomeAtributo + ":" + tipo + "\\l");
            }
        }

        fw.write("|");
        Method metodos[] = entidade.getDeclaredMethods();
        for (Method m : metodos) {
            if (!metodosExcluidosDoDiagrama.contains(m.getName())) {
                fw.write(m.getName() + ":" + m.getReturnType().getSimpleName() + "\\l");
            }
        }
        fw.write("}\"]\n");
        return associacoes;
    }

    public Map<String, List<Class>> pesquisaPacotes(List<Class> classes) {
        Map<String, List<Class>> pacotes = new HashMap<>();
        for (Class classe : classes) {
            String nomeClasse = classe.getName();
            String pacote = nomeClasse.substring(0, nomeClasse.lastIndexOf('.'));
            List<Class> lista = pacotes.get(pacote);
            if (lista == null) {
                lista = new ArrayList<>();
                pacotes.put(pacote, lista);
            }
            lista.add(classe);
        }
        return pacotes;
    }

}
