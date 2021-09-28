package com.Kermy.Lab6.commands;

import java.io.Serializable;

public abstract class Command implements Executable, Serializable {

    protected String[] args;

    public void clientInsertion(){
    }

    abstract public int getNumberOfRequiredArgs();
    public Command(String[] args){
        this.args = args;
    }

    public Command(){}

    public void setArgs(String[] args){
        this.args = args;
    }

    public String getDescription(){
        return "ленивый разработчик не написал описание команды";
    }
}
