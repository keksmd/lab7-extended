package com.alexkekiy.server.data.entities;

public enum Weapon {
    BOLT_PISTOL,
    COMBI_PLASMA_GUN,
    GRENADE_LAUNCHER,
    INFERNO_PISTOL,
    MULTI_MELTA;

    public static Weapon choose(String s) {
        Weapon gun = null;
        for (Weapon value : Weapon.values()) {
            if (value.name().equals(s)) {
                gun = value;
            }
        }
        return gun;
    }
}