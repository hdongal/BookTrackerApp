package com.hdongal.booktracker.model;

import java.util.List;

public class SearchResponse {
    private int numFound;
    private List<Book> docs;

    public int getNumFound() {
        return numFound;
    }

    public List<Book> getDocs() {
        return docs;
    }
}
