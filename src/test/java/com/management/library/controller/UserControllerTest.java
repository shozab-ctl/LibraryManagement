package com.management.library.controller;

import com.management.library.entity.User;
import com.management.library.model.UserRecord;
import com.management.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UsersController usersController;

    private final String TEST_VALUE = "Test";
    Supplier<UserRecord> userRecordSupplier = () -> new UserRecord(TEST_VALUE, TEST_VALUE, LocalDate.now(), LocalDate.now(), TEST_VALUE);

    @Test
    void getUsersBorrowedBooks() throws Exception {
        mockMvc.perform(get("/users/borrowed"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Found 11 records"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(11));
    }

    @Test
    void getUsersBorrowedBooksNoContent() {
        when(service.findUsersBorrowedBooks()).thenReturn(Collections.emptyList());
        var res = usersController.getUsersBorrowedBooks();
        assertSame(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void getNonTerminatedUsers() {
        when(service.findNonTerminatedUsers()).thenReturn(Collections.singletonList(userRecordSupplier.get()));
        var res = usersController.getNonTerminatedUsers();
        assertSame(res.getStatusCode(), HttpStatus.OK);
        assertNotNull(res);
    }

    @Test
    void getNonTerminatedUsersNoContent() {
        when(service.findNonTerminatedUsers()).thenReturn(Collections.emptyList());
        var res = usersController.getNonTerminatedUsers();
        assertSame(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void getUsersBorrowedOnDate() {

        when(service.getUsersBorrowedOnDate(any(LocalDate.class))).thenReturn(Collections.singletonList(userRecordSupplier.get()));
        var res = usersController.getUsersBorrowedOnDate(LocalDate.now());
        assertSame(res.getStatusCode(), HttpStatus.OK);
        assertNotNull(res);
    }

    @Test
    void getUsersBorrowedOnDateNoContent() {
        when(service.getUsersBorrowedOnDate(any(LocalDate.class))).thenReturn(Collections.emptyList());
        var res = usersController.getUsersBorrowedOnDate(LocalDate.now());
        assertSame(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }


}

