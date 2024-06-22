package com.alexkekiy.server.commands;

import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class Show extends ServerCommand implements Executable {
    public final String name = "show";

    public Show() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Show staticFactory(String[] args, String value) {
        Show inst = new Show();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;

    public Response calling() {
        Response resp = super.calling();

        if (getCollectionRepository().getCollectionSize() == 0) {
            resp.addMessage("В коллекции нет элементов");
        }

        getCollectionRepository().getCollectionStream().forEach(
                w -> resp.addMessage(w + "\n"));
        return resp;
    }
}
