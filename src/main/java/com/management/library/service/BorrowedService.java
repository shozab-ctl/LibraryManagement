package com.management.library.service;

import com.management.library.entity.Book;
import com.management.library.model.BookRecord;

import java.time.LocalDate;
import java.util.List;

public interface BorrowedService {

    List<BookRecord> findBooksBorrowedByUserInDateRange(Long userId, LocalDate startDate, LocalDate endDate);

}
