package com.hdongal.booktracker;

import com.hdongal.booktracker.exception.APIUnavailableException;
import com.hdongal.booktracker.model.Book;
import com.hdongal.booktracker.model.SearchResult;
import com.hdongal.booktracker.repository.OpenLibraryBookRepository;
import com.hdongal.booktracker.repository.Repository;
import com.hdongal.booktracker.service.BookService;
import com.hdongal.booktracker.service.BookServiceImpl;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        Repository<Book> bookRepository = new OpenLibraryBookRepository(httpClient);
        BookService bookService = new BookServiceImpl(bookRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a book title to search, or 'quit' to exit.");

        while (true) {
            System.out.print("\nSearch: ");
            String query = scanner.nextLine();

            if (query.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye");
                break;
            }

            try {
                SearchResult<Book> result = bookService.searchBooks(query);
                List<Book> books = result.getBooks();

                if (books.isEmpty()) {
                    System.out.println("No book found for \"" + query + "\".");
                } else {
                    System.out.println("Found " + books.size() + " of " + result.getTotalFound() + " total results");
                    for (int i = 0; i < books.size(); i++) {
                        System.out.println((i + 1) + ". " + books.get(i));
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (APIUnavailableException e) {
                System.out.println("Sorry, the book service is currently unavailable. Please try again later.");
                System.out.println("(Details: " + e.getMessage() + ")");

            }
        }
        scanner.close();
    }
}