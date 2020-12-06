package com.bigdata.utils;

import org.springframework.core.io.ClassPathResource;

public class ChunkFile {
    private static final int COUNT = 2;

    public static void readAndChunk() {
        StringBuilder text = new StringBuilder();
        ClassPathResource inputPathResource = new ClassPathResource("data/PS_Sample_log.csv");
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
