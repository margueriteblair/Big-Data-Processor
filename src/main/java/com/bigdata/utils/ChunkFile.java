package com.bigdata.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

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

    public static void writeSmallFile(String text, int fileCount) throws IOException {
        String fileNameSuffix = "PS_Sample_log_Part_";
        String newFileName = fileNameSuffix + fileCount + ".csv";
        File file = new File(newFileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(newFileName), "UTF-8"))) {
            out.write(text.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
