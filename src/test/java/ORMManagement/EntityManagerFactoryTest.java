/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package ORMManagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class EntityManagerFactoryTest {

    @Test
    void is() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/connection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        EntityManagerFactory factory = new EntityManagerFactory(properties);
        Assertions.assertTrue(factory.isDbValid());
    }
}