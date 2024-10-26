package com.example.book_store;

import org.junit.jupiter.api.Test;
import com.example.book_store.model.Book;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookProperties() {
        Book book = new Book("Sample Book", "Author", 15.99, "http://image.url");

        assertEquals("Sample Book", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(15.99, book.getPrice());
        assertEquals("http://image.url", book.getImgUrl());
    }
}
