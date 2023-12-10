package com.management.library.controller;

import com.management.library.entity.Book;
import com.management.library.model.BookRecord;
import com.management.library.model.GenericResponse;
import com.management.library.service.BookService;
import com.management.library.service.BorrowedService;
import com.management.library.service.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import static com.management.library.utils.DateFormatter.convertDate;

@RestController
@RequestMapping("/books")
@Slf4j
public class BooksController {


    @Autowired
    private BookService service;

    @Autowired
    private BorrowedService borrowedService;
    private final String CLASS_NAME = this.getClass().getName();


    /**
     * @return returns all books borrowed by a given user in a given date range
     */
    @GetMapping("/borrowed-by-user-in-date-range/{userId}/{startDate}/{endDate}")
    public ResponseEntity<GenericResponse<List<BookRecord>>> getBooksBorrowedByUser(
            @PathVariable Long userId,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) {
        log.info("== inside class " + CLASS_NAME + ": getBooksBorrowedByUser()");
        List<BookRecord> books = borrowedService.findBooksBorrowedByUserInDateRange(userId, convertDate.apply(startDate), convertDate.apply(endDate));
        if (CollectionUtils.isEmpty(books)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new GenericResponse<>("Found " + books.size() + " records", books));
        }
    }


    /**
     * @return returns all available (not borrowed) books
     */
    @GetMapping("/available-books")
    public ResponseEntity<GenericResponse<List<BookRecord>>> getAvailableBooks() {
        log.info("== inside class " + CLASS_NAME + ": getAvailableBooks()");
        List<BookRecord> books = service.getAvailableBooks();
        if (CollectionUtils.isEmpty(books)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new GenericResponse<>("Found " + books.size() + " records", books));
        }
    }


}
