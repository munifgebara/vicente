package br.com.munif.framework.vicente.core;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceUtil {
    public static Stream<Path> getPathRecursivelyFromJar(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);

        if (dirURL == null) {
            /*
             * In case of a jar file, we can't actually find a directory.
             * Have to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            List<Path> result = new ArrayList<>(); //avoid duplicates in case it is a subdirectory
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.contains(path)) { //filter according to
                    result.add(Paths.get("file:" + jarPath + "!/" + name.replace("classes", "classes!")));
                }
            }
            return result.stream().sorted();
        } else {
            return Files.walk(Paths.get(dirURL.toURI()));
        }
    }

    public static void exportSubResourcesDir(Class clazz, String subResourcesDir, String exporDir, String fileSeparator) throws URISyntaxException, IOException {
        Stream<Path> pathRecursivelyFromJar = ResourceUtil.getPathRecursivelyFromJar(clazz, subResourcesDir);
        File vicFiles = new File(exporDir);
        List<Path> collect = pathRecursivelyFromJar.collect(Collectors.toList());

        for (Path path : collect) {
            boolean isFile = !path.toFile().isDirectory();
            if (!path.endsWith(subResourcesDir) || isFile) {
                String fileBase = Thread.currentThread().getContextClassLoader().getResource("").getFile() + subResourcesDir + (isFile ? "" : fileSeparator);
                File fileToExport = path.toFile();
                fileBase = fileToExport.toString().replace(fileBase, "");
                File exportedFile = new File(vicFiles + fileSeparator + fileBase);
                if (!exportedFile.exists()) {
                    if (isFile(exportedFile.toURI().toString())) {
                        InputStream resourceAsStream = Thread.currentThread()
                                .getContextClassLoader().getResourceAsStream(subResourcesDir + fileBase);
                        FileUtils.copyInputStreamToFile(resourceAsStream, exportedFile);
                    } else {
                        exportedFile.mkdir();
                    }
                }
            } else {
                vicFiles.mkdir();
            }
        }
    }

    private static boolean isFile(String obj) {
        return obj.lastIndexOf(".") > 0;
    }
}
