package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

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
        List<Book> allBooks = books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
        when(this.bookService.findById(1L))
                .thenReturn(Optional.ofNullable(books.get(1L)));

        this.graphQlTester
                .documentName("findBookById")
                .variable("id", 1L)
                .execute()
                .path("findBookById")
                .matchesJson("""
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
}