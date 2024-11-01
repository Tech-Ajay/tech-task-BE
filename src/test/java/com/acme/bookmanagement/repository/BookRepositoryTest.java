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

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        book1 = new Book(null, "The God Father", "Mario Puzo", LocalDate.of(2021, 2, 3));
        book2 = new Book(null, "The God Particle", "Leon Lederman", LocalDate.of(2022, 3, 4));
        
        book1 = repo.save(book1);
        book2 = repo.save(book2);
    }

    @AfterEach
    public void tearDown() {
        repo.delete(book1);
        repo.delete(book2);
    }

    @Test
    void testSavedBookCanBeFoundById() {
        Book savedBook = repo.findById(book1.getId()).orElse(null);

        assertNotNull(savedBook);
        assertEquals("Mario Puzo", savedBook.getAuthor());
        assertEquals("The God Father", savedBook.getTitle());
        assertEquals(LocalDate.of(2021, 2, 3), savedBook.getPublishedDate());
    }

    @Test
    void testUpdatedBookCanBeFoundByIdWithUpdatedData() {
        book1.setTitle("title-one");
        repo.save(book1);

        Book updatedBook = repo.findById(book1.getId()).orElse(null);

        assertNotNull(updatedBook);
        assertEquals("title-one", updatedBook.getTitle());
    }

    @Test
    void testFindByTitleContaining() {
        List<Book> books = repo.findByTitleContainingIgnoreCase("god");
        assertEquals(2, books.size());
    }

    @Test
    void testFindByDateRange() {
        List<Book> books = repo.findByPublishedDateBetween(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 12, 31)
        );
        assertEquals(1, books.size());
        assertEquals(book1.getId(), books.get(0).getId());
    }

    @Test
    void testFindAllByOrderByTitleAsc() {
        List<Book> books = repo.findAllByOrderByTitleAsc();
        assertEquals("The God Father", books.get(0).getTitle());
        assertEquals("The God Particle", books.get(1).getTitle());
    }

    @Test
    void testFindAllByOrderByTitleDesc() {
        List<Book> books = repo.findAllByOrderByTitleDesc();
        assertEquals("The God Particle", books.get(0).getTitle());
        assertEquals("The God Father", books.get(1).getTitle());
    }

}
