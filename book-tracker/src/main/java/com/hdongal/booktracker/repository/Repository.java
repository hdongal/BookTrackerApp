package com.hdongal.booktracker.repository;

import com.hdongal.booktracker.exception.APIUnavailableException;
import com.hdongal.booktracker.exception.BookNotFoundException;
import com.hdongal.booktracker.model.SearchResult;

public interface Repository<T> {
    SearchResult<T> search(String query) throws APIUnavailableException;
    T findByKey(String key) throws BookNotFoundException, APIUnavailableException;
}
