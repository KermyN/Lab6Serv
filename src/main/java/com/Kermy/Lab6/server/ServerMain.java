package com.Kermy.Lab6.server;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {

        if(args.length!=1){
            System.out.println("Укажите в аргументах порт (8080)");
            System.exit(1);
        }else{
            int port = 8080;
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                System.out.println("Не целое число, использую 8080");
            }
            Server server = new Server(port);
            server.run();
        }
    }
}
