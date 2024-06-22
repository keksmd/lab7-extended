package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.AccountDTO;
import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class Enter extends ServerCommand implements Executable {
    public final String name = "enter";

    public Enter() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);

    }

    public static Enter staticFactory(String[] args, String value) {
        Enter inst = new Enter();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;

    @Override

    public Response calling() {
        Response resp = super.calling();
        resp.setUser(new AccountDTO(this.getValue().split(";")[0], this.getValue().split(";")[1]));
        resp.addMessage("Вы успешно вошли");

        return resp;

    }

}
