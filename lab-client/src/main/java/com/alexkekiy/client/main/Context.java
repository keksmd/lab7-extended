package com.alexkekiy.client.main;

import java.util.Scanner;

public class Context {
    private Scanner scanner;

    public Context(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
