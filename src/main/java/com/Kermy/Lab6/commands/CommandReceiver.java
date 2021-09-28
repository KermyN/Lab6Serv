package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.collection.Dragons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class CommandReceiver implements Serializable {

    private Dragons collection;


    /**
     * @return the collection
     */
    public Dragons getCollection() {
        return collection;
    }

    /**
     * @param collection
     */
    public CommandReceiver(Dragons collection){
        this.collection = collection;
    }
    public CommandReceiver(){
        this.collection = new Dragons();
    }

    public void setCollection(Dragons collection){
        this.collection = collection;
    }


    /**
     * Print help.
     */
    public String printHelp(HashMap<String, Command> registeredCommands){
        //для сортировки
        Map<String, Command> treeMap = new TreeMap<>(registeredCommands);
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Command> entry : treeMap.entrySet()){
            builder.append(entry.getKey()).append(" : ").append(entry.getValue().getDescription()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Exit.
     */
    public void exit(){
        System.exit(0);
    }
}