package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.util.ServerCommand;

import java.util.Comparator;
import java.util.Objects;

public class AddIfMax extends ServerCommand implements Executable {
    public final String name = "add_if_max";

    public AddIfMax() {
        super();
        this.setCommandType(CommandType.ELEMENT_ARGUMENTED);
    }

    public static AddIfMax staticFactory(String[] args, String value) {
        AddIfMax inst = new AddIfMax();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }


    @Override
    public Response calling() {
        Response resp = super.calling();
        SpaceMarineEntity spm = SpaceMarineEntity.newInstance(this.getArgs());
        if (spm.compareTo(Objects.requireNonNull(getCollectionRepository().getCollectionStream().max(Comparator.naturalOrder()).orElse(null))) >= 0) {
            new Add().calling(spm);
        } else {
            resp.setSuccess(false);
        }
        return resp;
    }
}
