package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Executable;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.server.util.ServerCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Help extends ServerCommand implements Executable {
    public final String name = "help";

    public Help() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Help staticFactory(String[] args, String value) {
        Help inst = new Help();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        resp.setMessages(Arrays.stream("help| справка/info| информация/show| элементы коллекции/add{element}| добавить элемент/update{id element}| обновить элемент по id/remove_by_id{id}| удалить элемент id/clear| очистить/save| сохранить/execute_script{filename}| исполнить команды из файла/exit| выйти/remove_head| удалить и показать первый элемент/add_if_max{element}| добавить элемент,если он-максимальный/add_if_min{element}| добавить элемент,если он-минимальный/group_counting_by_weapon_type| элементы коллекции по значению weaponType/filter_greater_than_height{height}| элементы,с height больше заданного/print_field_descending_loyal| вывести loyal всех элементов".split("/")).collect(Collectors.toCollection(ArrayList::new)));
        return resp;
    }
}
