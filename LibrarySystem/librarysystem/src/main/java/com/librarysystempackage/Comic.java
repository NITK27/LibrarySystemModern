package com.librarysystempackage;



public class Comic extends Book{
    public String type = "Comic";

    public Comic(String name, String Id, int quantity,String type) {
        super(name, Id, quantity,type);
        
        
    }
    public boolean ValidateBorrow(Reader reader) {
        return true;
    }



    
}
