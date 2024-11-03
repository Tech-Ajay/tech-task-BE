package com.acme.bookmanagement.model;

import java.util.List;

public class BookPage {
    private final List<Book> content;
    private final long totalElements;
    private final int totalPages;
    private final int pageNumber;
    private final int pageSize;

    public BookPage(List<Book> content, long totalElements, int totalPages, 
                   int pageNumber, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public List<Book> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
} 