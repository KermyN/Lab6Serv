package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.Exceptions.NoSuchCommandException;
import com.Kermy.Lab6.Exceptions.WrongArgumentsNumberException;

import java.util.HashMap;
import java.util.Map;

/**
 * шаблон Команда.
 * вызывает команды
 */
public class CommandsInvoker {

    private static HashMap<String, Command> registeredCommands = new HashMap<>();

    private static CommandsInvoker instance;


    public static CommandsInvoker getInstance(){
        if (instance == null) {
            instance = new CommandsInvoker();
        }
        return instance;
    }
    private CommandsInvoker(){
    }


    public void register(String commandName, Command command){
        registeredCommands.put(commandName, command);
    }

    public void register(Map<String, Command> commandMap){
        registeredCommands.putAll(commandMap);
    }

    public Command validateCommand(String commandName, String[] arguments) throws NoSuchCommandException,
            WrongArgumentsNumberException {
        if(registeredCommands.containsKey(commandName)){
            Command command = registeredCommands.get(commandName);
            int requiredArgs = command.getNumberOfRequiredArgs();
            if(requiredArgs == arguments.length){
                command.setArgs(arguments);
                return registeredCommands.get(commandName);
            }else{
                throw new WrongArgumentsNumberException(requiredArgs, arguments.length);
            }
        }else {
            throw new NoSuchCommandException(commandName);
        }
    }

    public HashMap<String, Command> getMapOfRegisteredCommands(){
        return registeredCommands;
    }
}