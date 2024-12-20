package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Test class for GraphQL Book Controller using Spring's GraphQlTest
// This annotation loads only the necessary components for testing GraphQL endpoints
@GraphQlTest(BookController.class)
public class BookControllerTest {

    // GraphQlTester provides utilities for testing GraphQL queries
    @Autowired
    private GraphQlTester graphQlTester;

    // Mock the service layer to control its behavior in tests
    @MockBean
    private BookService bookService;

    // Test data: Map of sample books used across test cases
    private final Map<Long, Book> books = Map.of(
            1L, new Book(1L,
                    "title-1",
                    "author-1",
                    LocalDate.of(2021, 2, 3),
                    "description-1",
                    "imageUrl-1"),
            2L, new Book(2L,
                    "title-2",
                    "author-2",
                    LocalDate.of(2021, 2, 3),
                    "description-2",
                    "imageUrl-2")
    );

    @Test
    void shouldGetBookById() {
        // Mock the service to return a specific book when findById is called
        when(this.bookService.findById(1L))
                .thenReturn(Optional.ofNullable(books.get(1L)));

        // Execute GraphQL query and verify response
        this.graphQlTester
                .documentName("findBookById")  // References a .graphql file with the query
                .variable("id", 1L)           // Set query variables
                .execute()                    // Run the query
                .path("findBookById")         // Navigate to response field
                .matchesJson("""             // Verify exact JSON match
                    {
                        "id": 1,
                        "title": "title-1",
                        "author": "author-1",
                        "publishedDate": "2021-02-03",
                        "description": "description-1",
                        "imageUrl": "imageUrl-1"
                    }
                """);
    }

    @Test
    void shouldGetAllBooks() {
        List<Book> allBooks = books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
        when(this.bookService.findAll())
                .thenReturn(new ArrayList<>(books.values()));

        this.graphQlTester
                .documentName("findAllBooks")
                .execute()
                .path("findAllBooks")
                .matchesJson("""
                    [
                        {
                            "id": 1,
                            "title": "title-1",
                            "author": "author-1",
                            "publishedDate": "2021-02-03",
                            "description": "description-1",
                            "imageUrl": "imageUrl-1"
                        },
                        {
                            "id": 2,
                            "title": "title-2",
                            "author": "author-2",
                            "publishedDate": "2021-02-03",
                            "description": "description-2",
                            "imageUrl": "imageUrl-2"
                        }
                    ]
                """);
    }

    @Test
    void shouldCreateBook() {
        Long id = 3L;
        String title = "title-3";
        String author = "author-3";
        LocalDate publishedDate = LocalDate.of(2021, 2, 3);
        Book savedBook = new Book(id,
                title,
                author,
                publishedDate,
                "description-3",
                "imageUrl-3");
        when(this.bookService.save(any(Book.class)))
                .thenReturn(savedBook);

        this.graphQlTester
                .documentName("createBook")
                .variable("title", title)
                .variable("author", author)
                .variable("publishedDate", publishedDate.toString())
                .execute()
                .path("createBook")
                .matchesJson("""
                    {
                        "id": 3,
                        "title": "title-3",
                        "author": "author-3",
                        "publishedDate": "2021-02-03",
                        "description": "description-3",
                        "imageUrl": "imageUrl-3"
                    }
                """);
    }

    @Test
    void shouldDeleteBook() {
        List<Book> allBooks = books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
        when(this.bookService.deleteById(1L))
                .thenReturn(1L);

        this.graphQlTester
                .documentName("deleteBook")
                .variable("id", 1L)
                .execute()
                .path("deleteBook")
                .matchesJson("1");
    }

    @Test
    void shouldGetPaginatedBooks() {
        // Create a Page object with test data and pagination info
        Page<Book> page = new PageImpl<>(
            new ArrayList<>(books.values()),
            PageRequest.of(0, 2),  // Page 0, size 2
            books.size()
        );
        
        // Mock service to return paginated results with sorting and filtering
        when(this.bookService.findAllWithPagination(
            eq(0), eq(2),                           // Page number and size
            eq(SortField.TITLE), eq(SortOrder.ASC), // Sorting parameters
            eq("title"), eq("author")))             // Filter parameters
        .thenReturn(page);

        // Execute GraphQL query with pagination, sorting, and filtering
        this.graphQlTester
            .documentName("findAllBooksWithPagination")
            .variable("page", 0)
            .variable("size", 2)
            .variable("sortField", "TITLE")
            .variable("sortOrder", "ASC")
            .variable("titleFilter", "title")
            .variable("authorFilter", "author")
            .execute()
            .path("findAllBooksWithPagination")
            .matchesJson("""
                {
                    "content": [
                        {
                            "id": 1,
                            "title": "title-1",
                            "author": "author-1",
                            "publishedDate": "2021-02-03"
                        },
                        {
                            "id": 2,
                            "title": "title-2",
                            "author": "author-2",
                            "publishedDate": "2021-02-03"
                        }
                    ],
                    "totalElements": 2,  // Total number of books
                    "totalPages": 1,     // Total number of pages
                    "pageNumber": 0,     // Current page number
                    "pageSize": 2        // Items per page
                }
            """);
    }
}