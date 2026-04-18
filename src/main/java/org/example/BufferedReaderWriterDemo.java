package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * BufferedReader and BufferedWriter Demo
 * --------------------------------------
 * This program demonstrates:
 * 1. Writing text using BufferedWriter
 * 2. Reading text using BufferedReader
 * 3. Different read/write methods
 *
 * Methods covered:
 * BufferedReader:
 *  - readLine()
 *  - read()
 *  - read(char[])
 *
 * BufferedWriter:
 *  - write(String)
 *  - write(char[])
 *  - newLine()
 *  - flush()
 *
 * Interview points:
 * - BufferedReader is used for efficient character input.
 * - BufferedWriter is used for efficient character output.
 * - readLine() reads a full line without the newline character.
 * - read() reads one character at a time.
 * - read(char[]) reads multiple characters into an array.
 * - write(String) writes a full string.
 * - write(char[]) writes character array content.
 * - newLine() adds a platform-dependent line separator.
 * - flush() forces buffered data to be written immediately.
 */
public class BufferedReaderWriterDemo {

    public static void main(String[] args) {
        String fileName = "buffered_demo.txt";

        // Step 1: Write sample data into file
        writeData(fileName);

        // Step 2: Read file using different BufferedReader methods
        readData(fileName);
    }

    /**
     * Writes sample text into the file using BufferedWriter.
     */
    private static void writeData(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            // write(String) -> writes a full string
            bw.write("Line 1: Hello Abhishek");

            // newLine() -> adds system-dependent line separator
            bw.newLine();

            // write(char[]) -> writes character array content
            char[] nameChars = { 'J', 'a', 'v', 'a', ' ', 'I', 'O' };
            bw.write(nameChars);

            bw.newLine();

            // Another write(String)
            bw.write("Line 3: BufferedWriter is efficient");

            // flush() -> forces buffer content to file immediately
            bw.flush();

            System.out.println("Data written successfully using BufferedWriter.");

        } catch (IOException e) {
            System.out.println("Error while writing file: " + e.getMessage());
        }
    }

    /**
     * Reads file content using BufferedReader methods.
     */
    private static void readData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            System.out.println("\n--- Reading using readLine() ---");
            String line;

            // readLine() -> reads one full line at a time
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error while reading file with readLine(): " + e.getMessage());
        }

        // Demonstrate read() and read(char[]) separately
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            System.out.println("\n--- Reading using read() ---");
            int ch;
            while ((ch = br.read()) != -1) {
                System.out.print((char) ch);
            }

            System.out.println();

        } catch (IOException e) {
            System.out.println("Error while reading file with read(): " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            System.out.println("\n--- Reading using read(char[]) ---");
            char[] buffer = new char[20];
            int charsRead;

            // read(char[]) -> reads multiple characters into array
            while ((charsRead = br.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, charsRead));
            }

            System.out.println();

        } catch (IOException e) {
            System.out.println("Error while reading file with read(char[]): " + e.getMessage());
        }
    }
}