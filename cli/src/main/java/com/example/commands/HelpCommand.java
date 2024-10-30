package com.example.commands;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Available commands:");
        System.out.println("pwd - Print working directory");
        System.out.println("cd [directory] - Change directory");
        System.out.println("ls - List files in the current directory");
        System.out.println("ls -a - List all files, including hidden files");
        System.out.println("ls -r - List files recursively");
        System.out.println("mkdir [directory] - Create a new directory");
        System.out.println("rmdir [directory] - Remove a directory");
        System.out.println("touch [file] - Create a new file or update the timestamp of an existing file");
        System.out.println("mv [source] [destination] - Move or rename a file");
        System.out.println("rm [file] - Remove a file");
        System.out.println("cat [file] - Display the contents of a file");
        System.out.println("exit - Exit the CLI");
        System.out.println("help - Show this help message");
    }
}
