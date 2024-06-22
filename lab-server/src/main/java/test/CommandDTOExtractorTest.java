package test;

import com.alexkekiy.server.commands.Add;
import com.alexkekiy.server.commands.Help;
import com.alexkekiy.server.main.services.CommandExtractorService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class CommandDTOExtractorTest {

    CommandExtractorService commandExtractorService;
    @Before
    public void init() {
        commandExtractorService = new AnnotationConfigApplicationContext().getBean(CommandExtractorService.class);
    }



    @SneakyThrows
    @Test
    public void extractCommand() {

        assert commandExtractorService.extractCommand("add").equals(new Add());
        assert commandExtractorService.extractCommand("help").equals(new Help());
    }

    @Test
    public void mapCommand() {
    }
}
