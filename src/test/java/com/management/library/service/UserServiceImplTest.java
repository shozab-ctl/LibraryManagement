package com.management.library.service;

import com.management.library.entity.Borrowed;
import com.management.library.entity.User;
import com.management.library.model.UserRecord;
import com.management.library.repository.BorrowedRepository;
import com.management.library.repository.UserRepository;
import com.management.library.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BorrowedRepository borrowedRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final String NAME = "Test Name";
    private final String FIRST_NAME = "Test First Name";
    private final LocalDate MEMBER_SINCE = LocalDate.of(2020, 1, 1);
    private final LocalDate MEMBER_TILL = LocalDate.of(2022, 1, 1);
    private final String GENDER = "Test Gender";

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, NAME, FIRST_NAME, MEMBER_SINCE, MEMBER_TILL, GENDER);
    }

    @Test
    void findUsersBorrowedBooks_shouldReturnUsers() {
        List<User> usersWithBorrowedBooks = Collections.singletonList(testUser);
        when(userRepository.findUsersWithBorrowedBooks()).thenReturn(usersWithBorrowedBooks);
        List<UserRecord> result = userService.findUsersBorrowedBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findUsersWithBorrowedBooks();
    }

    @Test
    void findUsersBorrowedBooks_shouldReturnEmptyList() {
        when(userRepository.findUsersWithBorrowedBooks()).thenReturn(Collections.emptyList());
        List<UserRecord> result = userService.findUsersBorrowedBooks();
        assertNull(result);
        verify(userRepository, times(1)).findUsersWithBorrowedBooks();
    }

    @Test
    void findNonTerminatedUsers_shouldReturnUsers() {
        List<User> usersWithBorrowedBooks = Collections.singletonList(testUser);
        when(userRepository.findUsersNotBorrowing()).thenReturn(usersWithBorrowedBooks);
        List<UserRecord> result = userService.findNonTerminatedUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findUsersNotBorrowing();
    }

    @Test
    void findNonTerminatedUsers_shouldReturnEmptyList() {
        when(userRepository.findUsersNotBorrowing()).thenReturn(Collections.emptyList());
        List<UserRecord> result = userService.findNonTerminatedUsers();
        assertNull(result);
        verify(userRepository, times(1)).findUsersNotBorrowing();
    }

    @Test
    void getUsersBorrowedOnDate_shouldReturnUsers() {
        Borrowed request = new Borrowed();
        request.setBorrower(testUser);
        List<Borrowed> responseList = Collections.singletonList(request);
        when(borrowedRepository.findByBorrowedFromEquals(any(LocalDate.class))).thenReturn(responseList);
        List<UserRecord> result = userService.getUsersBorrowedOnDate(LocalDate.now());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(borrowedRepository, times(1)).findByBorrowedFromEquals(any(LocalDate.class));
    }

    @Test
    void getUsersBorrowedOnDate_shouldReturnEmptyList() {
        when(borrowedRepository.findByBorrowedFromEquals(any(LocalDate.class))).thenReturn(Collections.emptyList());
        List<UserRecord> result = userService.getUsersBorrowedOnDate(LocalDate.now());
        assertNull(result);
        verify(borrowedRepository, times(1)).findByBorrowedFromEquals(any(LocalDate.class));
    }


}
