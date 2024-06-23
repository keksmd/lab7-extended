package test;

import com.alexkekiy.server.SpringConfig;
import com.alexkekiy.server.data.dao.ServerAccountDao;
import com.alexkekiy.server.data.dao.SpaceMarineDao;
import com.alexkekiy.server.data.entities.*;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RepositoryTest {
    ServerAccountRepository serverAccountRepository;

    SpaceMarineRepository spaceMarineRepository;
     SpaceMarineDao spaceMarineDao;
    ServerAccountDao serverAccountDao;
    @Before
    public  void init(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(SpringConfig.class);
        context.refresh();

        this.serverAccountDao = context.getBean(ServerAccountDao.class);
       this.spaceMarineDao = context.getBean(SpaceMarineDao.class);
       this.serverAccountRepository = context.getBean(ServerAccountRepository.class);



        this.spaceMarineRepository = context.getBean(SpaceMarineRepository.class);
    }
    @Test
    @Transactional
    public void testSpaceMarineRepo(){

        SpaceMarineEntity spm = new SpaceMarineEntity("name", new CoordinatesEntity(1L, 33f), 23, true, 33f, Weapon.COMBI_PLASMA_GUN, new ChapterEntity("", ""));
        spaceMarineRepository.add(spm);
        assert spaceMarineRepository.get(spm.getId()).isPresent();
        spaceMarineRepository.remove(spm);
        assert spaceMarineRepository.get(spm.getId()).isEmpty();

    }
    @Test
    @Transactional
    public void testServerAccountRepo(){
        AccountEntity account = new AccountEntity("logint_test","password_test");
        serverAccountRepository.add(account);
        assert serverAccountRepository.get(account.getId()).isPresent();
        serverAccountRepository.remove(account);
        assert serverAccountRepository.get(account.getId()).isEmpty();
        account = new AccountEntity("logint_test2","password_test2");
        serverAccountRepository.add(account);
        assert serverAccountRepository.get(account.getLogin()).isPresent();
        serverAccountRepository.remove(account);
        assert serverAccountRepository.get(account.getLogin()).isEmpty();

    }

}
