package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.server.data.repositories.CollectionRepository;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import com.alexkekiy.server.exceptions.InvalidPassword;
import com.alexkekiy.server.main.ClientConnector;
import com.alexkekiy.server.main.services.CommandExtractorService;
import com.alexkekiy.server.util.ServerCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * класс,обрабатывающий запрос в отдельном потоке и создающий ответное сообщение
 */

public class RequestHandler implements Callable<Optional<Response>> {
    private final Request request;
    private final ClientConnector connector;
    private final ServerAccountRepository serverAccountRepository;
    private final SpaceMarineRepository spaceMarineRepository;
    private final CollectionRepository collectionRepository;
    private final CommandExtractorService commandExtractorService;


    public RequestHandler(@NotNull Request request, ClientConnector connector, CollectionRepository collectionRepository, ServerAccountRepository serverAccountRepository, SpaceMarineRepository spaceMarineRepository, CommandExtractorService commandExtractorService) {
        this.connector = connector;
        this.request = request;
        this.serverAccountRepository = serverAccountRepository;
        this.spaceMarineRepository = spaceMarineRepository;
        this.collectionRepository = collectionRepository;
        this.commandExtractorService = commandExtractorService;
    }


    @Override
    public Optional<Response> call() {
            if (request.getMessages().get(0).equals("commands") && connector.getClient().isFirstMessageFromClient()) {
                return Optional.of(connector.setCommands());
            } else {
                try {
                    ServerCommand commandToExecute = commandExtractorService.mapCommand(request.getCommandToExecute(), request.getMessages().get(0));
                    Response response = executeCommand(commandToExecute);
                    System.out.printf("команда %s выполнена%n", request.getCommandToExecute().getName());

                    try {
                        connector.getClient().checkAnswer(response, request);
                    } catch (NoAccountFounded e) {
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Аккаунт не найден").collect(Collectors.toCollection(ArrayList::new)));
                    } catch (InvalidPassword e) {
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Неверный пароль").collect(Collectors.toCollection(ArrayList::new)));
                    }
                    return Optional.of(response);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Ошибка: неверно реализована команда");
                    e.printStackTrace();
                    return Optional.empty();
                }
            }


    }

    public Response executeCommand(ServerCommand command) {
        return command.execute(collectionRepository,serverAccountRepository,spaceMarineRepository);
    }
}