package com.librarysystempackage;


import java.util.* ;
import java.sql.*;


     public class Library {
    
    
    public ArrayList<Reader> Readers = new ArrayList<Reader>(); 
    public ArrayList<Book> AvailableBooks = new ArrayList<Book>();
    public ArrayList<String> AvailableBooksName = new ArrayList<String>();
    
    public Scanner vce = new Scanner(System.in);
    
      
    public Library(){
        this.LoadBooks();
        this.LoadReaders(); 
    }

    public void PrintAvailableBooks(){
        Connection con = establish_connection_cloud();
        
        
        System.out.println("----------  Available Books -------------");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books ");

            while (rs.next()) {
                String BookName = rs.getString("BookName");
                String Quantity = rs.getString("Quantity");
   
                System.out.println(BookName + " , Quantity : " + Quantity);
                
            }
        System.out.println("------------------------------------------");
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    } 
              


    public  void DisplayReaders(){

        Connection con = establish_connection_cloud();

        System.out.println("---------- Transactions -------------");

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ReaderBookDetails ");

            while (rs.next()) {
                String BookName = rs.getString("BookName");
                String ReaderName = rs.getString("ReaderName");
   
                System.out.println(ReaderName + " has borrowed the book " + BookName);
                
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        System.out.println("------------------------------------------");
    }

    public Book GetBookById(String bookId){
        for (Book book : AvailableBooks) {
            if (book.Id.equals(bookId)){
                return book;
            }
        }
        return null;
           
    }

    public Reader GetReaderById(int readerId){
        for (Reader reader : Readers) {
            if (reader.Id == readerId)
            return reader;
        }
        return null;
    }

//     public Connection establish_connection(){

//         try {
//             String conectionString = "jdbc:mysql://localhost:3306/LibrarySystem";
//         String username = "root";
//         String password = "18991000";
//         Connection con = DriverManager.getConnection(conectionString, username, password);
//         return con;

//         } 
//      catch (Exception e) {
//         e.printStackTrace();
//         return null;
        
//     }
// }
    public Connection establish_connection_cloud(){

    try {
    String conectionString = "jdbc:mysql://mysql-librarysystem-server.mysql.database.azure.com:3306/LibrarySystem?useSSL=true";
    String username = "kshitijsingh";
    String password = "Goldline123#";
    Connection con = DriverManager.getConnection(conectionString, username, password);
    return con;

    } 

 catch (Exception e) {
    e.printStackTrace();
    return null;
    
}
}

    public void LoadBooks() {

        Connection con = establish_connection_cloud();
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");

            while (rs.next()) {
                String name = rs.getString("BookName");
                String Id = rs.getString("BookId");
                int quantity = rs.getInt("Quantity");
                String type = rs.getString("Type"); // Assuming there's a column for book type
                
                // Create book object based on type
                Book book;
                if (type.equals("SpecialBook")) {
                    book = new Specialbook(name, Id, quantity,type);
                } else if (type.equals("Comic")) {
                    book = new Comic(name, Id, quantity,type);
                } else {
                    book = new Book(name, Id, quantity,type);
                }
                AvailableBooks.add(book);
            }
            
        } catch (Exception e) {
            System.out.println("database connection could not be established");
        }

    }

    public  void LoadReaders(){

        Connection con = establish_connection_cloud();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Readers");

            while (rs.next()) {
                String ReaderName = rs.getString("ReaderName");
                int Id = rs.getInt("ReaderId");
                String Password = rs.getString("password");
                String type = rs.getString("Type"); // Assuming there's a column for book type
                
                // Create book object based on type
                Reader reader;
                if (type.equals("VIP")) {
                    reader = new VIP(Id,ReaderName,Password);
                } else if (type.equals("Child")) {
                    reader = new Child(Id, ReaderName, Password);
                } else {
                    reader = new Reader(Id, ReaderName, Password);
                }
                Readers.add(reader);
            }
            
        } catch (Exception e) {
            System.out.println("database connection could not be established");
            
        }

    }

    public void LoadReaderBooks(Reader reader){


        Connection con = establish_connection_cloud();
        try { 
            reader.BorrowedBooks.clear();
            String query = "SELECT BookId FROM ReaderBooks WHERE ReaderId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, reader.Id);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String bookId = rs.getString("BookId");
                Book book = GetBookById(bookId);
                
                reader.BorrowedBooks.add(book);
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }

    }

    public ArrayList <Book> getAvailableBooks(){
        return AvailableBooks;
    }

    public ArrayList <String> getAvailableBooksName(){
        for (Book book : AvailableBooks) {
            AvailableBooksName.add(book.name);
        }
        return AvailableBooksName;
    }

    public int CountReaderBooks(Reader reader , Book book){

        int BookCount = 0 ;

        Connection con = establish_connection_cloud();

        try {
            CallableStatement CountReaderBooks = con.prepareCall("{call CountReaderBooks(?, ?, ?)}");
            
            CountReaderBooks.setInt(1, reader.Id);
            CountReaderBooks.setString(2, book.Id);
            CountReaderBooks.registerOutParameter(3, Types.INTEGER);
    
            CountReaderBooks.execute();
    
            BookCount = CountReaderBooks.getInt(3);
            
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        return BookCount;

    }


    public String borrowBook(Reader reader, Book book) {
    
        Connection con = establish_connection_cloud();
        
        try {

                CallableStatement SP_BorrowBook = con.prepareCall("{call SP_BorrowBook(?, ?)}");
                SP_BorrowBook.setInt(1, reader.Id);
                SP_BorrowBook.setString(2, book.Id);
                SP_BorrowBook.execute();
                System.out.println(reader.ReaderName + " has borrowed the book: " + book.name);
                PrintAvailableBooks();
                DisplayReaders();
                return reader.ReaderName + " has borrowed the book " + book.name;

            }
            catch(SQLIntegrityConstraintViolationException e){
                return "You cannot borrow another book of the same type.";
            }
    
    
            catch (Exception e) {
            e.printStackTrace();
        }
        return reader.ReaderName + " has borrowed the book " + book.name;
    }
    
    
    public String returnBook(Reader reader, Book book){
        Connection con = establish_connection_cloud();
        reader.BorrowedBooks.clear();
        LoadReaderBooks(reader);

        try {
            
            CallableStatement SP_ReturnBook = con.prepareCall("{call SP_ReturnBook(?, ?)}");

            SP_ReturnBook.setInt(1, reader.Id);
            SP_ReturnBook.setString(2, book.Id);

            if (reader.BorrowedBooks.contains(book)) {

                SP_ReturnBook.execute();
                //reader.BorrowedBooks.remove(book);

                PrintAvailableBooks();
                DisplayReaders();  
                return reader.ReaderName + " has returned the book: " + book.name;  
            } 
            
            else {
                return "You haven't borrowed this book, hence you can't return it.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "error while returning book";
        }
        
    }

    public int AddBook(String bookname, int quantity, String type) {
        Connection con = establish_connection_cloud();
        int bookId = 0;
        String addBook = "{CALL AddBook(?, ?, ?, ?)}";
    
        try {
            CallableStatement cstmt = con.prepareCall(addBook);
            cstmt.setString(1, bookname);
            cstmt.setInt(2, quantity);
            cstmt.setString(3, type);
            cstmt.registerOutParameter(4, Types.INTEGER);
    
            cstmt.execute();
    
            bookId = cstmt.getInt(4);
        } catch (Exception e) {
            System.out.println("Error adding book to the database: " + e.getMessage());
        }
    
        return bookId;
    }
    
    
    public String UpdateBook(String bookid, String bookname , int quantity , String type){
        Connection con = establish_connection_cloud();
        int rowsAffected = 0;
        String updateQuery = "UPDATE Books SET BookName = ?, Quantity = ?, Type = ? WHERE BookId = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, bookname);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, type);
            pstmt.setString(4, bookid );

            rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Book (" + bookname + ") has been updated";
            } else {
                return "No book found with BookId: " + bookid;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating book: " + e.getMessage();
        }
    
    }


    public  void DoTransaction(Reader reader){

    System.out.println("Enter Book Id:");
    
    String BookId = vce.next();
    // Get/Convert Book 
    Book objBook = GetBookById(BookId);

    

     System.out.println("Do you want to borrow book or return book?");
     String choice = vce.next().toLowerCase();

     if(choice.equals("b") ){
        boolean y = objBook.ValidateBorrow(reader);

        if (y == false) {
            return;
        }
          borrowBook(reader,objBook);}

        else {
         
             returnBook(reader,objBook);
    
         return ;
        }
    }


    public Reader Login(int ReaderId, String password){
    
    Reader objReader = GetReaderById(ReaderId);
    if (objReader.Password.equals(password)) {
        System.out.println("Login Successful");
        return objReader;
           
    }
    else{
        System.out.println("Wrong Password, try again");
        return null;
    }   

 }
}
     



