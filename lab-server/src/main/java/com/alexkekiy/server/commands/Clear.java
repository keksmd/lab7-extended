package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class Clear extends ServerCommand implements Executable {


    public final String name = "clear";

    public Clear() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Clear staticFactory(String[] args, String value) {
        Clear inst = new Clear();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;

    @Override
    public Response calling() {
        Response resp = super.calling();
        getCollectionRepository().getCollectionStream().forEach(w -> {
            if (w.getOwner().equals(getUser())) {
                getCollectionRepository().remove(w);
                getSpaceMarineRepository().remove(w);
            }
        });
        return resp;
    }
}

