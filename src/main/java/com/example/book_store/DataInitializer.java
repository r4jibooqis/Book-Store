package com.example.book_store;

import com.example.book_store.model.Book;
import com.example.book_store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty before adding initial data
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Dark Entries", "Robert Aickman", 10.99,
                    "https://m.media-amazon.com/images/I/91tBhzQYTeL.jpg"));
            bookRepository.save(new Book("Once Upon a Broken Heart", "Stephanie Garber", 8.99,
                    "https://www.outland.no/media/catalog/product/9/7/9781250268396__17c27ecd6d2384ef11fe989a188ac87d.jpg"));
            bookRepository.save(new Book("Wider than the Sky", "Katherine Rothschild", 12.99,
                    "https://m.media-amazon.com/images/I/81SXVj0ElwL._AC_UF1000,1000_QL80_.jpg"));
            bookRepository.save(new Book("The Teacher ", "Katerina Diamond", 11.99,
                    "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1693401496i/195967140.jpg"));
            bookRepository.save(new Book("Winter Tide", "Ruthanna Emrys", 14.99,
                    "https://m.media-amazon.com/images/I/A1BQspz2imL._AC_UF1000,1000_QL80_.jpg"));
        }
    }
}
