package com.alexkekiy.client.commands.utilites;

import com.alexkekiy.client.commands.ScriptExecutor;
import com.alexkekiy.client.commands.types.ElementAndValueArgumented;
import com.alexkekiy.client.commands.types.ElementArgumented;
import com.alexkekiy.client.commands.types.NoArgumented;
import com.alexkekiy.client.commands.types.ValueArgumented;
import com.alexkekiy.client.main.ClientMessaging;
import com.alexkekiy.client.main.Context;
import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.alexkekiy.client.main.ClientMessaging.readResponse;

/**
 * Утильный класс,который запрашивает,принимает и хранит данные для маппинга команд, а также занимается самим маппингом
 */
public class CommandMapper {
    public Map<String, CommandTypes> nameToTypeMap;
    private CommandMapper(Map<String, CommandTypes> map) {
        this.nameToTypeMap = map;
    }

    /**
     * Статическая фабрика, принимающая зашифрованные имена и типы команд
     *
     * @param chanel канал,из которого принимаются команды
     * @return новый объект CommandMapper-а
     */
    public static CommandMapper recieveCommands(SocketChannel chanel) {
        Map<String, CommandTypes> nameToTypeMap = new HashMap<>();
        try {
            ClientMessaging.sendTextMessage(chanel, "commands");
            String coded = readResponse(chanel).getMessages().get(0);
            Arrays.stream(coded.split(";"))
                    .forEach(w -> nameToTypeMap.
                            put(w.split(",")[0],
                                    CommandTypes.valueOf(w.split(",")[1])));
        } catch (IOException | MessageWasNotReadedSuccessfull ignored) {
            throw new RuntimeException();
        }
        System.out.println("доступные команды и их типы \n" + nameToTypeMap);
        return new CommandMapper(nameToTypeMap);
    }

    /**
     * Метод,определяющий команду по текстовому вводу
     *
     * @param str - текстовое значение команды
     * @param ctx - контекст считывания данных
     * @return объект, реализующий команду
     */

    public ClientCommand extractCommand(String str, Context ctx) {
        String[] tokens = str.split(" ");
        String prefix = "";
        for (int i = 0; i < tokens.length; i++) {
            prefix += tokens[i];
            if (nameToTypeMap.containsKey(prefix)) {
                CommandTypes type = nameToTypeMap.get(prefix);
                return switch (type) {
                    case VALUE_ARGUMENTED -> {
                        if (i < tokens.length - 1) {
                            if (new Scanner(tokens[i + 1]).hasNextInt()) {
                                ClientCommand temp = new ValueArgumented(tokens[i + 1]);
                                temp.setName(prefix);

                                yield temp;
                            } else {
                                yield new NotFound();
                            }
                        } else yield new NotFound();

                    }
                    case WITHOUT_ARGUMENTS -> {
                        ClientCommand temp = new NoArgumented();
                        temp.setName(prefix);
                        yield temp;
                    }
                    case ELEMENT_ARGUMENTED -> {
                        ClientCommand temp = new ElementArgumented(ctx);
                        temp.setName(prefix);
                        yield temp;
                    }
                    case ELEMENT_AND_VALUE_ARGUMENTED -> {
                        if (i < tokens.length - 1) {

                            if (new Scanner(tokens[i + 1]).hasNextInt()) {
                                ClientCommand temp = new ElementAndValueArgumented(ctx, tokens[i + 1]);
                                i++;
                                temp.setName(prefix);
                                yield temp;
                            } else {
                                yield new NotFound();
                            }
                        } else yield new NotFound();
                    }
                };
            } else if (prefix.equals("execute_script") && i < tokens.length - 1) {
                new ScriptExecutor(tokens[i + 1]).createRequest();
                i++;
            }
            prefix += " ";
        }
        return new NotFound();
    }
}
