/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.entities;

import br.com.munif.framework.vicente.core.Utils;

import javax.persistence.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author munif
 */
public class EntitiesToJSON {

    private final String pastaDestino;

    public EntitiesToJSON() {
        pastaDestino = System.getProperty("user.home") + "/json";
        new File(pastaDestino).mkdir();
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

    public void geraJSON() throws IOException {
        List<Class> onlyEntities = onlyEntities(Utils.scanClasses());
        processClasses(onlyEntities);
    }

    private void processClasses(List<Class> classes) {
        for (Class c : classes) {
//            System.out.println("\"" + c.getSimpleName().toLowerCase() + "\":{");
//            System.out.println("}");
        }
    }

}
