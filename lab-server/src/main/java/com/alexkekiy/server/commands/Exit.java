package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class Exit extends ServerCommand implements Executable {
    public final String name = "exit";

    public Exit() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Exit staticFactory(String[] args, String value) {
        Exit inst = new Exit();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        resp.setFlag(false);
        new Save().calling();
        return resp;

    }


}
