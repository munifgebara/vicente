package br.com.munif.framework.vicente.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import javax.persistence.Entity;

public class EntityDiagramBuilder {

    public static void main(String args[]) throws IOException {
        Reflections reflections = new Reflections();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);    
        System.out.println("--->"+annotated);
    }
    
    public List<Class> searchForEntities(File folder){
        List<Class> toReturn=new ArrayList<Class>();
        
        return toReturn;
    }

}
