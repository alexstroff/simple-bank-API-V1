package com.bank.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceReader {
//    private static ResourceReader instance;

    public ResourceReader(){}

//    public ResourceReader get(){
//        if (instance == null){
//            instance = new ResourceReader();
//        }
//        return instance;
//    }
//
    public synchronized String getSQL(String fileName) {
//        InputStream is = ResourceReader.class.getResourceAsStream("/AccountSelectAll.sql");
//
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder sb = new StringBuilder();

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
