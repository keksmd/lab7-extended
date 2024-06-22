package com.alexkekiy.server.commands;

import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.util.ServerCommand;

public class Add extends ServerCommand implements Executable {
    public final String name = "add";

    public Add() {
        super();
        this.setCommandType(CommandType.ELEMENT_ARGUMENTED);
    }

    public static Add staticFactory(String[] args, String value) {
        Add inst = new Add();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling( SpaceMarineEntity spm) {
        Response resp = super.calling();
        spm.setOwner(getUser());
        getCollectionRepository().add(spm);
        getSpaceMarineRepository().add(spm);
        return resp;

    }

    @Override
    public Response calling() {
        SpaceMarineEntity spm = SpaceMarineEntity.newInstance(getArgs());
        return this.calling( spm);
    }
}
