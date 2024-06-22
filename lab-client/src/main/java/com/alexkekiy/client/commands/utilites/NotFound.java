package com.alexkekiy.client.commands.utilites;


import com.alexkekiy.client.commands.types.NoArgumented;

public class NotFound extends NoArgumented {
    private String name = "not_found";
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
