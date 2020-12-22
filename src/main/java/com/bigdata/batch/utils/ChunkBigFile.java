package com.bigdata.batch.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class ChunkBigFile {

    private static final int SPLIT_COUNT = 2;

    public static void startSplit() throws IOException {
        readLargeFileAndChunk();
    }

    public static void readLargeFileAndChunk() throws IOException {
        StringBuilder text = new StringBuilder();

        ClassPathResource input = new ClassPathResource("data/PS_Sample_log.csv");
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

    public static void writeSmallFile(String text, int fileCount) throws IOException {
        String fileNameStart = "PS_Sample_log_Part_";
        String newFile = fileNameStart + fileCount + ".csv";

        File file = new File(newFile);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(newFile), "UTF-8"
        ))) {
            output.write(text.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
