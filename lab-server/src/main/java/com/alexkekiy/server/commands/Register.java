package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.AccountDTO;
import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.AccountEntity;
import com.alexkekiy.server.util.ServerCommand;

public class Register extends ServerCommand implements Executable {
    public final String name = "register";

    public Register() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);

    }

    public static Register staticFactory(String[] args, String value) {
        Register inst = new Register();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        resp.setUser(new AccountDTO(this.getValue().split(";")[0], this.getValue().split(";")[1]));
        getServerAccountRepository().add(new AccountEntity(resp.getUser().getLogin(), resp.getUser().getPassword()));
        resp.addMessage("Вы успешно зарегестрировались и вошли");
        resp.setSuccess(true);
        return resp;

    }

}
