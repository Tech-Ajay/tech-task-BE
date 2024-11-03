package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.BookPage;
import com.acme.bookmanagement.model.SortField;
import com.acme.bookmanagement.model.SortOrder;
import com.acme.bookmanagement.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
            @Argument String imageUrl) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedDate(LocalDate.parse(publishedDate));
        book.setDescription(description);
        book.setImageUrl(imageUrl);
        return bookService.save(book);
    }

    @MutationMapping
    public Integer deleteBook(@Argument Integer id) {
        return bookService.deleteById(id);
    }

    @QueryMapping
    public List<Book> findBooksByDateRange(
            @Argument String startDate,
            @Argument String endDate) {
        return bookService.findByDateRange(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }

    @QueryMapping
    public List<Book> findBooksByTitleContaining(@Argument String title) {
        return bookService.findByTitleContaining(title);
    }

    @QueryMapping
    public List<Book> findAllBooksSorted(
            @Argument SortField sortField,
            @Argument SortOrder sortOrder) {
        return bookService.findAllSorted(sortField, sortOrder);
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
    public List<Book> findBooksByAuthorContaining(@Argument String author) {
        return bookService.findByAuthorContaining(author);
    }

    @QueryMapping
    public BookPage findAllBooksWithPagination(
            @Argument Integer page,
            @Argument Integer size,
            @Argument SortField sortField,
            @Argument SortOrder sortOrder,
            @Argument String titleFilter,
            @Argument String authorFilter) {
        Page<Book> bookPage = bookService.findAllWithPagination(
                page, size, sortField, sortOrder, titleFilter, authorFilter);

        return new BookPage(
                bookPage.getContent(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages(),
                bookPage.getNumber(),
                bookPage.getSize());
    }
}
