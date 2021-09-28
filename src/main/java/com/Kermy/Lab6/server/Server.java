package com.Kermy.Lab6.server;

import com.Kermy.Lab6.app.SerializationManager;
import com.Kermy.Lab6.collection.Dragons;
import com.Kermy.Lab6.commands.Command;
import com.Kermy.Lab6.commands.CommandReceiver;
import com.Kermy.Lab6.commands.ExitCommand;
import com.Kermy.Lab6.commands.SaveCommand;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;



public class Server{
    private final int DEFAULT_BUFFER_SIZE = 4096;
    private ServerSocketChannel ssc;
    private IOManager ioManager;
    private CommandReceiver serverReceiver;
    private boolean serverOn = true;
    private final ByteBuffer b = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
    private final int port;
    private static Selector selector;

    public void run() throws JAXBException, IOException {
        ioManager = new IOManager();
        ioManager.writeLine("Запуск сервера.");
        initializeCollection();
        setupNet();
        while (serverOn) {
            checkClients();
            checkServerCommand();
        }
        new SaveCommand().execute(serverReceiver);
        closeEverything();
    }


    private boolean checkOneByte(SocketChannel channel) throws IOException {
        int bytesFromClient = 0;
        if(channel!=null) bytesFromClient = channel.read(b);
        return (bytesFromClient==1);
    }

    private void checkCommandFromClient(SocketChannel channel) throws IOException {
        if(checkOneByte(channel)){
            ioManager.writeLine("получен байт проверки");
            long a = System.currentTimeMillis();
            Command command = getCommandFromClient(channel);
            String resp;
            if(command!=null){
                resp = command.execute(serverReceiver);
                sendResponse(resp, channel);
                if(command instanceof ExitCommand){
                    ioManager.writeLine("Клиент вышел");
                    new SaveCommand().execute(serverReceiver);
                }
            }
            ioManager.writeLine("Команда выполнилась за " + (System.currentTimeMillis()-a) + " миллисекунд");
        }
    }
    private void checkAcceptable(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()) {
            SocketChannel channel = ssc.accept();
            if (channel != null) {
                ioManager.writeLine("accepted client");
                try {
                    channel.configureBlocking(false);
                    channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                    channel.register(selector, SelectionKey.OP_WRITE);
                } catch (IOException e) {
                    ioManager.writeLine("Unable to accept channel");
                    selectionKey.cancel();
                }
            }
        }
    }
    private void checkWritable(SelectionKey selectionKey) {
        if(selectionKey.isWritable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            try {
                checkCommandFromClient(socketChannel);
            } catch (IOException e) {
                ioManager.writeLine("Client disconnected.");
                selectionKey.cancel();
                new SaveCommand().execute(serverReceiver);
            }
        }
    }


    private void checkClients(){
        try {
            if(selector.selectNow()==0){
                return;
            }
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();

            while (iterator.hasNext() && serverOn) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                checkAcceptable(selectionKey);
                checkWritable(selectionKey);
                checkServerCommand();
            }
        } catch (IOException ioException) {
            ioManager.writeLine("Клиент отключился");
            new SaveCommand().execute(serverReceiver);
        }
    }

    //	Модуль отправки ответов
    private void sendResponse(String commandResult, SocketChannel channel){
        Response response = new Response(commandResult);
        try {
            byte[] ans = SerializationManager.writeObject(response);
            ioManager.writeLine("response serialized");
            ByteBuffer buffer = ByteBuffer.wrap(ans);
            int given = channel.write(buffer);
            ioManager.writeLine("sended response: " + given + " bytes");
        } catch (IOException e) {
            ioManager.writeLine("Error while serialization response");
        }
    }
    private void checkServerCommand() {
        try {
            if(System.in.available()>0){
                String[] line;
                String line1;
                Scanner scanner = new Scanner(System.in);
                if ((line1 = scanner.nextLine()) != null) {
                    line = line1.trim().split(" ");
                    if (line[0].equals("save")) {
                        switch (line.length) {
                            case 1:
                                new SaveCommand().execute(serverReceiver);
                                ioManager.writeLine("saved in default file");
                                break;
                            case 2:
                                new SaveCommand(line[1]).execute(serverReceiver);
                                ioManager.writeLine("saved in " + line[1]);
                                break;
                            default:
                                ioManager.writeLine("неверное количество аргументов");
                        }
                    } else if (line[0].equals("exit") && line.length == 1) {
                        ioManager.writeLine("exiting");
                        scanner.close();
                        closeEverything();
                        serverOn = false;
                    } else {
                        ioManager.writeLine("no such command");
                    }
                }
            }
        } catch (NoSuchElementException | IOException ignored) {
        }
    }

    private void closeEverything() {
        try {
            if(ssc!=null) ssc.close();
            if(selector!=null) selector.close();
        } catch (IOException i) {
        }
        ssc = null;
        selector = null;
    }

    private void setupNet(){
        selector = null;
        ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
            ssc.socket().bind(inetSocketAddress);
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println("Сервер уже запущен с другого окна...");
        }
    }

    public Server(int port){
        this.port = port;
    }

    //•	Модуль чтения запроса.
    //•	Модуль обработки полученных команд.
    private Command getCommandFromClient(SocketChannel channel) {
        try {
            Command command;
            int got;
            while(b.position()==1){
                got = channel.read(b);
                if(got!=0) ioManager.writeLine("получено байт:" + got);
            }
            if(b.remaining()!=0){
                command = SerializationManager.readObject(Arrays.copyOfRange(b.array(), 1, b.array().length-1));
                ioManager.writeLine("Полученная команда: " + command.toString());
                b.clear();
                return command;
            }
            return null;
        } catch (ClassNotFoundException e) {
            ioManager.writeLine("Error while serialization");
            return null;
        }catch (StreamCorruptedException e ){ // при попытке десериализации объекта, который не полностью передался
            System.out.println(e.getLocalizedMessage());
            return null;
        } catch(IOException e) {
            System.out.println("IOEXCEPTION");
            return null;
        }
    }
    private void initializeCollection() throws JAXBException, FileNotFoundException {
        String fileName ="Data.xml";
        ioManager.writeLine("имя файла:" + fileName);
        try {
            if (fileName != null) {
                File file = new File(fileName);
                if (!file.exists() || file.isDirectory()) {
                } else {
                    ioManager.writeLine("Файл существует, попытаемся считать коллекцию");
                }
            } else {
                ioManager.writeLine("переменная равна null");
            }
            Dragons dragons = new Dragons();
            dragons.uploadData(fileName);
            serverReceiver = new CommandReceiver(dragons);
        } catch (NullPointerException e) {
            System.out.println("Не удалось загрузить коллекцию");
        }

    }
}