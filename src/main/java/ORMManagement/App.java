/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import java.io.FileReader;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/connection.properties"));
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        System.out.println(factory.isDbValid());
    }
}
