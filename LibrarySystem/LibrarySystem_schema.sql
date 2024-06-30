-- create database LibrarySystem;
use LibrarySystem;
select * from Books;
 select * from Readers;
 select * from ReaderBooks;

-- Books
CREATE TABLE Books (
    BookId INT NOT NULL auto_increment,
    BookName VARCHAR(100) NOT NULL,
    Quantity INT NOT NULL,
    Type VARCHAR(50),
    PRIMARY KEY (BookId)
);

-- Readers
CREATE TABLE Readers (
    ReaderId INT NOT NULL,
    ReaderName VARCHAR(100) NOT NULL,
    password VARCHAR(100),
    Type VARCHAR(50),
    PRIMARY KEY (ReaderId)
);

-- creating table ReaderBooks
CREATE TABLE ReaderBooks (
    ReaderId int NOT NULL,
    BookId int NOT NULL,
    PRIMARY KEY (ReaderId, BookId),
    FOREIGN KEY (ReaderId) REFERENCES Readers(ReaderId),
    FOREIGN KEY (BookId) REFERENCES Books(BookId)
);

-- joining ReaderBooks, books and readers
CREATE VIEW ReaderBookDetails AS
SELECT

    Readers.ReaderName AS ReaderName,
    Books.BookName AS BookName
    
FROM
    Readers
INNER JOIN
    ReaderBooks ON Readers.ReaderId = ReaderBooks.ReaderId
INNER JOIN
    Books ON Books.BookId = ReaderBooks.BookId;

select * from ReaderBookDetails;



-- creating stored procedure for returning books
DELIMITER //
CREATE PROCEDURE SP_ReturnBook(
    IN reader_Id INT,
    IN book_Id VARCHAR(100)
   
)
BEGIN
	DELETE FROM ReaderBooks WHERE ReaderId = reader_Id AND BookId = book_Id;
    UPDATE Books SET Quantity = Quantity + 1 WHERE BookId = book_Id;
    
END //

DELIMITER ;
DELIMITER //


-- procedure for BorrowBook
CREATE PROCEDURE SP_BorrowBook(
    IN readerId INT,
    IN Id VARCHAR(100)
   
)
BEGIN
	INSERT INTO ReaderBooks (ReaderId , BookId) 
    VALUES (readerId , Id);
    UPDATE Books SET Quantity = Quantity - 1 WHERE BookId = Id;
    
END //

DELIMITER ;
SET SQL_SAFE_UPDATES = 0;

-- procedure for add book
DELIMITER //

CREATE PROCEDURE AddBook(IN bookname VARCHAR(255), IN quantity INT, IN type VARCHAR(255), OUT bookId INT)
BEGIN
    INSERT INTO Books (BookName, Quantity, Type) VALUES (bookname, quantity, type);
    SET bookId = LAST_INSERT_ID();
END //

DELIMITER ;



