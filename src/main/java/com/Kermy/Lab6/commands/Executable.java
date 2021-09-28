package com.Kermy.Lab6.commands;

/**
 * шаблон команда
 */
public interface Executable {
    /**
     * Execute.
     *
     */
    String execute(CommandReceiver receiver);
}
