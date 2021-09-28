package com.Kermy.Lab6.client;
import com.Kermy.Lab6.Exceptions.NoSuchCommandException;
import com.Kermy.Lab6.Exceptions.WrongArgumentsNumberException;
import com.Kermy.Lab6.app.SerializationManager;
import com.Kermy.Lab6.commands.*;
import com.Kermy.Lab6.server.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static CommandsInvoker invoker;
    private static Socket socket;
    public static final int BUFFER_SIZE = 4096;
    private static boolean notExit = true;
    private static final BufferedReader systemIn
            = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    private static final Scanner scanner = new Scanner(System.in);
    private final String host;
    private final int port;
    private boolean once = true;
    public void run() {

        registerCommands();
        connect();
        while (notExit) {
            handshake();
        }
        scanner.close();
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public static void sendOneByte() throws IOException {
        byte[] b = new byte[1];
        b[0] = (byte) 127;
        socket.getOutputStream().write(b);
    }

    public void handshake(){
        if(socket==null) {
            System.out.println("socket==null");
            return;
        }
        try{
            Command command = scanCommandFromConsole();
            if(command instanceof ExitCommand) {
                notExit = false;
            }
            sendOneByte();
            sendCommand(command);
            getAnswer();
        } catch (IOException e) {
            if(notExit){
                System.out.println("Потеря соединения");
                connect();
            }
        }catch (ClassNotFoundException e){
            System.out.println("Ошибка при сериализации");
        }
    }

    private Command scanCommandFromConsole() throws IOException {
        Command command = null;
        String line;
        do{
            if((line = systemIn.readLine()) != null){
                command = getCommandFromString(line);
            }
        }while(command==null);
        command.clientInsertion();
        return command;
    }

    public static void getAnswer() throws IOException, ClassNotFoundException {
        byte[] buff = new byte[BUFFER_SIZE];
        int got = socket.getInputStream().read(buff);
        if(got>0){
            Response r = SerializationManager.readObject(buff);
            System.out.println(r.getAnswer());
        }
    }
    public static void sendCommand(Command command) throws IOException {
        byte[] serializedCommand = SerializationManager.writeObject(command);
        socket.getOutputStream().write(serializedCommand);
    }

    public static Command getCommandFromString(String command){
        String[] splitted = command.split(" ");
        String commandName = splitted[0];
        String[] arguments = new String[splitted.length - 1];
        System.arraycopy(splitted, 1, arguments, 0, splitted.length - 1);
        try {
            return invoker.validateCommand(commandName, arguments);
        } catch (WrongArgumentsNumberException | NoSuchCommandException e) {
            return null;
        }
    }

    private void connect()  {
        System.out.println("Пытаюсь установить соединение с сервером");
        while (true) {
            try {
                InetAddress addr = InetAddress.getByName(host);
                socket = new Socket(addr, port);
                System.out.println("Подключено: " + socket);
                once=true;
                return;
            } catch (UnknownHostException e) {
                if(once){
                    once=false;
                    System.out.println("Неправильно указан хост");
                }
            } catch (IOException e) {
                if(once){
                    once=false;
                    System.out.println("Сервер отключен");
                }
            }
        }
    }

    private void registerCommands(){
        invoker = CommandsInvoker.getInstance();
        invoker.register("info", new InfoCommand( null));
        invoker.register("help", new HelpCommand(null));
        invoker.register("exit", new ExitCommand(null));
        invoker.register("clear", new ClearCommand(null));
        invoker.register("remove_by_id", new RemoveByIdCommand(null));
        invoker.register("add", new AddElementCommand(null));
        invoker.register("show", new ShowCommand(null));
        invoker.register("add", new AddElementCommand(null));
        invoker.register("update", new UpdateIdCommand(null));
        invoker.register("filter_starts_with_name", new FilterStartsWithNameCommand(null));
        invoker.register("remove_lower", new RemoveLowerThanElementCommand(null));
        invoker.register("execute_script", new ExecuteScriptCommand(null));
    }

    public static Socket getSocket() {
        return socket;
    }
}