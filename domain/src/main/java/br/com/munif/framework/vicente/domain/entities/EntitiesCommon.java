package br.com.munif.framework.vicente.domain.entities;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.util.*;

public class EntitiesCommon {
    public static Map<String, List<Class>> searchPackages(List<Class> classes) {
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

    public static List<String> getAssociationsFromEntity(Class entity, Writer fw, List<String> associations) throws IOException {
        String cor = "";
        fw.write(entity.getSimpleName() + " [" + cor + "label = \"{" + entity.getSimpleName() + "|");
        Field attrs[] = entity.getDeclaredFields();
        int i = 0;
        Set<String> excludedMethodsFromDiagram = new HashSet<>();
        excludedMethodsFromDiagram.add("equals");
        excludedMethodsFromDiagram.add("hashCode");
        excludedMethodsFromDiagram.add("toString");

        for (Field f : attrs) {
            i++;
            Class classType = f.getType();
            String classSimpleName = classType.getSimpleName();
            String attrName = f.getName();
            String naA = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
            excludedMethodsFromDiagram.add("set" + naA);
            excludedMethodsFromDiagram.add("get" + naA);

            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            if (f.getType().equals(List.class) || f.getType().equals(Set.class) || f.getType().equals(Map.class)) {
                ParameterizedType type = (ParameterizedType) f.getGenericType();
                Type[] typeArguments = type.getActualTypeArguments();
                Class genericType = (Class) typeArguments[f.getType().equals(Map.class) ? 1 : 0];

                if (f.isAnnotationPresent(ManyToMany.class)) {
                    ManyToMany mm = f.getAnnotation(ManyToMany.class);
                    if (!mm.mappedBy().isEmpty()) {
                        continue;
                    }
                    associations.add("edge [arrowhead = \"none\" headlabel = \"*\" taillabel = \"*@\"] " + entity.getSimpleName() + " -> " + genericType.getSimpleName() + " [label = \"" + attrName + "\"]");
                } else if (f.isAnnotationPresent(OneToMany.class)) {
                    OneToMany oo = f.getAnnotation(OneToMany.class);
                    if (!oo.mappedBy().isEmpty()) {
                        continue;
                    }
                    associations.add("edge [arrowhead = \"none\" headlabel = \"*\" taillabel = \"1@\"] " + entity.getSimpleName() + " -> " + genericType.getSimpleName() + " [label = \"" + attrName + "\"]");
                }

            } else if (f.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne mo = f.getAnnotation(ManyToOne.class);
                associations.add("edge [arrowhead = \"none\" headlabel = \"1\" taillabel = \"*@\"] " + entity.getSimpleName() + " -> " + classSimpleName + " [label = \"" + attrName + "\"]");
            } else if (f.isAnnotationPresent(OneToOne.class)) {
                OneToOne oo = f.getAnnotation(OneToOne.class);
                if (!oo.mappedBy().isEmpty()) {
                    continue;
                }
                associations.add("edge [arrowhead = \"none\" headlabel = \"1\" taillabel = \"1@\"] " + entity.getSimpleName() + " -> " + classSimpleName + " [label = \"" + attrName + "\"]");

            } //else
            {
                fw.write(attrName + ":" + classSimpleName + "\\l");
            }
        }

        fw.write("|");
        Method[] methods = entity.getDeclaredMethods();
        for (Method m : methods) {
            if (!excludedMethodsFromDiagram.contains(m.getName())) {
                fw.write(m.getName() + ":" + m.getReturnType().getSimpleName() + "\\l");
            }
        }
        fw.write("}\"]\n");
        return associations;
    }

    public static void writeHeader(Writer fileWriter) throws IOException {
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

    public static void writeAssociations(IEntities iEntities, Writer fw, Map<String, List<Class>> packages) throws Exception {
        Set<String> associations = new HashSet<>();
        EntitiesCommon.writeHeader(fw);
        for (String pkg : packages.keySet()) {
            fw.write("subgraph cluster" + pkg.replaceAll("\\.", "_") + "\n{\n");
            fw.write("label=\"" + pkg + "\";\n");
            for (Class entity : packages.get(pkg)) {
                associations.addAll(iEntities.createClass(entity, fw));
            }
            fw.write("\n}\n\n");
        }
        for (String s : associations) {
            fw.write(s + "\n");
        }
        fw.write("\n}\n\n");
        fw.close();
    }
}
