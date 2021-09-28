package com.Kermy.Lab6.commands;

/**
 * The type Clear command.
 */
public class ClearCommand extends Command {


    /**
     * Instantiates a new Command.
     *
     */
    public ClearCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String execute(CommandReceiver receiver) {
         receiver.getCollection().clear();
         return "Коллекция очищена";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
