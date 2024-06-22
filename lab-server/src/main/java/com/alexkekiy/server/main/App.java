package com.alexkekiy.server.main;


import com.alexkekiy.common.data.AccountDTO;
import com.alexkekiy.server.SpringConfig;
import com.alexkekiy.server.data.entities.AccountEntity;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * main-класс,принимающий подключения и задающий конфигурации
 */
public class App {
    public static final Logger log = LogManager.getLogger(App.class.getName());


    private App() {
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();

        AccountEntity acc = new AccountEntity(AccountDTO.getCommonAcc().getLogin(), AccountDTO.getCommonAcc().getPassword());
        AccountEntity.commonAcc = acc;
        context.getBean(ServerAccountRepository.class).add(acc);




        context.getBean("server", Server.class).run();
    }




}