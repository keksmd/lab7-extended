package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

public class FilterHeight extends ServerCommand implements Executable {
    public final String name = "filter_greater_than_height";

    public FilterHeight() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);
    }

    public static FilterHeight staticFactory(String[] args, String value) {
        FilterHeight inst = new FilterHeight();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        StringBuilder s = new StringBuilder();
        getCollectionRepository().getCollectionStream().filter(w -> w.getOwner().getLogin().equals(getUser().getLogin())).filter(w -> w.getHeight() > Integer.parseInt(getValue())).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;
    }
}
