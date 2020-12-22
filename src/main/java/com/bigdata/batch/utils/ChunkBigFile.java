package com.bigdata.batch.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChunkBigFile {

    private static final int SPLIT_COUNT = 2;

    public static void startSplit() throws IOException {
        readLargeFileAndChunk();
    }

    public static void readLargeFileAndChunk() throws IOException {
        StringBuilder text = new StringBuilder();

        ClassPathResource input = new ClassPathResource("data/PS_20174392719_1491204439457_log");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                input.getInputStream()))) {
            int count = 0;
            int fileCount = 0;
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                text.append(row).append("\n");
                count++;

                if (count > SPLIT_COUNT) {
                    fileCount++;
                    writeSmallFile(text.toString(), fileCount);
                    count = 0;
                    text.setLength(0);
                }
            }
            if (text.length() != 0) {
                fileCount++;
                writeSmallFile(text.toString(), fileCount);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeSmallFile(String text, int FileCount) throws IOException {
        String newFileName = "PS_20174392719_1491204439457_log.csv";
    }
}
