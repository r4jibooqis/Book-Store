package com.example.book_store;

import com.example.book_store.model.Book;
import com.example.book_store.repository.BookRepository;
import com.example.book_store.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book("Book One", "Author One", 10.0, "url1");
        Book book2 = new Book("Book Two", "Author Two", 15.0, "url2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        Book book = new Book("Book One", "Author One", 10.0, "url1");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);
        assertNotNull(foundBook);
        assertEquals("Book One", foundBook.getTitle());
    }

    @Test
    public void testCreateBook() {
        Book book = new Book("Book Three", "Author Three", 20.0, "url3");
        when(bookRepository.save(book)).thenReturn(book);

        Book createdBook = bookService.createBook(book);
        assertEquals("Book Three", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book("Book One", "Author One", 10.0, "url1");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        book.setPrice(12.0);

        when(bookRepository.save(book)).thenReturn(book);
        Book updatedBook = bookService.updateBook(1L, book);

        assertEquals(12.0, updatedBook.getPrice());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
