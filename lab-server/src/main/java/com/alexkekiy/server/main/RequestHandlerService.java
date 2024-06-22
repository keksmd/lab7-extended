package com.alexkekiy.server.main;

import com.alexkekiy.common.data.Request;
import com.alexkekiy.common.data.Response;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import com.alexkekiy.server.main.handlers.RequestHandler;
import com.alexkekiy.server.main.managers.CollectionRepository;
import com.alexkekiy.server.util.CommandExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestHandlerService {
    private final CollectionRepository collectionRepository;
    private final ServerAccountRepository serverAccountRepository;
    private final SpaceMarineRepository spaceMarineRepository;
    private final CommandExtractorService commandExtractorService;

    @Autowired
    public RequestHandlerService(CollectionRepository collectionRepository, ServerAccountRepository serverAccountRepository, SpaceMarineRepository spaceMarineRepository, CommandExtractorService commandExtractorService) {
        this.collectionRepository = collectionRepository;
        this.serverAccountRepository = serverAccountRepository;
        this.spaceMarineRepository = spaceMarineRepository;
        this.commandExtractorService = commandExtractorService;
    }

    public Optional<Response> handleRequest(Request request, ClientConnector connector) {
        RequestHandler requestHandler = new RequestHandler(request, connector, collectionRepository, serverAccountRepository, spaceMarineRepository, commandExtractorService);
        return requestHandler.call();
    }

}
