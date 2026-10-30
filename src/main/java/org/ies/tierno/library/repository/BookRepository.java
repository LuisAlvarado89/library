package org.ies.tierno.library.repository;

import org.ies.tierno.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public long nextId() {
        return idSequence.getAndIncrement();
    }

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::id))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public void save(Book book) {
        books.put(book.id(), book);
    }

    public boolean deleteById(long id) {
        return books.remove(id) != null;
    }
}

