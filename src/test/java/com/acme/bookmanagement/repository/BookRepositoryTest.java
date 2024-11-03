package com.acme.bookmanagement.repository;

import com.acme.bookmanagement.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// This annotation sets up an in-memory database for testing JPA repositories
// It configures Hibernate, Spring Data, and the DataSource
@DataJpaTest
public class BookRepositoryTest {

    // Spring automatically injects the repository implementation
    @Autowired
    private BookRepository repo;

    // Test data books that will be used across multiple tests
    private Book book1;
    private Book book2;

    // This method runs before each test
    // Sets up test data in the database
    @BeforeEach
    public void setUp() {
        // Create test books with null IDs (they'll be assigned by the database)
        book1 = new Book(null, "The God Father", "Mario Puzo", LocalDate.of(2021, 2, 3));
        book2 = new Book(null, "The God Particle", "Leon Lederman", LocalDate.of(2022, 3, 4));
        
        // Save books and update references with database-assigned IDs
        book1 = repo.save(book1);
        book2 = repo.save(book2);
    }

    // Cleanup after each test to ensure a clean state for the next test
    @AfterEach
    public void tearDown() {
        repo.delete(book1);
        repo.delete(book2);
    }

    // Test basic save and findById operations
    @Test
    void testSavedBookCanBeFoundById() {
        Book savedBook = repo.findById(book1.getId()).orElse(null);

        assertNotNull(savedBook);
        assertEquals("Mario Puzo", savedBook.getAuthor());
        assertEquals("The God Father", savedBook.getTitle());
        assertEquals(LocalDate.of(2021, 2, 3), savedBook.getPublishedDate());
    }

    // Test updating an existing book
    @Test
    void testUpdatedBookCanBeFoundByIdWithUpdatedData() {
        book1.setTitle("title-one");
        repo.save(book1);  // Save the updated book

        Book updatedBook = repo.findById(book1.getId()).orElse(null);

        assertNotNull(updatedBook);
        assertEquals("title-one", updatedBook.getTitle());
    }

    // Test case-insensitive search by partial title
    @Test
    void testFindByTitleContaining() {
        List<Book> books = repo.findByTitleContainingIgnoreCase("god");
        assertEquals(2, books.size());  // Should find both books containing "god"
    }

    // Test finding books within a date range
    @Test
    void testFindByDateRange() {
        List<Book> books = repo.findByPublishedDateBetween(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 12, 31)  // Should only find book1 in 2021
        );
        assertEquals(1, books.size());
        assertEquals(book1.getId(), books.get(0).getId());
    }

    // Test sorting books by title in ascending order
    @Test
    void testFindAllByOrderByTitleAsc() {
        List<Book> books = repo.findAllByOrderByTitleAsc();
        assertEquals("The God Father", books.get(0).getTitle());    // Should come first alphabetically
        assertEquals("The God Particle", books.get(1).getTitle());  // Should come second
    }

    // Test sorting books by title in descending order
    @Test
    void testFindAllByOrderByTitleDesc() {
        List<Book> books = repo.findAllByOrderByTitleDesc();
        assertEquals("The God Particle", books.get(0).getTitle());  // Should come first (reverse alphabetical)
        assertEquals("The God Father", books.get(1).getTitle());    // Should come second
    }
}
