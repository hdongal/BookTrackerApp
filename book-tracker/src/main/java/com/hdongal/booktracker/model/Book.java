package com.hdongal.booktracker.model;

import java.util.List;

public class Book {
    private String key;
    private String title;
    private List<String> author_name;
    private Integer first_publish_year;
    private Integer edition_count;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorName() {
        return author_name;
    }

    public Integer getFirstPublishYear() {
        return first_publish_year;
    }

    public Integer getEditionCount() {
        return edition_count;
    }

    @Override
    public String toString() {
        String author = (author_name != null && !author_name.isEmpty()) ? author_name.getFirst() : "Unknown author";
        String year = (first_publish_year != null) ? first_publish_year.toString() : "year unknown";
        return title + " by " + author + " (" + year + ")";
    }
}
