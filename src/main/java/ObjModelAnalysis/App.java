package ObjModelAnalysis;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {
    // java path to package for scanning
    public static final String PATH = "MoviePortal";

    public static void main(String[] args) {
        System.out.println("\t\t-- == ===  Found classes  === == —-");
        List<Class<?>> classList = find();
        for (Class<?> aClass : classList) {
            System.out.println(aClass.getName());
        }

        System.out.println("\n\n\t\t-- == ===  Class fields  === == —-");
        for (Class<?> aClass : classList) {
            System.out.println("\n" + aClass.getName());
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                System.out.println("\t" + declaredField.getName());
            }
        }


    }

    private static List<Class<?>> find() {
        String scannedPath = App.PATH.replace(".", "/");
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + App.PATH);
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, App.PATH));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String path) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = path + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
