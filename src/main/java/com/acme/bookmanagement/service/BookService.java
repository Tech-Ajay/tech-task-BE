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

