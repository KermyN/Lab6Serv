package com.Kermy.Lab6.commands;


public class SaveCommand extends Command {
    public SaveCommand(String[] args) {
        super(args);
    }
    public SaveCommand(){
        this.args = new String[]{"input.xml"};
    }
    public SaveCommand(String resFileName){
        this.args = new String[]{resFileName};
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public String execute(CommandReceiver receiver) {
        receiver.getCollection().save();
        return "save done";
    }
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
