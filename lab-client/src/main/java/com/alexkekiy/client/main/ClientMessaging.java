package com.alexkekiy.client.main;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;
import com.alexkekiy.common.utilites.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public final class ClientMessaging {
    private ClientMessaging() {
    }

    public static Response readResponse(SocketChannel clientChannel) throws IOException, MessageWasNotReadedSuccessfull {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int readed = clientChannel.read(buf);
        if (readed > 0) {
            buf.flip();
            String s = new String(ByteBuffer.allocate(readed).put(buf.array(), 0, readed).array());
            return JsonSerializer.deserialize(s, new TypeReference<>() {

            });
        } else {
            throw new MessageWasNotReadedSuccessfull();
        }
    }

    public static void sendTextMessage(SocketChannel clientChannel, String message) throws IOException {
        Request req = new Request(null);
        req.addMessage(message);
        String json = JsonSerializer.toJson(req);
        ByteBuffer buf = ByteBuffer.allocate(json.getBytes().length).put(json.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
        System.out.println("sended " + json);
    }

    public static void sendRequest(SocketChannel clientChannel, Request req) throws IOException {
        String message = JsonSerializer.toJson(req);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
        System.out.println("sended " + message);
    }

}
