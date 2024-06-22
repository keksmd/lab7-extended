package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import com.alexkekiy.server.util.ServerCommand;

public class RemoveById extends ServerCommand implements Executable {
    public final String name = "remove_by_id";

    public RemoveById() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);
    }

    public static RemoveById staticFactory(String[] args, String value) {
        RemoveById inst = new RemoveById();
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
                    getCollectionRepository().remove(w);
                    SpaceMarineRepository.getSpaceMarineRepository().remove(w);
                    resp.addMessage("Объект успешно удален");
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
