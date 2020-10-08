package com.bank.repository.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

public class ResourceReader {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ResourceReader() {
    }

    public synchronized String getSQL(String fileName) {
        String line;
        StringBuilder sb = new StringBuilder();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.warn("Something wrong, with getting SQL from file name = {}", fileName, e);
        }
        logger.debug("Got SQL = {}", sb.toString());
        return sb.toString();
    }
}
