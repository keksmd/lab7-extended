package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.data.entities.Weapon;
import com.alexkekiy.server.util.ServerCommand;

import java.util.Arrays;


public class GroupByWeapon extends ServerCommand implements Executable {
    public final String name = "group_counting_by_weapon_type";

    public GroupByWeapon() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static GroupByWeapon staticFactory(String[] args, String value) {
        GroupByWeapon inst = new GroupByWeapon();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        StringBuilder s = new StringBuilder();
        Arrays.stream(Weapon.values()).forEach(gun -> s.append(String.format("%s : %d%n", gun.name(), getCollectionRepository().getCollectionStream().filter(w -> w.getWeaponType() == gun).count())));
        resp.addMessage(s.toString());
        return resp;
    }

}
