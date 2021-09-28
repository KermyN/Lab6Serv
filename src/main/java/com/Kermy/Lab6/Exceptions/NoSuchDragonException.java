package com.Kermy.Lab6.Exceptions;

public class NoSuchDragonException extends Exception {

    public NoSuchDragonException(long id){
        System.out.println("Дракон с id " + id + " не найден.");
    }

}
