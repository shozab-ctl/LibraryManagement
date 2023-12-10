package com.management.library.controller;

import com.management.library.model.BookRecord;
import com.management.library.model.GenericResponse;
import com.management.library.model.UserRecord;
import com.management.library.service.UserService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

    private final String CLASS_NAME = this.getClass().getName();

    @Autowired
    private UserService service;


    /**
     * @return returns all users who have actually borrowed at least one book
     */
    @GetMapping("/borrowed")
    public ResponseEntity<GenericResponse<List<UserRecord>>> getUsersBorrowedBooks() {
        log.info("== inside class " + CLASS_NAME + ": getUsersBorrowedBooks()");

        List<UserRecord> users = service.findUsersBorrowedBooks();
        if (CollectionUtils.isEmpty(users)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new GenericResponse<>("Found " + users.size() + " records", users));
        }

    }

    ;

    /**
     * @return returns all non-terminated users who have not currently borrowed anything
     */
    @GetMapping("/non-terminated")
    public ResponseEntity<GenericResponse<List<UserRecord>>> getNonTerminatedUsers() {
        log.info("== inside class " + CLASS_NAME + ": getNonTerminatedUsers()");

        List<UserRecord> users = service.findNonTerminatedUsers();
        if (CollectionUtils.isEmpty(users)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new GenericResponse<>("Found " + users.size() + " records", users));
        }
    }

    ;

    /**
     * @return returns all users who have borrowed a book on a given date
     */
    @GetMapping("/borrowed-on-date/{date}")
    public ResponseEntity<GenericResponse<List<UserRecord>>> getUsersBorrowedOnDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        log.info("== inside class " + CLASS_NAME + ": getUsersBorrowedOnDate()");

        List<UserRecord> users = service.getUsersBorrowedOnDate(date);
        if (CollectionUtils.isEmpty(users)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new GenericResponse<>("Found " + users.size() + " records", users));
        }
    }

    ;
}
