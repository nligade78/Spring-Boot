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

    //gate all data handler
    @GetMapping("/data")
    public List<Book> getAllBook()
    {
        return this.bookServices.getBookList();
    }

    //gate data by id handler
    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") int id)
    {
        return this.bookServices.getBookById(id);
    }

    //insert a new data handler
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book)
    {
       Book b= this.bookServices.addBook(book);
        return b;
    }

    //delete data by id handler
    @DeleteMapping("/book/{bookId}")
    public void deleteBook(@PathVariable("bookId") int bookId)
    {
        this.bookServices.deleteBook(bookId);
        System.out.println("Book Deleted");

    }
    //update book handler
    @PutMapping("/book/{bookId}")
    public Book updateBook(@RequestBody Book book,@PathVariable("bookId") int bookId)
    {
        this.bookServices.updateBook(book,bookId);
       return book;
    }

}
