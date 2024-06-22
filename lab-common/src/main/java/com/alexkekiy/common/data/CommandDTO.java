/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package com.alexkekiy.common.data;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * абстрактный класс для команд
 */
@Setter
@Getter
@RequiredArgsConstructor
public class CommandDTO  {
    @Setter
    @Getter
    AccountDTO user;
    private String[] args;
    private String value;
    private String name = "command";


    public CommandDTO(String[] args, String value) {
        this.args = args;
        this.value = value;
    }



    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("***** ").append(this.getClass()).append(" Details *****\n");
        for (Field f : this.getClass().getFields()) {
            try {
                f.setAccessible(true);
                if (f.get(this) == null) {
                    s.append(f.getName()).append("=").append("null").append("\n");
                } else {
                    s.append(f.getName()).append("=").append(f.get(this).toString()).append("\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        s.append("*****************************");

        return s.toString();
    }


}