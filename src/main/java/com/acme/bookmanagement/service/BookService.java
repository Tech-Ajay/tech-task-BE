package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.SortField;
import com.acme.bookmanagement.model.SortOrder;
import com.acme.bookmanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

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

    public Integer deleteById(Integer id) {
        bookRepository.deleteById(id);
        return id;
    }

    public List<Book> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookRepository.findByPublishedDateBetween(startDate, endDate);
    }

    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

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

    public List<Book> findAllSortedByTitle(Boolean ascending) {
        return ascending ? 
            bookRepository.findAllByOrderByTitleAsc() : 
            bookRepository.findAllByOrderByTitleDesc();
    }

    public List<Book> findAllSortedByDate(Boolean ascending) {
        return ascending ? 
            bookRepository.findAllByOrderByPublishedDateAsc() : 
            bookRepository.findAllByOrderByPublishedDateDesc();
    }

    public List<Book> findByAuthorContaining(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}

