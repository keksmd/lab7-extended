package test;

import com.alexkekiy.server.SpringConfig;
import com.alexkekiy.server.data.dao.ServerAccountDao;
import com.alexkekiy.server.data.dao.SpaceMarineDao;
import com.alexkekiy.server.data.entities.ChapterEntity;
import com.alexkekiy.server.data.entities.CoordinatesEntity;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.data.entities.Weapon;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class RepositoryTest {
    ServerAccountRepository serverAccountRepository;

    SpaceMarineRepository spaceMarineRepository;
     SpaceMarineDao spaceMarineDao;
    ServerAccountDao serverAccountDao;
    @Before
    public  void init(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        /*this.serverAccountDao = context.getBean(ServerAccountDao.class);
       this.spaceMarineDao = context.getBean(SpaceMarineDao.class);
       this.serverAccountRepository = context.getBean(ServerAccountRepository.class);

      */
        context.register(SpringConfig.class);
        context.refresh();
        this.spaceMarineRepository = context.getBean(SpaceMarineRepository.class);
    }
    @Test
    public void testSpaceMarineRepo(){
        SpaceMarineEntity spm = SpaceMarineEntity.builder().
                chapterEntity(new ChapterEntity()).
                coordinatesEntity
                (new CoordinatesEntity(1L,2f)).
                name("").loyal(true).
                weaponType(Weapon.COMBI_PLASMA_GUN)
                .build();
        spaceMarineRepository.add(spm);
        assert spaceMarineRepository.get(spm.getId()).isPresent();
        spaceMarineRepository.remove(spm);
        assert spaceMarineRepository.get(spm.getId()).isEmpty();

    }

}
