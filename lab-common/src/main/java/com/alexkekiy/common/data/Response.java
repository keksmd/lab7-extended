package com.alexkekiy.common.data;

import lombok.Getter;
import lombok.Setter;

/**
 * дата-класс-обертка для ответа сервера
 */
@Getter
@Setter

public class Response extends Message {
    AccountDTO user;
    private boolean success;
    private boolean flag = true;
}
