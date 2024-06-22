package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;
import com.alexkekiy.server.main.ClientConnector;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

import static com.alexkekiy.common.utilites.JsonSerializer.toJson;
import static com.alexkekiy.server.ServerMessaging.readRequest;
import static com.alexkekiy.server.main.App.log;

/**
 * класс,считывающий запросы c помощью Executor-а
 */
public class RequestReader extends RecursiveAction {
    private final ClientConnector client;


    public RequestReader(ClientConnector client) {
        this.client = client;
    }

    @Override
    protected void compute() {
        Request request = null;
        try {
            request = readRequest(client.getChannel());
        } catch (IOException e) {
            log.error("непрочитали", e);
            e.printStackTrace();
        } catch (MessageWasNotReadedSuccessfull e) {
        }
        if (request != null) {
            System.out.println("прочитали: " + toJson(request));
            client.requestsToHandle.add(request);
        }
    }
}
