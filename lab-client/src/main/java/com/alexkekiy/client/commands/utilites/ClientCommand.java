/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package com.alexkekiy.client.commands.utilites;

import com.alexkekiy.common.data.CommandDTO;
import com.alexkekiy.common.data.Request;

import static com.alexkekiy.client.main.Main.continuingAccount;

public abstract class ClientCommand extends CommandDTO {

    public ClientCommand(String v, String[] args) {
        super(args, v);
    }

    /**
     * общий для всех классов-комманд,являющихся наследниками {@link ClientCommand}
     * метод,оборачивающий команду в запрос
     */
    public Request createRequest() {
        Request request = new Request(this);
        this.setUser(continuingAccount);
        return request;
    }
}
