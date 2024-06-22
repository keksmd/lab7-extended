package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.util.ServerCommand;

public class RemoveHead extends ServerCommand implements Executable {
    public final String name = "remove_head";

    public RemoveHead() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static RemoveHead staticFactory(String[] args, String value) {
        RemoveHead inst = new RemoveHead();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        if (getCollectionRepository().peek() != null && getCollectionRepository().peek().getOwner().equals(getUser())) {
            SpaceMarineEntity spm = getCollectionRepository().poll();
            getSpaceMarineRepository().remove(spm);
            resp.addMessage("голова успешно удалена");
        } else {
            resp.addMessage(" невозможно удалить голову(очередь пуста или голова не принадлежит вам)");
        }
        return resp;
    }
}
