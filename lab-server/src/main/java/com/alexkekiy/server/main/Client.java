package com.alexkekiy.server.main;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.server.data.entities.AccountEntity;
import com.alexkekiy.server.exceptions.InvalidPassword;
import com.alexkekiy.server.main.services.AuthService;
import lombok.Getter;
import lombok.Setter;

/**
 * менеджер клиента,упраявляющий его подключением,синхронизацией аккаунта с бд и авторизацией
 */
@Setter
@Getter
public class Client {
    @Getter
    boolean firstMessageFromClient = true;

    private AuthService authService;
    private AccountEntity user;

    public Client(AuthService authService) {
        this.authService = authService;
    }

    public void checkAnswer(Response response, Request request) throws InvalidPassword, NoAccountFounded {
        this.user =  authService.checkAnswer(response, request,this.user);

    }

}
