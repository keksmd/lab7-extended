package com.alexkekiy.common.data;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * класс для представления передаваемых сообщений
 */
@Getter
@Setter
public abstract class Message {
    private ArrayList<String> messages;

    public Message() {
        this.messages  = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("***** ").append(this.getClass()).append(" Details *****\n");
        for (Field f : this.getClass().getFields()) {
            try {
                f.setAccessible(true);
                s.append(f.getName()).append("=").append(f.get(this).toString()).append("\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        s.append("*****************************");

        return s.toString();
    }

    public void addMessage(String msg1) {
        this.messages.add(msg1);
    }

}
