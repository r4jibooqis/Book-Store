package com.example.book_store;

import com.example.book_store.controller.BookController;
import com.example.book_store.model.Book;
import com.example.book_store.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        // Mocking the service layer
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(
                new Book("Test Book 1", "Author 1", 10.0, ""),
                new Book("Test Book 2", "Author 2", 20.0, "")));

        // Performing GET request
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$[1].title").value("Test Book 2"));

        verify(bookService, times(1)).getAllBooks(); // Verify that the service method was called
    }

    @Test
    void testGetBookById() throws Exception {
        // Mocking the service layer
        Book book = new Book("Test Book", "Author", 15.0, "");
        when(bookService.getBookById(1L)).thenReturn(book);

        // Performing GET request
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookById(1L); // Verify that the service method was called
    }

    @Test
    void testCreateBook() throws Exception {
        // Mocking the service layer
        Book book = new Book("New Book", "Author", 25.0, "");
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        // Performing POST request
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Book\",\"author\":\"Author\",\"price\":25.0,\"imgUrl\":\"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"));

        verify(bookService, times(1)).createBook(any(Book.class)); // Verify that the service method was called
    }

    @Test
    void testUpdateBook() throws Exception {
        // Mocking the service layer
        Book updatedBook = new Book("Updated Book", "Updated Author", 30.0, "");
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        // Performing PUT request
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Book\",\"author\":\"Updated Author\",\"price\":30.0,\"imgUrl\":\"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Book"));

        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class)); // Verify that the service method was called
    }

    @Test
    void testDeleteBook() throws Exception {
        // Performing DELETE request
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L); // Verify that the service method was called
    }
}
