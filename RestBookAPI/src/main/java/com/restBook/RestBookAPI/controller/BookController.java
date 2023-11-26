package com.restBook.RestBookAPI.controller;

import com.restBook.RestBookAPI.entities.Book;
import com.restBook.RestBookAPI.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookServices bookServices;
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String getBooks()
    {
        return "This is testing book";
    }

    @GetMapping("/data")
    public List<Book> getAllBook()
    {
        return this.bookServices.getBookList();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") int id)
    {
        return this.bookServices.getBookById(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book)
    {
       Book b= this.bookServices.addBook(book);
        return b;
    }
}
