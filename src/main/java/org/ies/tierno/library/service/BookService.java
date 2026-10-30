package org.ies.tierno.library.service;

import org.ies.tierno.library.model.Book;
import org.ies.tierno.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    // No book has id 0: ids start at 1.
    private static final long NO_ID = 0;

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        List<Book> all = repository.findAll();
        if (author == null) {
            return all;
        }
        return all.stream()
                .filter(book -> book.author().equalsIgnoreCase(author))
                .toList();
    }

    public Book findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(String isbn, String title, String author,
                       int year, BigDecimal purchasePrice) {
        checkIsbnIsAvailable(isbn, NO_ID);
        Book book = new Book(repository.nextId(), isbn, title, author,
                year, purchasePrice, LocalDate.now());
        repository.save(book);
        return book;
    }

    public Book replace(long id, String isbn, String title, String author,
                        int year, BigDecimal purchasePrice) {
        Book current = findById(id);
        checkIsbnIsAvailable(isbn, id);
        Book book = new Book(id, isbn, title, author,
                year, purchasePrice, current.registrationDate());
        repository.save(book);
        return book;
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new BookNotFoundException(id);
        }
    }

    private void checkIsbnIsAvailable(String isbn, long ignoredId) {
        boolean duplicate = repository.findAll().stream()
                .anyMatch(b -> b.id() != ignoredId && b.isbn().equals(isbn));
        if (duplicate) {
            throw new DuplicateIsbnException(isbn);
        }
    }
}