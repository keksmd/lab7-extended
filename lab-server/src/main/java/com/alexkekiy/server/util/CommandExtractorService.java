package com.alexkekiy.server.util;

import com.alexkekiy.common.data.CommandDTO;
import com.alexkekiy.server.commands.NotFound;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class CommandExtractorService {
    public final HashMap<String, Function<String, ServerCommand>> nameToHandleMap;

    public CommandExtractorService(HashMap<String, Function<String, ServerCommand>> nameToHandleMap) {
        this.nameToHandleMap = nameToHandleMap;
    }

    /**
     * Метод, возвращающий команду,определяемую по текстовому запросу
     *
     * @param str - текстовое значение команды
     * @return объект, реализующий команду
     */
    public ServerCommand extractCommand(String str) {
        String[] tokens = str.split(" ");
        String prefix = "";
        for (int i = 0; i < tokens.length; i++) {
            prefix += tokens[i];
            if (nameToHandleMap.containsKey(prefix)) {
                Function<String, ServerCommand> factory = nameToHandleMap.get(prefix);
                if (i < tokens.length - 1) {
                    return factory.apply(tokens[i + 1]);
                } else {
                    return factory.apply("");
                }
            }
            prefix += " ";
        }
        return new NotFound();

    }

    public ServerCommand mapCommand(CommandDTO command, String name) throws InvocationTargetException, IllegalAccessException {
        ServerCommand s = extractCommand(name);
        return s.castInto(s, command);
    }
}
