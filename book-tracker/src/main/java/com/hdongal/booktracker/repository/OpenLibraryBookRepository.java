package com.hdongal.booktracker.repository;

import com.google.gson.Gson;

import com.hdongal.booktracker.exception.BookNotFoundException;
import com.hdongal.booktracker.exception.APIUnavailableException;
import com.hdongal.booktracker.model.Book;
import com.hdongal.booktracker.model.SearchResponse;
import com.hdongal.booktracker.model.SearchResult;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.IOException;

public class OpenLibraryBookRepository implements Repository<Book> {
    private final HttpClient httpClient;
    private final Gson gson;

    // Constructor injection: HttpClient is passed in, not created here
    public OpenLibraryBookRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.gson = new Gson();
    }

    @Override
    public SearchResult<Book> search(String query) throws APIUnavailableException {
        String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+") + "&limit=20";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 422 || response.statusCode() == 400) {
                throw new IllegalArgumentException("Search query was rejected by the API — try a longer or more specific term.");
            }

            if (response.statusCode() != 200) {
                throw new APIUnavailableException("Open Library API returned status " + response.statusCode());
            }

            SearchResponse searchResponse = gson.fromJson(response.body(), SearchResponse.class);
            return new SearchResult<>(searchResponse.getDocs(), searchResponse.getNumFound());

        } catch (IOException | InterruptedException e) {
            throw new APIUnavailableException("Failed to reach Open Library API", e);
        }
    }

    @Override
    public Book findByKey(String key) throws BookNotFoundException, APIUnavailableException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
