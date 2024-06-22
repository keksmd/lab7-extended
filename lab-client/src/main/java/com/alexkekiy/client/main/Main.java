package com.alexkekiy.client.main;

import com.alexkekiy.client.commands.types.NoArgumented;
import com.alexkekiy.client.commands.types.ValueArgumented;
import com.alexkekiy.client.commands.utilites.ClientCommand;
import com.alexkekiy.client.commands.utilites.CommandMapper;
import com.alexkekiy.client.commands.utilites.NotFound;
import com.alexkekiy.client.exceptions.NotFoundedCommand;
import com.alexkekiy.common.data.AccountDTO;
import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;
import com.alexkekiy.common.utilites.PasswordCryptography;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.alexkekiy.client.main.ClientMessaging.readResponse;
import static com.alexkekiy.client.main.ClientMessaging.sendRequest;
import static com.alexkekiy.common.utilites.CheckingReader.validateInput;


public class Main {
    private static final Context ctx = new Context(new Scanner(System.in));
    private final static ClientCommand exit = new NoArgumented();
    private final static Set<String> wasExecuted = new HashSet<>();
    public static AccountDTO continuingAccount;
    public static boolean flag = true;
    static SocketChannel socketChannel;
    private static CommandMapper commandMapper;

    static {
        exit.setName("exit");
    }

    private Main() {
    }

    public static Set<String> getWasExecuted() {
        return wasExecuted;
    }

    private static void setConnection() {
        boolean flag = true;
        while (flag) {
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8081);
            try {
                flag = false;
                //InetSocketAddress  socketAddress = new InetSocketAddress(InetAddress.getByName("helios.cs.ifmo.ru"),8081);
                socketChannel = SocketChannel.open(socketAddress);
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                flag = true;
                System.out.println("Не удалось подключиться к серверу,введите любую строку,чтобы попробовать еще раз");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        continuingAccount = AccountDTO.getCommonAcc();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sendRequest(socketChannel, exit.createRequest());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        setConnection();
        socketChannel.configureBlocking(true);
        commandMapper = CommandMapper.recieveCommands(socketChannel);
        setAccount();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        ReadingThread readingThread = new ReadingThread(selector);
        readingThread.start();
        socketChannel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);

        while (true) {
            while (flag) {
                try {
                    if (System.in.available() > 0) {
                        executeNext(ctx.getScanner());
                    }
                } catch (NotFoundedCommand e) {
                    System.out.println("Unknown command,try again or use 'help' to get information about available commands");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            readingThread.wait();
        }
    }

    private static void setAccount() throws IOException {
        boolean flag = false;
        while (!flag) {
            try {
                if ((Boolean) validateInput("b", "", "Вы уже зарегистрированы?", new Scanner(System.in))) {
                    String login = (String) validateInput("s", "Введите логин", new Scanner(System.in));
                    String pswd = PasswordCryptography.encodePassword((String) validateInput("s", "Введите пароль", new Scanner(System.in)));
                    ValueArgumented v = new ValueArgumented(login + ";" + pswd);
                    v.setName("enter");
                    Request r = v.createRequest();
                    r.addMessage("enter");
                    ClientMessaging.sendRequest(socketChannel, r);
                    if (readResponse(socketChannel).isSuccess()) {
                        flag = true;
                        continuingAccount = new AccountDTO(login, pswd);
                        return;
                    }
                } else {
                    String login = (String) validateInput("s", "Введите логин", new Scanner(System.in));
                    String pswd = PasswordCryptography.encodePassword((String) validateInput("s", "Введите пароль", new Scanner(System.in)));
                    ValueArgumented v = new ValueArgumented(login + ";" + pswd);
                    v.setName("register");
                    Request r = v.createRequest();
                    r.addMessage("register");
                    ClientMessaging.sendRequest(socketChannel, r);
                    if (readResponse(socketChannel).isSuccess()) {
                        flag = true;
                        continuingAccount = new AccountDTO(login, pswd);
                        return;
                    }
                }
            } catch (MessageWasNotReadedSuccessfull ignored) {
            }
        }

    }

    public static void executeNext(Scanner s) throws IOException {
        Request req = commandMapper.extractCommand(s.nextLine(), ctx).createRequest();
        if (!(req.getCommandToExecute() instanceof NotFound)) {
            req.addMessage(req.getCommandToExecute().getName());
            req.getCommandToExecute().setUser(continuingAccount);
            sendRequest(socketChannel, req);
        } else {
            throw new NotFoundedCommand();
        }
    }
}