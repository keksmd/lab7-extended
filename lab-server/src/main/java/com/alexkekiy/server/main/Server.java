package com.alexkekiy.server.main;

import com.alexkekiy.server.main.managers.ClientManager;
import com.alexkekiy.server.main.managers.CollectionRepository;
import com.alexkekiy.server.util.CommandExtractorService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static com.alexkekiy.server.main.App.log;
import static java.nio.channels.SelectionKey.OP_READ;

@Component("server")
public class Server implements Runnable {
    @Getter
    private final CollectionRepository collectionRepository;
    private final RequestHandlerService requestHandlerService;
    @Value("${server.port}")
    private int PORT;
    private final ServerSocketChannel serverSocketChannel;
    private final Selector appSelector;
    private final CommandExtractorService commandExtractorService;
    private final AuthService authService;
    @Autowired
    public Server(CollectionRepository collectionRepository, ServerSocketChannel serverSocketChannel, Selector appSelector, RequestHandlerService requestHandlerService, CommandExtractorService commandExtractorService,AuthService authService) {
        this.collectionRepository = collectionRepository;
        this.serverSocketChannel = serverSocketChannel;
        this.appSelector = appSelector;
        this.requestHandlerService = requestHandlerService;
        this.commandExtractorService = commandExtractorService;
        this.authService =authService;
    }

    private void handleAcception() throws IOException {
        SocketChannel clientChannel = serverSocketChannel.accept();
        Selector oneCleintSelector = Selector.open();
        ClientConnector clientConnector = new ClientConnector(oneCleintSelector,requestHandlerService, commandExtractorService,clientChannel,new ClientManager(authService));

        clientConnector.getChannel().configureBlocking(false);
        clientConnector.getChannel().register(oneCleintSelector, OP_READ | SelectionKey.OP_WRITE);
        System.out.println("Соединение с клиентом обработано и передано конннектору для работы с клиентом");
        clientConnector.start();
    }

    @PostConstruct
    void init() {
        try {
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(appSelector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.error(e);
        }
        try {
            ;
        } catch (Exception ignored) {

        }
    }

    public void run() {
        try {
            System.out.println("Программа запущенна");
            while (true) {
                appSelector.select();
                for (SelectionKey selectionKey : appSelector.selectedKeys()) {
                    handleAcception();
                }
            }
        } catch (Exception e) {
            log.error("ошибка, приложение чуть не упало", e);
        }
    }
}
