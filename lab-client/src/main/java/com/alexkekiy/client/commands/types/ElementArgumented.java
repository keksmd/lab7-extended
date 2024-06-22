package com.alexkekiy.client.commands.types;

import com.alexkekiy.client.main.Context;

import static com.alexkekiy.common.utilites.CheckingReader.readSomeArgs;

/**
 * Класс команд,имеющих аргументами  String[] - аргументы для управления сущностями
 */
public class ElementArgumented extends ElementAndValueArgumented {
    public ElementArgumented(String[] args) {
        super(null, args);
    }

    public ElementArgumented(Context ctx) {
        super(null, readSomeArgs(9, "s,l,f,l,b,f,s,s,s".split(","), ctx.getScanner(), (
                        "Введите имя;" +
                                "Введите целочисленную x-координату (x<=625));" +
                                "Введите y-координату в формате деcятичной дроби (y>=-354.0);" +
                                "Введите здоровье;" +
                                "Введите булевое значение true/false преданности;" +
                                "Введите десятичное число,характеризующее длинну;" +
                                """
                                        Введите одно из названия для оружия:
                                            BOLT_PISTOL,
                                            COMBI_PLASMA_GUN,
                                            GRENADE_LAUNCHER,
                                            INFERNO_PISTOL,
                                            MULTI_MELTA;""" +
                                "Введите название главы;" +
                                "Введите название главы;").split(";"),
                "more length 0;less than 626;more than -353.0;more than 0;;;is weapon;more length 0;more length 0".split(";")));
    }

}
