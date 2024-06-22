package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;
import com.alexkekiy.server.main.ClientConnector;

import java.io.IOException;

import static com.alexkekiy.common.utilites.JsonSerializer.toJson;
import static com.alexkekiy.server.ServerMessaging.readRequest;
import static com.alexkekiy.server.main.App.log;
@Deprecated
public class ThreadRequestReader implements Runnable{
    private final ClientConnector client;


    public ThreadRequestReader(ClientConnector client) {
        this.client = client;
    }
    @Override
    public void run() {
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
            //Optional<Response> optionalRequest =new RequestHandler(request, client).call();
            //optionalRequest.ifPresent(client.responses::add);
        }
    }
}
