package org.example;

import java.io.*;

/**
 * COMPLETE JAVA SERIALIZATION EXAMPLE
 * ===================================
 * This example demonstrates:
 * 1. Basic object serialization/deserialization
 * 2. 'transient' keyword behavior
 * 3. Custom versioning with serialVersionUID
 * 4. Real-world security implications
 *
 * KEY CONCEPTS FOR INTERVIEWS:
 * - Serializable marker interface (no methods)
 * - ObjectOutputStream/ObjectInputStream
 * - transient fields are NOT serialized
 * - Versioning prevents ClassCastException
 * - Security: Never serialize sensitive data!
 */

class Student implements Serializable {

    // ✅ CRITICAL: Use serialVersionUID for versioning control
    // Prevents ClassCastException during deserialization
    // Change this value = different class version
    private static final long serialVersionUID = 1L;

    // Regular field - WILL be serialized
    String name;

    // transient field - WILL NOT be serialized (security best practice)
    // Passwords, session IDs, DB connections should always be transient
    transient String password;

    // Constructor
    public Student(String name, String password) {
        this.name = name;
        this.password = password;
        System.out.println("Student1 object created: " + name);
    }

    // Custom getter for password (for demo purposes only)
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Student1{name='" + name + "', password='" +
                (password != null ? "******" : "null (not serialized)") + "'}";
    }
}

/**
 * MAIN DEMO CLASS - Serialization in Action
 */
public class SerializationDeserializationDemo {

    public static void main(String[] args) {
        try {
            // ========================================
            // STEP 1: CREATE OBJECT TO SERIALIZE
            // ========================================
            System.out.println("=== STEP 1: Creating Student object ===");
            Student stu = new Student("Abhishek", "abc123");
            System.out.println("Original object: " + stu);

            // ========================================
            // STEP 2: SERIALIZE (Object -> Byte Stream)
            // ========================================
            System.out.println("\n=== STEP 2: Serializing to stu.ser ===");
            serializeStudent(stu);

            // ========================================
            // STEP 3: DESERIALIZE (Byte Stream -> Object)
            // ========================================
            System.out.println("\n=== STEP 3: Deserializing from stu.ser ===");
            Student deserializedStu = deserializeStudent();
            System.out.println("Deserialized object: " + deserializedStu);

            // ========================================
            // STEP 4: VERIFY BEHAVIOR
            // ========================================
            System.out.println("\n=== STEP 4: Results Analysis ===");
            System.out.println("Name (serialized): " + deserializedStu.name);
            System.out.println("Password (transient): " +
                    (deserializedStu.password != null ?
                            deserializedStu.password : "NULL - NOT SERIALIZED!"));

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    /**
     * SERIALIZATION METHOD
     * Converts Java object to byte stream and saves to file
     */
    private static void serializeStudent(Student student) throws IOException {
        // Create file output stream pointing to .ser file
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("stu.ser"))) {

            oos.writeObject(student);
            System.out.println("✅ Object serialized to stu.ser");
        }
    }

    /**
     * DESERIALIZATION METHOD
     * Converts byte stream from file back to Java object
     */
    private static Student deserializeStudent() throws IOException, ClassNotFoundException {
        // Create file input stream from .ser file
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("stu.ser"))) {

            Student student = (Student) ois.readObject();
            System.out.println("✅ Object deserialized successfully");
            return student;
        }
    }
}