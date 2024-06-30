package com.librarysystempackage;
public class VIP extends Reader{

    public VIP(int Id, String ReaderName, String Password) {
        super(Id, ReaderName, Password);
    }
    
    public int ValidateReader(){
        
        return 2;

    }
    
}
