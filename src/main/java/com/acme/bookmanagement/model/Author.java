package com.acme.bookmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Set;

/**
 * Entity class representing an author in the book management system.
 * This class maps to the author table in the database.
 */
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    /**
     * Default constructor required by JPA
     */
    public Author() {
    }

    /**
     * Creates a new Author with specified id and name
     * @param id The unique identifier for the author
     * @param name The full name of the author
     */
    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the author's ID
     * @return The unique identifier of the author
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the author's ID
     * @param id The unique identifier to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the author's name
     * @return The full name of the author
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the author's name
     * @param name The full name to set
     */
    public void setName(String name) {
        this.name = name;
    }
} 