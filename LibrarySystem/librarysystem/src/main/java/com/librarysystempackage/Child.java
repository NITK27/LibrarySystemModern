
package com.librarysystempackage;



public class Child extends Reader{
    int parentId;
    

    public Child(int Id,String ReaderName,String Password){
        super(Id, ReaderName, Password);
        
    }

    public int ValidateReader(){
       
        return 3;
    
}
    
    
}
