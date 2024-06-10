package com.example.demo.metric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageReader {

    public static List<String> readMessage(String filePath) {
        List<String> messages = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            // Read the file line by line
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Check if the next line is also empty
                    reader.mark(1000); // Marking the current position
                    String nextLine = reader.readLine();
                    if (nextLine != null && nextLine.trim().isEmpty()) {
                        messages.add(messageBuilder.toString().trim());
                        messageBuilder = new StringBuilder();
                    } else {
                        messageBuilder.append(line).append("\n");
                    }
                    if (nextLine != null) {
                        reader.reset(); // Reset back to the marked position
                    }
                } else {
                    messageBuilder.append(line).append("\n");
                }
            }

            if (!messages.toString().trim().isEmpty()) {
                messages.add(messages.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the reader
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return messages;
    }
}