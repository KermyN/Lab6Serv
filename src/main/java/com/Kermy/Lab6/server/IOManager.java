package com.Kermy.Lab6.server;

import com.Kermy.Lab6.collection.Color;
import com.Kermy.Lab6.collection.DragonCharacter;
import com.Kermy.Lab6.collection.DragonType;

import java.io.*;
import static com.Kermy.Lab6.server.IsDigit.*;


public class IOManager {
    private final FileWriter FileWriter;
    private final InputStreamReader inputStreamReader;

    public IOManager() throws IOException{
       // try {
            inputStreamReader = new InputStreamReader(System.in);
            FileWriter = new FileWriter("log.txt");
        //}
       // catch(IOException e){
           // e.printStackTrace();
         //   System.exit(0);
       // }
    }

    public void write(String s) {
        System.out.print(s);
    }

    public void writeFile(String s){
        try {
            FileWriter.write(s);
            FileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String s) {
        write(s + "\n");
    }

    public int idReader(String question)throws NumberFormatException, IOException{
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || !IsDigit.isInteger(value)  || !(Integer.parseInt(value) > 0)) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return Integer.parseInt(value);
    }
    public String readNext() {
        StringBuilder newString = new StringBuilder();
        while (true) {
            try {
                if (!inputStreamReader.ready()) break;
                char c = (char)inputStreamReader.read();
                if (c == '\n'){break;}
                if (newString.length() != 0 && Character.isWhitespace(c)) break;
                newString.append(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newString.toString();
    }

    public String read() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in); // создать экземпляр InputStreamReader
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // экземпляр класса буферизации
        return bufferedReader.readLine();
    }

    public String StringReader(String question) throws IOException {
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty()) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return value;
    }

    /**
     * Method reading Integers
     */
    public Integer IntegerReader(String question) throws NumberFormatException, IOException {
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || !IsDigit.isInteger(value)  || !(Integer.parseInt(value) > 0)) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return Integer.valueOf(value);
    }

    /**
     * Method reading Doubles
     */
    public Double DoubleReader(String question) throws NumberFormatException, IOException {
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || !isDouble(value)) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return Double.valueOf(value);
    }

    /**
     * Method reading Longs
     */
    public Long LongReader(String question) throws NumberFormatException, IOException {
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || !isFloat(value) || !(Long.parseLong(value) > 0)|| !(Long.parseLong(value) < 404)) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return Long.valueOf(value);
    }

    /**
     * Method reading Colors
     */
    public Color ColorReader(String question) throws IllegalArgumentException,IOException{
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || (!value.equals("ORANGE") && !value.equals("RED") && !value.equals("YELLOW"))) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return Color.valueOf(value);
    }

    /**
     * Method reading Dragon characters
     */
    public DragonCharacter CharacterReader(String question) throws IllegalArgumentException,IOException{
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || (!value.equals("FICKLE") && !value.equals("GOOD") && !value.equals("CUNNING"))) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return DragonCharacter.valueOf(value);
    }
    /**
     * Method reading Dragon types
     */
    public DragonType TypeReader(String question) throws IllegalArgumentException,IOException{
        writeLine(question);
        String value = read();
        while (value.trim().isEmpty() || (!value.equals("WATER") && !value.equals("UNDERGROUND") && !value.equals("FIRE")&& !value.equals("AIR"))) {
            writeLine("Invalid value");
            writeLine(question);
            value = read();
        }
        return DragonType.valueOf(value);
    }

}

