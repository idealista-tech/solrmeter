package com.idealista.solrmeter.view;

public abstract class HeadlessConsolePanel implements ConsolePanel {

    public abstract void scheduleOperations();
    public abstract boolean operationsComplete();

}
