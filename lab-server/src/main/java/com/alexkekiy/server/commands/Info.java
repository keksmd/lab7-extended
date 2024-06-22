package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class Info extends ServerCommand implements Executable {
    public final String name = "info";

    public Info() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Info staticFactory(String[] args, String value) {
        Info inst = new Info();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        resp.addMessage(String.format("В коллекции хранится %d элементов, %d из них принадлежат вам", getCollectionRepository().getCollectionSize(), getCollectionRepository().getCollectionStream().filter(w -> w.getOwner().equals(getUser())).count()));
        return resp;
    }

}
