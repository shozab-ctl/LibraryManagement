package com.management.library.repository;

import com.management.library.entity.Borrowed;
import com.management.library.entity.User;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedRepository extends JpaRepository<Borrowed, Long> {

    List<Borrowed> findByBorrower_IdAndBorrowedFromGreaterThanEqualAndBorrowedToLessThanEqual(Long user, LocalDate from, LocalDate to);

    List<Borrowed> findByBorrowedFromEquals(LocalDate date);
}
