package com.example.commands;

public class ExitCommand implements Command {
    private final Runnable exitAction;

    public ExitCommand() {
        this(() -> System.exit(0));
    }

    public ExitCommand(Runnable exitAction) {
        this.exitAction = exitAction;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Exiting CLI...");
        exitAction.run();
    }
}