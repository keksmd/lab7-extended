package com.alexkekiy.common.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * дата-класс-обертка для запроса выполнения команды
 */
public class Request extends Message {
    private CommandDTO commandToExecute;

    public Request(CommandDTO commandToExecute) {
        super();
        this.commandToExecute = commandToExecute;
    }
}
