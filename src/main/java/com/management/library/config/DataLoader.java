package com.management.library.config;

import com.management.library.entity.Book;
import com.management.library.entity.Borrowed;
import com.management.library.entity.User;
import com.management.library.repository.BookRepository;
import com.management.library.repository.BorrowedRepository;
import com.management.library.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowedRepository borrowedRepository;

    private final String CLASS_NAME = this.getClass().getName();

    @Autowired
    public DataLoader(BookRepository bookRepository, UserRepository userRepository, BorrowedRepository borrowedRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowedRepository = borrowedRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (isDatabaseEmpty()) {
            loadBooks();
            loadUsers();
            loadBorrowed();
            log.info("Data loaded successfully.");
        } else {
            log.info("Database is not empty. Skipping data loading.");
        }
    }


    private boolean isDatabaseEmpty() {
        return bookRepository.count() == 0 && userRepository.count() == 0 && borrowedRepository.count() == 0;
    }

    private void loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classloader.getResourceAsStream("books.csv");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.values().length > 0 && StringUtils.isNotEmpty(csvRecord.values()[0])) {
                    Book entity = new Book();
                    entity.setAuthor(csvRecord.get("Author"));
                    entity.setTitle(csvRecord.get("Title"));
                    entity.setGenre(csvRecord.get("Genre"));
                    entity.setPublisher(csvRecord.get("Publisher"));
                    books.add(entity);
                }

            }

            bookRepository.saveAll(books);
        } catch (IOException e) {
            log.error("== error in " + CLASS_NAME + " loadBooks ==" + e);
        }
    }

    private void loadUsers() {
        List<User> users = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classloader.getResourceAsStream("user.csv");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                User entity = new User();
                entity.setName(csvRecord.get("Name"));
                entity.setFirstName(csvRecord.get("First name"));
                entity.setMemberSince(LocalDate.parse(csvRecord.get("Member since"), FORMATTER));
                if (!StringUtils.isEmpty(csvRecord.get("Member till"))) {
                    entity.setMemberTill(LocalDate.parse(csvRecord.get("Member till"), FORMATTER));
                }
                entity.setGender(csvRecord.get("Gender"));
                users.add(entity);
            }

            userRepository.saveAll(users);
        } catch (IOException e) {
            log.error("== error in " + CLASS_NAME + " loadUsers ==" + e);
        }
    }

    private void loadBorrowed() {
        List<Borrowed> list = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classloader.getResourceAsStream("borrowed.csv");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                Borrowed entity = new Borrowed();
                Optional<Book> book = bookRepository.findByTitle(csvRecord.get("Book"));
                book.ifPresent(entity::setBook);
                String[] borrower = csvRecord.get("Borrower").split(",");
                if (borrower.length != 0) {
                    Optional<User> user = userRepository.findByNameAndFirstName(borrower[0], borrower[1]);
                    user.ifPresent(entity::setBorrower);
                }

                entity.setBorrowedFrom(LocalDate.parse(csvRecord.get("borrowed from"), FORMATTER));
                entity.setBorrowedTo(LocalDate.parse(csvRecord.get("borrowed to"), FORMATTER));
                list.add(entity);
            }

            borrowedRepository.saveAll(list);
        } catch (IOException e) {
            log.error("== error in " + CLASS_NAME + " loadBorrowed ==" + e);
        }
    }

}