package com.management.library.service;

import com.management.library.model.BookRecord;

import java.util.List;
import java.util.function.Function;

public interface BookService {
    List<BookRecord> getAllBooks();

    List<BookRecord> getAvailableBooks();

}
