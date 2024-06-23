package com.alexkekiy.server.data.entities;

import com.alexkekiy.common.data.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * дата-энтити класс синхронизированного с бд аккаунта
 */
@Setter
@Entity
@DynamicInsert
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator")
    @SequenceGenerator(name = "my_sequence_generator", sequenceName = "my_sequence_name", allocationSize = 1)

    @Getter
    private Long id;

    public AccountEntity() {
        super();
        //this.id = getDBConnection().newId();
    }
    public AccountEntity(Account account){
        this.login = account.getLogin();
        this.password = account.getPassword();
        //this.id = account instanceof AccountEntity ? ((AccountEntity) account).getId() : getDBConnection().newId();
    }

    public AccountEntity(String login, String password) {
        this.login = login;
        this.password = password;
       // this.id = getDBConnection().newId();
    }

}

