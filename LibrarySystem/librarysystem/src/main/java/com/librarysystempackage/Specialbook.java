package com.librarysystempackage;


 public class Specialbook extends Book {
    public String type = "SpecialBook";

    public Specialbook(String name, String Id, int quantity, String type) {
        super(name, Id, quantity,type);
        
        
    }

    public boolean ValidateBorrow(Reader reader) {
        if (reader.ValidateReader()== 2) {
            return true;
            
        }
        else {
            System.out.println("you are not a VIP customer, hence you can't borrow this book");
            TransactionPage.outcome.setText("you are not a VIP customer, hence you can't borrow this book");
            return false;
        }
    }

    public String getType(){
        return "SpecialBook";
    }
        
}
