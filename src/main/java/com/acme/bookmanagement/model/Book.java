package com.acme.bookmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import jakarta.persistence.Column;

/**
 * Entity class representing a book in the book management system.
 * This class maps to the book table in the database.
 */
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    /** Title of the book, required field */
    @Column(nullable = false)
    private String title;
    
    /** Author of the book, required field */
    @Column(nullable = false)
    private String author;
    
    /** Publication date of the book, required field */
    @Column(nullable = false)
    private LocalDate publishedDate;
    
    /** Book description, limited to 2000 characters */
    @Column(length = 2000)
    private String description;
    
    /** URL to book cover image, limited to 1000 characters */
    @Column(length = 1000)
    private String imageUrl;

    /**
     * Default constructor required by JPA
     */
    public Book() {
    }

    /**
     * Creates a new Book with essential fields
     * @param id The unique identifier for the book
     * @param title The title of the book
     * @param author The author of the book
     * @param publishedDate The date when the book was published
     */
    public Book(Integer id, String title, String author, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    /**
     * Creates a new Book with all available fields
     * @param id The unique identifier for the book
     * @param title The title of the book
     * @param author The author of the book
     * @param publishedDate The date when the book was published
     * @param description A detailed description of the book
     * @param imageUrl URL to the book's cover image
     */
    public Book(Integer id, String title, String author, LocalDate publishedDate, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the book's ID
     * @return The unique identifier of the book
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the book's ID
     * @param id The unique identifier to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the book's title
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book's title
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the book's author
     * @return The author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the book's author
     * @param author The author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the book's publication date
     * @return The date when the book was published
     */
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    /**
     * Sets the book's publication date
     * @param publishedDate The publication date to set
     */
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * Gets the book's description
     * @return The detailed description of the book
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the book's description
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the book's cover image URL
     * @return The URL to the book's cover image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the book's cover image URL
     * @param imageUrl The image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

