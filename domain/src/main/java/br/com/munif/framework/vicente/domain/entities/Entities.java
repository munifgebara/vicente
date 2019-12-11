/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.entities;

import br.com.munif.framework.vicente.core.Utils;

import javax.persistence.Entity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author munif
 */
public class Entities implements IEntities {

    private final String destinationFolder;

    public Entities() {
        destinationFolder = System.getProperty("user.home") + "/diagrama";
        new File(destinationFolder).mkdir();
    }

    public static List<Class> onlyEntities(List<Class> scanClasses) {
        List<Class> toReturn = new ArrayList<>();
        for (Class c : scanClasses) {
            if (c.isAnnotationPresent(Entity.class)) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public void generateDiagram() throws IOException {
        List<Class> onlyEntities = onlyEntities(Utils.scanClasses());
        processClasses(onlyEntities);
    }

    private void processClasses(List<Class> classes) {
        Map<String, List<Class>> packages = EntitiesCommon.searchPackages(classes);
        for (String pkg : packages.keySet()) {
            List<String> associations = new ArrayList<String>();
            try {
                File pkgDot = new File(destinationFolder, pkg.replaceAll("\\.", "_") + ".dot");
                FileWriter fw = new FileWriter(pkgDot, false);
                EntitiesCommon.writeHeader(fw);
                fw.write("subgraph cluster" + pkg.replaceAll("\\.", "_") + "\n{\n");
                fw.write("label=\"" + pkg + "\";\n");
                for (Class entidade : packages.get(pkg)) {
                    associations.addAll(createClass(entidade, fw));
                }
                fw.write("}\n\n");
                for (String s : associations) {
                    fw.write(s + "\n");
                }
                fw.write("\n}\n\n");
                fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            File pkgDot = new File(destinationFolder, "allclasses.dot");
            FileWriter fw = new FileWriter(pkgDot, false);
            EntitiesCommon.writeAssociations(this, fw, packages);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        packages.put("allclasses", null);
        generateLotFileShPng(packages);
        generateLotFileShSvg(packages);
        generateLotFileBatPng(packages);
        generateLotFileBatSvg(packages);

    }


    public void generateLotFileShPng(Map<String, List<Class>> packages) {
        try {
            File script = new File(destinationFolder, "dot2png.sh");
            FileWriter fw = new FileWriter(script, false);
            script.setExecutable(true);
            fw.write("#!/bin/sh\n");
            for (String pkg : packages.keySet()) {
                fw.write("dot -T png -o " + pkg.replaceAll("\\.", "_") + ".png " + pkg.replaceAll("\\.", "_") + ".dot\n");
            }
            fw.write("\n");
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateLotFileBatPng(Map<String, List<Class>> packages) {
        try {
            File script = new File(destinationFolder, "dot2png.bat");
            FileWriter fw = new FileWriter(script, false);
            //script.setExecutable(true);
            for (String pkg : packages.keySet()) {
                fw.write("dot -T png -o " + pkg.replaceAll("\\.", "_") + ".png " + pkg.replaceAll("\\.", "_") + ".dot\n");
            }
            fw.write("\n");
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateLotFileShSvg(Map<String, List<Class>> packages) {
        try {
            File script = new File(destinationFolder, "dot2svg.sh");
            FileWriter fw = new FileWriter(script, false);
            script.setExecutable(true);
            fw.write("#!/bin/sh\n");
            for (String pkg : packages.keySet()) {
                fw.write("dot -T svg -o " + pkg.replaceAll("\\.", "_") + ".svg " + pkg.replaceAll("\\.", "_") + ".dot\n");
            }
            fw.write("\n");
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateLotFileBatSvg(Map<String, List<Class>> packages) {
        try {
            File script = new File(destinationFolder, "dot2svg.bat");
            FileWriter fw = new FileWriter(script, false);
            //script.setExecutable(true);
            for (String pkg : packages.keySet()) {
                fw.write("dot -T svg -o " + pkg.replaceAll("\\.", "_") + ".svg " + pkg.replaceAll("\\.", "_") + ".dot\n");
            }
            fw.write("\n");
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> createClass(Class entity, Writer fw) throws Exception {
        List<String> associations = new ArrayList<String>();

        if (!entity.getSuperclass().equals(Object.class)) {
            if (entity.getSuperclass().getSimpleName().equals("BaseEntity")) {
                //COLOCAR apenas uma marca
            } else {
                associations.add("edge [ arrowhead = \"empty\" headlabel = \"\" taillabel = \"\"] " + entity.getSimpleName() + " -> " + entity.getSuperclass().getSimpleName());
            }
        }

        return EntitiesCommon.getAssociationsFromEntity(entity, fw, associations);
    }


}
