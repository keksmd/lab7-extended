package com.alexkekiy.server.main;


import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.server.main.handlers.RequestReader;
import com.alexkekiy.server.main.handlers.ResponseSender;
import com.alexkekiy.server.main.services.CommandExtractorService;
import com.alexkekiy.server.main.services.RequestHandlerService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.*;

import static com.alexkekiy.server.main.App.log;

/**
 * класс,управляющий подключением конкретного клиента к серверу,управялющий его аккаунтом,запросами и ответами
 */
public class ClientConnector extends Thread {
    private static final ForkJoinPool pool = new ForkJoinPool();
    private final static ExecutorService responseSendingPool = Executors.newFixedThreadPool(5);
    public final LinkedBlockingDeque<Response> responses = new LinkedBlockingDeque<>(100);
    public final LinkedBlockingDeque<Request> requestsToHandle = new LinkedBlockingDeque<>(100);
    private final RequestHandlerService requestHandlerService;
    private final CommandExtractorService commandExtractorService;
    @Setter
    @Getter
    private Selector selector;
    @Getter
    private final Client client;
    @Getter
    @Setter
    SocketChannel channel;

    public ClientConnector(Selector selector, RequestHandlerService requestHandlerService, CommandExtractorService commandExtractorService, SocketChannel socketChannel, Client client) {
        this.selector = selector;
        this.requestHandlerService = requestHandlerService;
        this.commandExtractorService = commandExtractorService;
       this.client = client;
       this.channel = socketChannel;

    }

    /**
     * Метод,задающий команды,которыми будут пользоваться клиент
     * и сервер
     */
    public Response setCommands() {
        this.client.setFirstMessageFromClient(false);
        StringBuilder sb = new StringBuilder();
        commandExtractorService.nameToHandleMap.
                forEach((name,functionStringToCommand) -> sb.
                        append(name).
                        append(",").
                        append(functionStringToCommand.apply(name).commandType.toString()).
                        append(";"));

        Response commandsResponse = new Response();
        commandsResponse.addMessage(sb.toString());
        return commandsResponse;
    }


    public void run() {
        try {
            while (true) {//true
                System.out.println("before");
                while (this.selector.selectNow() == 0) ;
                System.out.println("after");
                System.out.println("мощность итератора по селетору =" + getSelector().selectedKeys().size());
                Iterator<SelectionKey> keysIterator = this.getSelector().selectedKeys().iterator();
                try {
                    while (keysIterator.hasNext()) {
                        SelectionKey key = keysIterator.next();
                        if (!key.isValid()) {
                            continue;
                        }
                        this.handleKey(key);
                        keysIterator.remove();
                    }
                } catch (SocketException | ClosedChannelException e) {
                    this.channel.close();
                } catch (Exception e) {
                    log.error("ошибка,сервер чуть не лег", e);
                }
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * метод, определяющий готовность ключа к тем или иным действиям, и вызывающий методы их реализации
     *
     * @param key обрабатываемый ключ
     * @throws IOException (пробрасывает выше) в случае ошибки при преме-передаче сообщений
     */
    private void handleKey(SelectionKey key) throws IOException {
        try {
            Response response;
            Request request;
            if (key.isReadable()) {
                this.handleRead();
            }
            if ((request = this.requestsToHandle.poll(10, TimeUnit.MILLISECONDS)) != null) {

                Optional<Response> optionalRequest = requestHandlerService.handleRequest(request, this);
                optionalRequest.ifPresent(this.responses::add);

            }
            if ((response = this.responses.poll(10, TimeUnit.MILLISECONDS)) != null) {

                this.handleWrite(response);
            }
        } catch (InterruptedException e) {
        }
    }

    /**
     * метод чтения сообщения ключа
     */
    private void handleRead() {
        RequestReader requestReader = new RequestReader(this);
        pool.execute(requestReader);
        //ThreadRequestReader threadRequestReader = new ThreadRequestReader(this);
        //threadRequestReader.run();
    }

    /**
     * метод отправки ответа
     */
    void handleWrite(Response response) {
        try {
            if (response != null) {
                synchronized (responseSendingPool) {
                    responseSendingPool.submit(new ResponseSender(response, this)).get();
                }

            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }





}