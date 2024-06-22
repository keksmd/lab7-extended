package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import com.alexkekiy.server.util.ServerCommand;

public class UpdateById extends ServerCommand implements Executable {
    public final String name = "update_by_id";

    public UpdateById() {
        super();
        this.setCommandType(CommandType.ELEMENT_AND_VALUE_ARGUMENTED);
    }

    public static UpdateById staticFactory(String[] args, String value) {
        UpdateById inst = new UpdateById();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        if (getCollectionRepository().getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            getCollectionRepository().getCollectionStream().filter(w -> String.valueOf(w.getId()).equals(this.getValue())).forEach(w -> {
                if (w.getOwner().equals(getUser())) {
                    SpaceMarineEntity spm = getCollectionRepository().getCollectionStream().filter(x -> x.getId() == Integer.parseInt(this.getValue())).findFirst().get();
                    spm.update(this.getArgs());
                    SpaceMarineRepository.getSpaceMarineRepository().update(spm);
                    resp.addMessage("Объект успешно обновлен");
                } else {
                    resp.addMessage("Объект с этим id принадлежит не вам");
                    resp.setSuccess(false);
                }
            });
        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }


}
