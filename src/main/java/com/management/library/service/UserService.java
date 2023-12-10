package com.management.library.service;

import com.management.library.model.UserRecord;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserRecord> findUsersBorrowedBooks();

    List<UserRecord> findNonTerminatedUsers();

    List<UserRecord> getUsersBorrowedOnDate(LocalDate date);
}
