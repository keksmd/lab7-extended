package com.alexkekiy.server.main.managers;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.server.data.entities.AccountEntity;
import com.alexkekiy.server.exceptions.InvalidPassword;
import com.alexkekiy.server.main.AuthService;
import lombok.Getter;
import lombok.Setter;

/**
 * менеджер клиента,упраявляющий его подключением,синхронизацией аккаунта с бд и авторизацией
 */
@Setter
@Getter
public class ClientManager {
    @Getter
    boolean firstMessageFromClient = true;

    private AuthService authService;
    private AccountEntity user;

    public ClientManager(AuthService authService) {
        this.authService = authService;
    }

    public void checkAnswer(Response response, Request request) throws InvalidPassword, NoAccountFounded {
        this.user =  authService.checkAnswer(response, request,this.user);

    }

}
