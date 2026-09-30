# Book Tracker

A console application for searching books via the Open Library API, built to practice
core object-oriented and REST/API integration concepts in Java — specifically interfaces,
generics, dependency injection, and structured exception handling.

## What it does

Search for books by title and see results (title, author, publish year), pulled live
from the Open Library API. Handles no-results, invalid input, and API failures gracefully
without crashing.

## Architecture

The project follows a layered design with the Repository pattern:

Main → BookService (interface) → Repository<T> (interface) → OpenLibraryBookRepository (implementation)

- **Repository<T>**: a generic interface abstracting "how to fetch data," with no
  knowledge of HTTP or JSON. `OpenLibraryBookRepository` is the concrete implementation
  that talks to the Open Library REST API and deserializes JSON responses into `Book`
  objects using Gson.
- **BookService**: depends only on the `Repository<T>` interface, not the concrete
  implementation (constructor-based dependency injection). Contains business logic
  (e.g. sorting results by publish year).
- **Custom exceptions**: `APIUnavailableException` and `BookNotFoundException`
  distinguish between different failure modes (network/service failure vs. a specific
  book not existing vs. invalid user input), so each is handled and messaged
  appropriately rather than caught generically.

This structure means the data source could be swapped (e.g. for Google Books API, or a
mock repository for testing) without changing the service layer or `Main` at all.

## Tech stack
- Java 26
- Maven
- Gson (JSON serialization/deserialization)
- Java 11+ built-in `HttpClient` (no external HTTP library)
- Open Library REST API

## Concepts demonstrated
- Interfaces and abstraction (Repository pattern)
- Generics (`Repository<T>`, `SearchResult<T>`)
- Dependency injection (constructor injection, program-to-interface)
- Custom checked exceptions and differentiated error handling
- REST API consumption and JSON (de)serialization

## Known limitations
- Results are capped at 20 per search (Open Library API's `limit` parameter); no
  pagination beyond the first page
- `findByKey()` (fetch a single book by its Open Library key) is defined in the
  interface but not yet implemented

## Running it
1. Clone the repo
2. `mvn compile exec:java` 
   (or run `Main.java` directly from your IDE)
3. Type a search term, or `quit` to exit

## Example
```
Search: harry potter
Found 20 of 4063 total results
1. Harry Potter by Adele Books (2019)
2. ...
```