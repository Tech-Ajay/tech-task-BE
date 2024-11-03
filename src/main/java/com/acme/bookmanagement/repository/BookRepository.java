package com.acme.bookmanagement.repository;

import com.acme.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    List<Book> findByPublishedDateBetween(LocalDate startDate, LocalDate endDate);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
    List<Book> findAllByOrderByAuthorAsc();
    List<Book> findAllByOrderByAuthorDesc();
    List<Book> findAllByOrderByPublishedDateAsc();
    List<Book> findAllByOrderByPublishedDateDesc();
}

