package com.acme.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Book Management application.
 * This Spring Boot application provides functionality for managing books,
 * including CRUD operations, searching, sorting, and pagination.
 */
@SpringBootApplication
public class BookManagementApplication {

    /**
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
    }

}
