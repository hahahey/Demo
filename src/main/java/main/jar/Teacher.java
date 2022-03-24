package main.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Teacher implements Person {

    public static String getClasspath() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }


    public static String getClassResources() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    public static void main(String[] args) throws IOException {


//        Properties prop = new Properties();
//        FileInputStream fileInputStream = new FileInputStream("conf/conf.properties");
//        prop.load(fileInputStream);
//        System.out.println(prop.size());


        System.out.println(getClassResources());
        System.out.println(getClasspath());

    }
}
