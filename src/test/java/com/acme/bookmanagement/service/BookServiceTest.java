package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookServiceTest {
    // Create a mock of the repository layer to control its behavior in tests
    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);
    
    // Create the service instance with the mocked repository
    private final BookService bookService = new BookService(bookRepository);

    // Test data: Sample book used across test cases
    private final Book book = new Book(1L,
            "title-1",
            "author-1",
            LocalDate.of(2021, 2, 3));

    // Test the findAll method
    @Test
    void testFindAll() {
        // Configure mock: When findAll is called, return a list with one book
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        
        // Verify that the service returns the correct number of books
        assertEquals(1, bookService.findAll().size());
    }

    // Test the findById method
    @Test
    void testFindById() {
        // Configure mock: When findById(1L) is called, return our test book
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        
        // Verify that the service returns the correct book
        // orElse(null) is used to handle the Optional return type
        assertEquals(book, bookService.findById(1L).orElse(null));
    }

    // Test the save method
    @Test
    void testSave() {
        // Configure mock: When save is called with our test book, return the same book
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        
        // Verify that the service returns the saved book
        assertEquals(book, bookService.save(book));
    }

    // Test the deleteById method
    @Test
    void testDeleteById() {
        // Call the service method
        bookService.deleteById(1L);
        
        // Verify that the repository's deleteById was called exactly once with the correct ID
        Mockito.verify(bookRepository).deleteById(1L);
    }
}
