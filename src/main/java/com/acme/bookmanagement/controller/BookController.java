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

/**
 * GraphQL Controller for handling book-related operations
 * Provides endpoints for querying and mutating book data
 */
@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books in the system
     * @return List of all books
     */
    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    /**
     * Finds a specific book by its ID
     * @param id The book's unique identifier
     * @return Optional containing the book if found
     */
    @QueryMapping
    public Optional<Book> findBookById(@Argument Integer id) {
        return bookService.findById(id);
    }

    /**
     * Creates a new book in the system
     * @param title Book title
     * @param author Book author
     * @param publishedDate Publication date in ISO format (yyyy-MM-dd)
     * @param description Book description
     * @param imageUrl URL to book cover image
     * @return The created book with assigned ID
     */
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

    /**
     * Deletes a book from the system
     * @param id ID of the book to delete
     * @return The ID of the deleted book
     */
    @MutationMapping
    public Integer deleteBook(@Argument Integer id) {
        return bookService.deleteById(id);
    }

    /**
     * Finds books published within a specific date range
     * @param startDate Start date in ISO format (yyyy-MM-dd)
     * @param endDate End date in ISO format (yyyy-MM-dd)
     * @return List of books within the date range
     */
    @QueryMapping
    public List<Book> findBooksByDateRange(
            @Argument String startDate,
            @Argument String endDate) {
        return bookService.findByDateRange(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }

    /**
     * Searches for books by partial title match
     * @param title Title substring to search for
     * @return List of matching books
     */
    @QueryMapping
    public List<Book> findBooksByTitleContaining(@Argument String title) {
        return bookService.findByTitleContaining(title);
    }

    /**
     * Retrieves all books sorted by specified field and order
     * @param sortField Field to sort by (e.g., TITLE, AUTHOR, DATE)
     * @param sortOrder Sort direction (ASC or DESC)
     * @return Sorted list of books
     */
    @QueryMapping
    public List<Book> findAllBooksSorted(
            @Argument SortField sortField,
            @Argument SortOrder sortOrder) {
        return bookService.findAllSorted(sortField, sortOrder);
    }

    /**
     * Retrieves all books sorted by title
     * @param ascending If true, sorts in ascending order; descending if false
     * @return Sorted list of books
     */
    @QueryMapping
    public List<Book> findAllBooksSortedByTitle(@Argument Boolean ascending) {
        return bookService.findAllSortedByTitle(ascending);
    }

    /**
     * Retrieves all books sorted by publication date
     * @param ascending If true, sorts in ascending order; descending if false
     * @return Sorted list of books
     */
    @QueryMapping
    public List<Book> findAllBooksSortedByDate(@Argument Boolean ascending) {
        return bookService.findAllSortedByDate(ascending);
    }

    /**
     * Searches for books by partial author name match
     * @param author Author name substring to search for
     * @return List of matching books
     */
    @QueryMapping
    public List<Book> findBooksByAuthorContaining(@Argument String author) {
        return bookService.findByAuthorContaining(author);
    }

    /**
     * Retrieves a paginated and filtered list of books
     * @param page Zero-based page number
     * @param size Number of items per page
     * @param sortField Field to sort by
     * @param sortOrder Sort direction
     * @param titleFilter Optional title filter
     * @param authorFilter Optional author filter
     * @return BookPage containing results and pagination metadata
     */
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
