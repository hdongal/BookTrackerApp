package com.hdongal.booktracker.service;

import com.hdongal.booktracker.exception.BookNotFoundException;
import com.hdongal.booktracker.exception.APIUnavailableException;
import com.hdongal.booktracker.model.Book;
import com.hdongal.booktracker.model.SearchResult;

public interface BookService {
    SearchResult<Book> searchBooks(String query) throws APIUnavailableException;
    Book getBookByKey(String key) throws BookNotFoundException, APIUnavailableException;
}
