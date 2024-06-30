package com.librarysystempackage;

public class Book {
    public String name;
    public String Id;
    public int quantity;
    public String type = "Book";
    // hello

    public Book(String name, String Id, int quantity, String type) {
        this.name = name;
        this.Id = Id;
        this.quantity = quantity;
        this.type = type;
    }
    public Book() {}

    

   public boolean ValidateBorrow(Reader reader){

    if(reader.ValidateReader()== 3){
        System.out.println("this is a child account, parent account authentication is required");
        TransactionPage.outcome.setText("This is a child account, hence this book cannot be borrowed.");
        return false;
    }
   
    else{return true;}
  
}


 public String toString() {
     return name; // Override toString() to return book title
 }
}




    

