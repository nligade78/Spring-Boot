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
public class BookController {

    @Autowired
    private BookServices bookServices;
//    @RequestMapping(value = "/books", method = RequestMethod.GET)
//    public String getBooks()
//    {
//        return "This is testing book";
//    }

    //gate all data handler
//    @GetMapping("/data")
//    public List<Book> getAllBook()
//    {
//        return this.bookServices.getBookList();
//    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks()
    {
       List<Book> list= bookServices.getBookList();
       if(list.size()<=0)
       {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(list);

    }

//    @GetMapping("/book/{id}")
//    public Book getBookById(@PathVariable("id") int id)
//    {
//        return this.bookServices.getBookById(id);
//    }

    //gate data by id handler
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id)
    {
        Book book=bookServices.getBookById(id);
        if(book==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    }

    //insert a new data handler
//    @PostMapping("/books")
//    public Book addBook(@RequestBody Book book)
//    {
//       Book b= this.bookServices.addBook(book);
//        return b;
//    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book)
    {
        Book b=null;
        try{
            b=this.bookServices.addBook(book);
            System.out.println(book);
            return ResponseEntity.of(Optional.of(b));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //delete data by id handler
//    @DeleteMapping("/book/{bookId}")
//    public void deleteBook(@PathVariable("bookId") int bookId)
//    {
//        this.bookServices.deleteBook(bookId);
//        System.out.println("Book Deleted");
//
//    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId)
    {
       try {
           this.bookServices.deleteBook(bookId);
           return ResponseEntity.ok().build();
       }
       catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }

    }
    //update book handler
//    @PutMapping("/book/{bookId}")
//    public Book updateBook(@RequestBody Book book,@PathVariable("bookId") int bookId)
//    {
//        this.bookServices.updateBook(book,bookId);
//       return book;
//    }

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
