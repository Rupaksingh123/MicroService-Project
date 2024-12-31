package com.Icwd.user.service.exceptions;

public class ResouceNotFoundException extends RuntimeException{

    //extra properties the we want to add
    public ResouceNotFoundException(){
        super("Resource Not Found on server !! ");
    }

    public ResouceNotFoundException(String message){
        super(message);
    }
}
