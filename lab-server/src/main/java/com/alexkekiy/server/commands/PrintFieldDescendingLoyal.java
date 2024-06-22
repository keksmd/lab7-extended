package com.alexkekiy.server.commands;

import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.util.ServerCommand;

public class PrintFieldDescendingLoyal extends ServerCommand implements Executable {
    public final String name = "print_field_descending_loyal";

    public PrintFieldDescendingLoyal() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static PrintFieldDescendingLoyal staticFactory(String[] args, String value) {
        PrintFieldDescendingLoyal inst = new PrintFieldDescendingLoyal();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        StringBuilder s = new StringBuilder();
        getCollectionRepository().getCollectionStream().filter(SpaceMarineEntity::getLoyal).forEach(s::append);
        getCollectionRepository().getCollectionStream().filter(w -> !w.getLoyal()).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;

    }
}
