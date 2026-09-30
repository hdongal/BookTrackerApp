package com.hdongal.booktracker.service;

import com.hdongal.booktracker.exception.BookNotFoundException;
import com.hdongal.booktracker.exception.APIUnavailableException;
import com.hdongal.booktracker.model.Book;
import com.hdongal.booktracker.model.SearchResult;
import com.hdongal.booktracker.repository.Repository;

import java.util.Comparator;

public class BookServiceImpl implements BookService {
    private final Repository<Book> bookRepository;

    public BookServiceImpl(Repository<Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public SearchResult<Book> searchBooks(String query) throws APIUnavailableException {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }

        SearchResult<Book> results = bookRepository.search(query);

        results.getBooks().sort(Comparator.comparing(
                Book::getFirstPublishYear,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));
        return results;
    }

    @Override
    public Book getBookByKey(String key) throws BookNotFoundException, APIUnavailableException {
        return bookRepository.findByKey(key);
    }
}
