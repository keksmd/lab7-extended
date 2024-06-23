package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.ServerAccountDao;
import com.alexkekiy.server.data.entities.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Scope("singleton")
public class ServerAccountRepository extends DBRepository<AccountEntity> {
    private final ServerAccountDao serverAccountDao;

    @Autowired

    private ServerAccountRepository(ServerAccountDao serverAccountDao) {
        super(serverAccountDao);
        this.serverAccountDao = serverAccountDao;
    }


    @Transactional
    public Optional<AccountEntity> get(String login) {
        Optional<AccountEntity> optionalServerAccount;
        try {
            optionalServerAccount = serverAccountDao.get(login);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return optionalServerAccount;
    }

    /*@Transactional
    @Override
    public void add(AccountEntity serverAccount) {
        jbdcServerAccountDao.save(serverAccount);
    }

    @Transactional
    @Override
    public void remove(AccountEntity serverAccount) {
        jbdcServerAccountDao.delete(serverAccount);
    }

     */
}
