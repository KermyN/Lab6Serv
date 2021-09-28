package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.collection.Dragon;
import com.sun.istack.internal.NotNull;


import java.io.Serializable;

/**
 * The type Add element command.
 */
public class AddElementCommand extends Command implements Serializable {

    private Dragon dr=null;

    /**
     * Instantiates a new Command.
     */
    public AddElementCommand(String[] args) {

    }

    @Override
    public void clientInsertion() {
        FieldsScanner fieldsScanner = FieldsScanner.getInstance();
        this.dr = fieldsScanner.scanDragon();
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String execute(@NotNull CommandReceiver receiver){
        dr.setCreationDate();
        receiver.getCollection().add(dr);
        return "Дракон добавлен успешно!";
    }

    /**
     *
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
