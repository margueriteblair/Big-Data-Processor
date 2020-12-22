package com.bigdata.batch.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChunkBigFile {

    private static final int SPLIT_COUNT = 2;

    public static void startSplit() {
        readLargeFileAndChunk();
    }

    public static void readLargeFileAndChunk() throws IOException {
        StringBuilder text = new StringBuilder();

        ClassPathResource input = new ClassPathResource("data/PS_20174392719_1491204439457_log");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                input.getInputStream()))) {
            int count = 0;
            int fileCount = 0;
            String line = in.readLine();

        }
    }
}
