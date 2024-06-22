package com.alexkekiy.client.commands;

import com.alexkekiy.client.commands.utilites.ClientCommand;
import com.alexkekiy.client.main.Main;
import com.alexkekiy.common.data.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ScriptExecutor extends ClientCommand {

    final String fileName;
    private final String name = "execute_script";

    public ScriptExecutor(String fileName) {
        super(null, null);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Request createRequest() {
        Request resp = super.createRequest();
        File file = new File(fileName);
        if (file.exists()) {
            if (!Main.getWasExecuted().add(fileName)) {
                System.out.println("Рекурсия была проинорирована");
            } else {
                Scanner fileContentScanner;
                try {
                    fileContentScanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                while (fileContentScanner.hasNextLine()) {
                    try {
                        Main.executeNext(fileContentScanner);
                    } catch (IOException ignored) {
                    }
                }
            }
            return resp;
        } else {
            System.out.println("There is no file with choosen name");
            return resp;
        }
    }
}