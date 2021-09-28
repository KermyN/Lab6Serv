package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.app.SerializationManager;
import com.Kermy.Lab6.client.Client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The type Execute script command.
 */
public class ExecuteScriptCommand extends Command {

    /**
     * Instantiates a new Command.
     *
     * @param args
     */
    public ExecuteScriptCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public void clientInsertion() {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            System.out.println("Запуск скрипта " + args[0]);
            while ((line = reader.readLine()) != null) {
                Command c = Client.getCommandFromString(line);
                if (c != null) {
                    FieldsScanner scanner = FieldsScanner.getInstance();
                    scanner.configureScanner(new Scanner(reader));
                    c.clientInsertion();
                    scanner.configureScanner(new Scanner(System.in));
                    byte[] serializedCommand = SerializationManager.writeObject(c);
                    Client.sendOneByte();
                    Client.getSocket().getOutputStream().write(serializedCommand);
                    Client.getAnswer();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("капут");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (StackOverflowError e){
            System.out.println("Стэк переполнен!");
        }

    }

    @Override
    public String execute(CommandReceiver receiver) {
        return "script done";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
