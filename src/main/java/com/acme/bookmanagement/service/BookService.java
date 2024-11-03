package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.SortField;
import com.acme.bookmanagement.model.SortOrder;
import com.acme.bookmanagement.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class that handles business logic for Book operations.
 * Provides methods for CRUD operations, searching, sorting, and pagination of books.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Constructs a new BookService with the required repository.
     * @param bookRepository The repository for book operations
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books from the database.
     * @return List of all books
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Finds a book by its ID.
     * @param id The ID of the book to find
     * @return Optional containing the book if found, empty otherwise
     */
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    /**
     * Saves a book to the database.
     * @param book The book to save
     * @return The saved book with updated information
     */
    public Book save(Book book) {
        // Add debug logging
        System.out.println("Saving book with description: " + book.getDescription());
        System.out.println("Saving book with imageUrl: " + book.getImageUrl());
        
        Book savedBook = bookRepository.save(book);
        
        // Verify saved data
        System.out.println("Saved book description: " + savedBook.getDescription());
        System.out.println("Saved book imageUrl: " + savedBook.getImageUrl());
        
        return savedBook;
    }

    /**
     * Deletes a book by its ID.
     * @param id The ID of the book to delete
     * @return The ID of the deleted book
     */
    public Integer deleteById(Integer id) {
        bookRepository.deleteById(id);
        return id;
    }

    /**
     * Finds books published within a specific date range.
     * @param startDate The start date of the range
     * @param endDate The end date of the range
     * @return List of books published between the specified dates
     */
    public List<Book> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookRepository.findByPublishedDateBetween(startDate, endDate);
    }

    /**
     * Searches for books by title (case-insensitive partial match).
     * @param title The title to search for
     * @return List of books matching the title criteria
     */
    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Retrieves all books sorted by the specified field and order.
     * @param sortField The field to sort by (TITLE, AUTHOR, or PUBLISHED_DATE)
     * @param sortOrder The sort direction (ASC or DESC)
     * @return List of sorted books
     */
    public List<Book> findAllSorted(SortField sortField, SortOrder sortOrder) {
        return switch (sortField) {
            case TITLE -> sortOrder == SortOrder.ASC ? 
                bookRepository.findAllByOrderByTitleAsc() : 
                bookRepository.findAllByOrderByTitleDesc();
            case AUTHOR -> sortOrder == SortOrder.ASC ? 
                bookRepository.findAllByOrderByAuthorAsc() : 
                bookRepository.findAllByOrderByAuthorDesc();
            case PUBLISHED_DATE -> sortOrder == SortOrder.ASC ? 
                bookRepository.findAllByOrderByPublishedDateAsc() : 
                bookRepository.findAllByOrderByPublishedDateDesc();
        };
    }

    /**
     * Retrieves all books sorted by title.
     * @param ascending If true, sorts A-Z; if false, sorts Z-A
     * @return List of books sorted by title
     */
    public List<Book> findAllSortedByTitle(Boolean ascending) {
        return ascending ? 
            bookRepository.findAllByOrderByTitleAsc() : 
            bookRepository.findAllByOrderByTitleDesc();
    }

    /**
     * Retrieves all books sorted by publication date.
     * @param ascending If true, sorts oldest first; if false, sorts newest first
     * @return List of books sorted by date
     */
    public List<Book> findAllSortedByDate(Boolean ascending) {
        return ascending ? 
            bookRepository.findAllByOrderByPublishedDateAsc() : 
            bookRepository.findAllByOrderByPublishedDateDesc();
    }

    /**
     * Searches for books by author (case-insensitive partial match).
     * @param author The author name to search for
     * @return List of books matching the author criteria
     */
    public List<Book> findByAuthorContaining(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    /**
     * Retrieves a paginated and filtered list of books with sorting options.
     * @param page Zero-based page index
     * @param size Number of items per page
     * @param sortField Field to sort by (can be null)
     * @param sortOrder Sort direction (can be null)
     * @param titleFilter Optional title filter (can be null)
     * @param authorFilter Optional author filter (can be null)
     * @return Page of books matching the criteria
     */
    public Page<Book> findAllWithPagination(
        Integer page, 
        Integer size, 
        SortField sortField,
        SortOrder sortOrder,
        String titleFilter,
        String authorFilter
    ) {
        Sort sort = Sort.unsorted();
        if (sortField != null && sortOrder != null) {
            String fieldName = switch (sortField) {
                case TITLE -> "title";
                case AUTHOR -> "author";
                case PUBLISHED_DATE -> "publishedDate";
            };
            sort = Sort.by(sortOrder == SortOrder.ASC ? 
                Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        
        Specification<Book> spec = Specification.where(null);
        if (titleFilter != null && !titleFilter.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("title")), 
                    "%" + titleFilter.toLowerCase() + "%"));
        }
        if (authorFilter != null && !authorFilter.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("author")), 
                    "%" + authorFilter.toLowerCase() + "%"));
        }

        return bookRepository.findAll(spec, pageable);
    }
}

