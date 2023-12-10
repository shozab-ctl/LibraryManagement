package com.management.library.service.impl;

import com.management.library.entity.Borrowed;
import com.management.library.entity.User;
import com.management.library.model.UserRecord;
import com.management.library.repository.BorrowedRepository;
import com.management.library.repository.UserRepository;
import com.management.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.management.library.constants.Constant.NO_DATA;
import static com.management.library.constants.Constant.SUCCESS_DATA;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final String CLASS_NAME = this.getClass().getName();

    @Autowired
    private UserRepository repository;

    @Autowired
    private BorrowedRepository borrowedRepository;

    private final Function<User, UserRecord> convertEntityToRecord =
            user -> new UserRecord(user.getName(), user.getFirstName(), user.getMemberSince(), user.getMemberTill(), user.getGender());
    public Function<List<User>, List<UserRecord>> listConvert = userList -> userList.stream()
            .map(convertEntityToRecord)
            .collect(Collectors.toList());

    @Override
    public List<UserRecord> findUsersBorrowedBooks() {
        List<User> users = repository.findUsersWithBorrowedBooks();
        if (!users.isEmpty()) {
            log.info(CLASS_NAME + "== " + SUCCESS_DATA + " ==");
            return listConvert.apply(users);

        } else {
            log.info(CLASS_NAME + "== " + NO_DATA + " ==");
        }
        return null;

    }

    @Override
    public List<UserRecord> findNonTerminatedUsers() {
        List<User> users = repository.findUsersNotBorrowing();
        if (!users.isEmpty()) {
            log.info(CLASS_NAME + "== " + SUCCESS_DATA + " ==");
            return listConvert.apply(users);

        } else {
            log.info(CLASS_NAME + "== " + NO_DATA + " ==");
        }
        return null;
    }

    @Override
    public List<UserRecord> getUsersBorrowedOnDate(LocalDate date) {
        List<Borrowed> borrowd = borrowedRepository.findByBorrowedFromEquals(date);
        if (!borrowd.isEmpty()) {
            log.info(CLASS_NAME + "== " + SUCCESS_DATA + " ==");
            return borrowd.stream().map(borrowed -> new UserRecord(borrowed.getBorrower().getName(), borrowed.getBorrower().getFirstName(), borrowed.getBorrower().getMemberSince(), borrowed.getBorrower().getMemberTill(), borrowed.getBorrower().getGender())).collect(Collectors.toList());

        } else {
            log.info(CLASS_NAME + "== " + NO_DATA + " ==");
        }
        return null;
    }
}
