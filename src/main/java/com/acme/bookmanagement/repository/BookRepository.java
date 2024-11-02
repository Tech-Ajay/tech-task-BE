package com.acme.bookmanagement.repository;

import com.acme.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPublishedDateBetween(LocalDate start, LocalDate end);
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
    List<Book> findAllByOrderByAuthorAsc();
    List<Book> findAllByOrderByAuthorDesc();
    List<Book> findAllByOrderByPublishedDateAsc();
    List<Book> findAllByOrderByPublishedDateDesc();
    List<Book> findByAuthorContainingIgnoreCase(String author);
}

