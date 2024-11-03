package com.acme.bookmanagement.repository;

import com.acme.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Book entities in the database.
 * Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for complex queries.
 */
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    /**
     * Finds books published between two dates, inclusive.
     * @param startDate The start date of the date range
     * @param endDate The end date of the date range
     * @return List of books published within the specified date range
     */
    List<Book> findByPublishedDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Searches for books where the title contains the specified string (case-insensitive).
     * @param title The title substring to search for
     * @return List of books matching the title criteria
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Searches for books where the author contains the specified string (case-insensitive).
     * @param author The author substring to search for
     * @return List of books matching the author criteria
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Retrieves all books sorted by title in ascending order.
     * @return List of books sorted by title (A-Z)
     */
    List<Book> findAllByOrderByTitleAsc();

    /**
     * Retrieves all books sorted by title in descending order.
     * @return List of books sorted by title (Z-A)
     */
    List<Book> findAllByOrderByTitleDesc();

    /**
     * Retrieves all books sorted by author in ascending order.
     * @return List of books sorted by author (A-Z)
     */
    List<Book> findAllByOrderByAuthorAsc();

    /**
     * Retrieves all books sorted by author in descending order.
     * @return List of books sorted by author (Z-A)
     */
    List<Book> findAllByOrderByAuthorDesc();

    /**
     * Retrieves all books sorted by published date in ascending order.
     * @return List of books sorted by published date (oldest first)
     */
    List<Book> findAllByOrderByPublishedDateAsc();

    /**
     * Retrieves all books sorted by published date in descending order.
     * @return List of books sorted by published date (newest first)
     */
    List<Book> findAllByOrderByPublishedDateDesc();
}

