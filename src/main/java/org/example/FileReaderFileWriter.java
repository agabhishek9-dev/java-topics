package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileReaderFileWriter Demo
 * -------------------------
 * This program shows how to:
 * 1. Write text into a file using FileWriter
 * 2. Read the same text from the file using FileReader
 * 3. Handle file operations safely using try-catch-finally
 *
 * Interview points:
 * - FileWriter is used for character-based output.
 * - FileReader is used for character-based input.
 * - Both work with text files, not binary files.
 * - read() returns an int, and -1 means end of file.
 */
public class FileReaderFileWriter {

    public static void main(String[] args) {
        FileWriter fileWriter = null;
        FileReader fileReader = null;

        try {
            // ----------------------------------------
            // STEP 1: Write text into a file
            // ----------------------------------------
            // "test.txt" will be created if it does not exist.
            // If it already exists, content may be overwritten.
            fileWriter = new FileWriter("test.txt");

            // Write a sample line into the file
            fileWriter.write("Hello Abhishek, how are you !! 123");

            // Important: flush() pushes data from memory to file immediately
            fileWriter.flush();

            System.out.println("Data written successfully to test.txt");

            // ----------------------------------------
            // STEP 2: Read text from the file
            // ----------------------------------------
            fileReader = new FileReader("test.txt");

            int data; // stores each character as an integer
            while ((data = fileReader.read()) != -1) {
                // Cast int to char and print character by character
                System.out.print((char) data);
            }

            System.out.println(); // move to next line after file content

        } catch (IOException e) {
            // Handles file not found, read/write errors, permissions issues, etc.
            System.out.println("File handling error: " + e.getMessage());
        } finally {
            // ----------------------------------------
            // STEP 3: Close resources
            // ----------------------------------------
            // Close FileWriter first if it was opened
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing FileWriter: " + e.getMessage());
            }

            // Close FileReader if it was opened
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing FileReader: " + e.getMessage());
            }
        }
    }
}