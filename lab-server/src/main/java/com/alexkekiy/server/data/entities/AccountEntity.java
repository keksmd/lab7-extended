package com.alexkekiy.server.data.entities;

import com.alexkekiy.common.data.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.alexkekiy.server.util.DBConnection.getDBConnection;

/**
 * дата-энтити класс синхронизированного с бд аккаунта
 */
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "login")
})
public class AccountEntity implements Account {

    @Getter
    @Transient
    public static AccountEntity commonAcc;
    @Getter
    public String login;
    @Getter
    public String password;
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;

    public AccountEntity() {
        super();
        this.id = getDBConnection().newId();
    }
    public AccountEntity(Account account){
        this.login = account.getLogin();
        this.password = account.getPassword();
        this.id = account instanceof AccountEntity ? ((AccountEntity) account).getId() : getDBConnection().newId();
    }

    public AccountEntity(String login, String password) {
        this.login = login;
        this.password = password;
        this.id = getDBConnection().newId();
    }

}

