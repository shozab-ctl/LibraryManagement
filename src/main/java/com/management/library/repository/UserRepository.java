package com.management.library.repository;

import com.management.library.entity.Book;
import com.management.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByName(String s);

    Optional<User> findByNameAndFirstName(String s, String s1);

    @Query("SELECT DISTINCT u FROM User u JOIN Borrowed b ON u.id = b.borrower.id")
    List<User> findUsersWithBorrowedBooks();

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT DISTINCT b.borrower.id FROM Borrowed b)")
    List<User> findUsersNotBorrowing();
}