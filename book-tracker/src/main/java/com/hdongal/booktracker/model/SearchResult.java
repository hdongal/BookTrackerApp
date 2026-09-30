package com.hdongal.booktracker.model;

import java.util.List;

public class SearchResult<T> {
    private final List<T> books;
    private final int totalFound;

    public SearchResult(List<T> books, int totalFound) {
        this.books = books;
        this.totalFound = totalFound;
    }

    public List<T> getBooks() {
        return books;
    }

    public int getTotalFound() {
        return totalFound;
    }
}
