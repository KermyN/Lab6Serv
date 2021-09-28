package com.Kermy.Lab6.Exceptions;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String badCommand){
        System.out.println("Такой команды \""+badCommand+"\" нет.");
    }
}
