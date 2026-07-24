package com.restBook.RestBookAPI.controller;

import com.restBook.RestBookAPI.entities.Book;
import com.restBook.RestBookAPI.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/booksServices")
public class BookController {

    @Autowired
    private BookServices bookServices;

    // *************************************************
    //addBook Controller
    @PostMapping("/addBooks")
    public ResponseEntity<Book> addBook(@RequestBody Book book)
    {
        Book b=null;
        try
        {
            b=bookServices.addBook(book);
            System.out.println(book);
            return ResponseEntity.of(Optional.of(b));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // *************************************************

    //get AllBooks

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks()
    {
        List<Book> allBooks = bookServices.getAllBooks();
        if(allBooks.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(allBooks);
    }

    // *************************************************

    //getBookById

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id")int id)
    {
        Book book = bookServices.getBookById(id);
        if(book == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    }

    // *************************************************

    //Delete Book By id

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId")int bookId)
    {
        try{
            this.bookServices.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book,@PathVariable("bookId") int bookId)
    {
        try {
            this.bookServices.updateBook(book,bookId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

//Class Loaded
//   ↓
//Bean Definition Created
//   ↓
//Object Instantiated
//   ↓
//Dependencies Injected
//   ↓
//@PostConstruct
//   ↓
//Bean Ready
//   ↓
//Bean Used
//   ↓
//@PreDestroy
//   ↓
//Bean Destroyed
