package com.management.library.service.impl;

import com.management.library.entity.Book;
import com.management.library.model.BookRecord;
import com.management.library.repository.BookRepository;
import com.management.library.repository.BorrowedRepository;
import com.management.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.management.library.constants.Constant.NO_DATA;
import static com.management.library.constants.Constant.SUCCESS_DATA;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private BorrowedRepository borrowedRepository;

    private final String CLASS_NAME = this.getClass().getName();

    private final Function<Book, BookRecord> convertEntityToRecord =
            book -> new BookRecord(book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublisher());
    public Function<List<Book>, List<BookRecord>> listConvert = bookList1 -> bookList1.stream()
            .map(convertEntityToRecord)
            .collect(Collectors.toList());

    @Override
    public List<BookRecord> getAvailableBooks() {
        List<Book> bookList = repository.findAvailableBooks();
        if (!bookList.isEmpty()) {
            log.info(CLASS_NAME + "== " + SUCCESS_DATA + " ==");
            return listConvert.apply(bookList);

        } else {
            log.info(CLASS_NAME + "== " + NO_DATA + " ==");
        }
        return null;
    }

    @Override
    public List<BookRecord> getAllBooks() {
        List<Book> bookList = repository.findAll();
        if (!bookList.isEmpty()) {
            log.info(CLASS_NAME + "== " + SUCCESS_DATA + " ==");
            return listConvert.apply(bookList);

        } else {
            log.info(CLASS_NAME + "== no data available for provided input ==");
        }
        return null;
    }


}
