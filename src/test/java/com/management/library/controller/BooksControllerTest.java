package com.management.library.controller;

import com.management.library.service.BorrowedService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BorrowedService borrowedService;

    @InjectMocks
    private BooksController booksController;

    @Test
    void getBooksBorrowedByUser_shouldReturnBooks() throws Exception {
        mockMvc.perform(get("/books/borrowed-by-user-in-date-range/1/2004-05-14/2020-12-31"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Found 13 records"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(13));
    }

    @Test
    void getBooksBorrowedByUser_shouldNotReturnBooks() throws Exception {
        mockMvc.perform(get("/books/borrowed-by-user-in-date-range/1/2022-01-01/2022-12-31"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAvailableBooks() throws Exception {
        mockMvc.perform(get("/books/available-books"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray());
    }


}

