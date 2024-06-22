package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;
@Deprecated

public class Save extends ServerCommand {
    public final String name = "save";

    public Save() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Save staticFactory(String[] args, String value) {
        Save inst = new Save();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        return super.calling();
    }

}