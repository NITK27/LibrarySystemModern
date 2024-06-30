package com.librarysystemapi.library_system_api;
import com.librarysystempackage.*;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class HelloWorldController {
    Library objLibrary = new Library();
    
    @GetMapping("/HomePage")
    public String Homepage(){
        return "Welcome, please Login";

    }
    

    @GetMapping("/AvailableBooks")
    public ArrayList <String> getAvailableBooks() {
        
         return objLibrary.getAvailableBooksName();
    }

    @GetMapping("/Bookname")
    public String returnBookName(String bookid){
       
       Book book = objLibrary.GetBookById(bookid);
       if (book != null) {
        return book.name; 
    } else {
        return "Book not found";
    }

    }

    // @PostMapping("/Login")
    // public String Login(@RequestBody int readerid , @RequestBody String password){
    //     Reader reader = objLibrary.Login(readerid , password);

    //     if (reader != null) {
    //         return "Login Successful";
            
    //     } else {
    //         return "Login Unsuccessful, Try Again";   
    //     }
    // }

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }

    @PostMapping("/Login")
    public String Login(@RequestBody LoginCredential objLogin){
        Reader reader = objLibrary.Login( objLogin.Userid , objLogin.Password);

        if (reader != null) {
            return "Login Successful";
            
        } else {
            return "Login Unsuccessful, Try Again";   
        }
    }

    @PostMapping("/Book")
    public int AddBook(@RequestBody Book book ){
        int id = objLibrary.AddBook(book.name , book.quantity, book.type);
        System.out.println("Received Book: " + book.name + ", " + book.quantity + ", " + book.type);
        return id;

    }
    @PutMapping("/Book")
    public String UpdateBook( @RequestBody Book book){
         return objLibrary.UpdateBook(book.Id, book.name , book.quantity, book.type);
        
    }
    
    @GetMapping("/Borrow")
    public String BorrowBook( @RequestParam int readerId, @RequestParam String bookId){
         Reader reader = objLibrary.GetReaderById(readerId);
         Book book = objLibrary.GetBookById(bookId);

         if (reader == null) {
            return "Reader with ID " + readerId + " not found.";
        }
        
        if (book == null) {
            return "Book with ID " + bookId + " not found.";
        }

         return  objLibrary.borrowBook(reader, book);
        
    }

    @GetMapping("/Return")
    public String ReturnBook( @RequestParam int readerId, @RequestParam String bookId){
         Reader reader = objLibrary.GetReaderById(readerId);
         Book book = objLibrary.GetBookById(bookId);

         if (reader == null) {
            return "Reader with ID " + readerId + " not found.";
        }
        
        if (book == null) {
            return "Book with ID " + bookId + " not found.";
        }

         return  objLibrary.returnBook(reader, book);
        
    }
    
}





