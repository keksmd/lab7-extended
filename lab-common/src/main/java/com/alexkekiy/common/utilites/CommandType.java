package com.alexkekiy.common.utilites;

public enum CommandType {
    ELEMENT_AND_VALUE_ARGUMENTED(),
    ELEMENT_ARGUMENTED(),
    VALUE_ARGUMENTED(),
    WITHOUT_ARGUMENTS();

    @Override
    public String toString() {
        return name();
    }
}
