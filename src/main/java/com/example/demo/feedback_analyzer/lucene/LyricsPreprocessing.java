package com.example.demo.feedback_analyzer.lucene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Message Preprocessing Class
 * <p>
 * Used for preprocessing the messages from our dataset
 */
public class LyricsPreprocessing {

    public static void main(String[] args) {
        String inputFilePath = "../dataset2.txt";
        String outputFilePath = "../result.txt";

        try {
            preprocessMessage(inputFilePath, outputFilePath);
            System.out.println("Preprocessing completed. Preprocessed file saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error occurred during file processing: " + e.getMessage());
        }
    }

    private static void preprocessMessage(String inputFilePath, String outputFilePath) throws IOException {
        Path inputFile = Paths.get(inputFilePath);
        Path outputFile = Paths.get(outputFilePath);

        try (BufferedReader reader = Files.newBufferedReader(inputFile);
                BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Preprocess (lowercase and remove punctuation) for each line
                String processedLine = line.toLowerCase().replaceAll("[^a-z\\s]", "");
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }
}