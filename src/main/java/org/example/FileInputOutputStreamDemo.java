package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileInputOutputStreamDemo
 * -------------------------
 * This program demonstrates binary file handling in Java.
 *
 * What it does:
 * 1. Reads an image file using FileInputStream
 * 2. Writes the same bytes into another file using FileOutputStream
 * 3. Creates an exact copy of the source file
 *
 * Interview points:
 * - FileInputStream reads raw bytes from a file.
 * - FileOutputStream writes raw bytes to a file.
 * - These classes are used for binary files such as images, videos, PDFs, etc.
 * - The read() method returns an int, and -1 means end of file.
 */
public class FileInputOutputStreamDemo {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            // ---------------------------------------------------
            // STEP 1: Open the source image file for reading
            // ---------------------------------------------------
            // Replace the file name with your actual image path.
            fileInputStream = new FileInputStream("Screenshot (1).png");

            // ---------------------------------------------------
            // STEP 2: Open the destination file for writing
            // ---------------------------------------------------
            // This file will store a copy of the source image bytes.
            fileOutputStream = new FileOutputStream("testimage.dat");

            // ---------------------------------------------------
            // STEP 3: Read one byte at a time and write it out
            // ---------------------------------------------------
            int data;

            while ((data = fileInputStream.read()) != -1) {
                // Write the same byte into the new file
                fileOutputStream.write(data);
            }

            // Ensure all buffered bytes are written to the file
            fileOutputStream.flush();

            System.out.println("File copied successfully.");

        } catch (IOException e) {
            // Handles file not found, access errors, disk issues, etc.
            System.out.println("File handling error: " + e.getMessage());
        } finally {
            // ---------------------------------------------------
            // STEP 4: Close resources properly
            // ---------------------------------------------------
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing FileInputStream: " + e.getMessage());
            }

            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing FileOutputStream: " + e.getMessage());
            }
        }
    }
}