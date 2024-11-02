package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.SortField;
import com.acme.bookmanagement.model.SortOrder;
import com.acme.bookmanagement.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public Optional<Book> findBookById(@Argument Integer id) {
        return bookService.findById(id);
    }

    @MutationMapping
    public Book createBook(
        @Argument String title,
        @Argument String author,
        @Argument String publishedDate,
        @Argument String description,
        @Argument String imageUrl
    ) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedDate(LocalDate.parse(publishedDate));
        book.setDescription(description);
        book.setImageUrl(imageUrl);

        System.out.println("Creating book with description: " + description);
        System.out.println("Creating book with imageUrl: " + imageUrl);

        Book savedBook = bookService.save(book);

        System.out.println("Saved book description: " + savedBook.getDescription());
        System.out.println("Saved book imageUrl: " + savedBook.getImageUrl());

        return savedBook;
    }

    @MutationMapping
    public Integer deleteBook(@Argument Integer id) {
        return bookService.deleteById(id);
    }

    @QueryMapping
    public List<Book> findBooksByDateRange(
        @Argument String startDate,
        @Argument String endDate
    ) {
        return bookService.findByDateRange(
            LocalDate.parse(startDate),
            LocalDate.parse(endDate)
        );
    }

    @QueryMapping
    public List<Book> findBooksByTitleContaining(@Argument String title) {
        return bookService.findByTitleContaining(title);
    }

    @QueryMapping
    public List<Book> findAllBooksSortedByTitle(@Argument Boolean ascending) {
        return bookService.findAllSortedByTitle(ascending);
    }

    @QueryMapping
    public List<Book> findAllBooksSortedByDate(@Argument Boolean ascending) {
        return bookService.findAllSortedByDate(ascending);
    }

    @QueryMapping
    public List<String> getAllBookTitles() {
        return bookService.findAll().stream()
            .map(Book::getTitle)
            .collect(Collectors.toList());
    }

    @QueryMapping
    public List<Book> findAllBooksSorted(
        @Argument SortField sortField,
        @Argument SortOrder sortOrder
    ) {
        return bookService.findAllSorted(sortField, sortOrder);
    }

    @QueryMapping
    public List<Book> findBooksByAuthorContaining(@Argument String author) {
        return bookService.findByAuthorContaining(author);
    }
}
