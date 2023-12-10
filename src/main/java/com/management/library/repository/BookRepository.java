package com.management.library.repository;

import com.management.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String book);

    @Query("SELECT b FROM Book b WHERE b.id NOT IN (SELECT br.book.id FROM Borrowed br)")
    List<Book> findAvailableBooks();
}



