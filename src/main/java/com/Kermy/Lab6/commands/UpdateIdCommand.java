package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.collection.Dragon;

/**
 * The type Update id command.
 */
public class UpdateIdCommand extends Command{

    /**
     * Instantiates a new Command.
     */
    public UpdateIdCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }
    private Dragon dr;

    @Override
    public void clientInsertion() {
        FieldsScanner fieldsScanner = FieldsScanner.getInstance();
        dr = fieldsScanner.scanDragon();
    }

    @Override
    public String execute(CommandReceiver receiver) {
        try{
            int id = Integer.parseInt(args[0].trim());
            if(receiver.getCollection().get(id)!=null){
                dr.setId(id);
                receiver.getCollection().add(dr);
                return "Дракон добавлен успешно!";
            }else{
                return "Дракона с id " + id + " в коллекции не нашлось.";
            }
        }catch (NumberFormatException e){
            return "ID - это число";
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}