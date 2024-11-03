package com.acme.bookmanagement.model;

import java.util.List;

/**
 * Represents a paginated result of books.
 * This class provides pagination information along with the actual content
 * for displaying books in a paginated format.
 */
public class BookPage {
    /** List of books in the current page */
    private final List<Book> content;
    
    /** Total number of books across all pages */
    private final long totalElements;
    
    /** Total number of pages available */
    private final int totalPages;
    
    /** Current page number (0-based) */
    private final int pageNumber;
    
    /** Number of items per page */
    private final int pageSize;

    /**
     * Creates a new BookPage with pagination information
     * @param content List of books in the current page
     * @param totalElements Total number of books across all pages
     * @param totalPages Total number of available pages
     * @param pageNumber Current page number (0-based)
     * @param pageSize Number of items per page
     */
    public BookPage(List<Book> content, long totalElements, int totalPages, 
                   int pageNumber, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * Gets the list of books in the current page
     * @return List of Book objects for the current page
     */
    public List<Book> getContent() {
        return content;
    }

    /**
     * Gets the total number of books across all pages
     * @return Total number of books in the repository
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Gets the total number of pages
     * @return Total number of pages available
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Gets the current page number
     * @return Current page number (0-based)
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Gets the number of items per page
     * @return Number of items displayed per page
     */
    public int getPageSize() {
        return pageSize;
    }
} 