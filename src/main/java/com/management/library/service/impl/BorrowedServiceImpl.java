package com.management.library.service.impl;

import com.management.library.entity.Book;
import com.management.library.entity.Borrowed;
import com.management.library.model.BookRecord;
import com.management.library.repository.BorrowedRepository;
import com.management.library.service.BorrowedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BorrowedServiceImpl implements BorrowedService {

    @Autowired
    private BorrowedRepository repository;
    private final String CLASS_NAME = this.getClass().getName();

    public List<BookRecord> findBooksBorrowedByUserInDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Borrowed> entityList = repository.findByBorrower_IdAndBorrowedFromGreaterThanEqualAndBorrowedToLessThanEqual(userId, startDate, endDate);
        if (entityList.isEmpty()) {
            log.info(CLASS_NAME + "== no data available for provided input ==");
        }
        return entityList.stream().map(convertEntityToRecord
        ).collect(Collectors.toList());
    }

    private final Function<Borrowed, BookRecord> convertEntityToRecord =
            borrowed -> new BookRecord(borrowed.getBook().getTitle(), borrowed.getBook().getAuthor(), borrowed.getBook().getGenre(), borrowed.getBook().getPublisher());
}
