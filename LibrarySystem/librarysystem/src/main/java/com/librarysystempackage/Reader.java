package com.librarysystempackage;
import java.util.ArrayList;

public class Reader{
    String ReaderName ;
    int Id;
    int Balance ;
    String Password ;
    ArrayList<Book> BorrowedBooks = new ArrayList<Book>();
    
    public Reader(int Id,String ReaderName,String Password) {
        this.ReaderName=ReaderName ;
        this.Id=Id;
        this.Password = Password;
        
    }

    public ArrayList<String> getBorrowedBooks(){
        ArrayList<String> BorrowedBooksName = new ArrayList<String>();
        for (Book borrowedbook : BorrowedBooks) {
            
            BorrowedBooksName.add(borrowedbook.name);
            
        }
        return BorrowedBooksName;

    }

    public int ValidateReader(){
       
            return 1;
        
    }
    


}