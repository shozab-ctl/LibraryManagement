package com.management.library.service;

import com.management.library.entity.Book;
import com.management.library.model.BookRecord;
import com.management.library.repository.BookRepository;
import com.management.library.repository.BorrowedRepository;
import com.management.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowedRepository borrowedRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private final String TITLE = "Test Book";
    private final String AUTHOR = "Test Author";
    private final String GENRE = "Test Genre";
    private final String PUBLISHER = "Test Publisher";
    Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book(1L, TITLE, AUTHOR, GENRE, PUBLISHER);
    }

    @Test
    void getAvailableBooks_shouldReturnAvailableBooks() {
        List<Book> availableBooks = Collections.singletonList(testBook);
        when(bookRepository.findAvailableBooks()).thenReturn(availableBooks);
        List<BookRecord> result = bookService.getAvailableBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAvailableBooks();
    }

    @Test
    void getAvailableBooks_shouldReturnEmptyList() {
        when(bookRepository.findAvailableBooks()).thenReturn(Collections.emptyList());
        List<BookRecord> result = bookService.getAvailableBooks();
        assertNull(result);
        verify(bookRepository, times(1)).findAvailableBooks();
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        List<Book> allBooks = Collections.singletonList(testBook);
        when(bookRepository.findAll()).thenReturn(allBooks);
        List<BookRecord> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_shouldReturnEmptyList() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        List<BookRecord> result = bookService.getAllBooks();
        assertNull(result);
        verify(bookRepository, times(1)).findAll();
    }
}
