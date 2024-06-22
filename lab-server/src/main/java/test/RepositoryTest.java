package test;

import com.alexkekiy.server.data.entities.*;
import com.alexkekiy.server.data.repositories.HibernateUtils;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

public class RepositoryTest {
   EntityManager em;
    @Before
    public  void init(){
        this.em = HibernateUtils.getEntityManager();
    }
    @Test
    public void testPersist(){
        SpaceMarineEntity.SpaceMarineEntityBuilder spmb = SpaceMarineEntity.builder();
        spmb.chapterEntity(new ChapterEntity()).coordinatesEntity
                (new CoordinatesEntity()).name("").loyal(true).weaponType(Weapon.COMBI_PLASMA_GUN).build();
        Stream<Object> entities = Stream.of(spmb.build(),new AccountEntity(),new CoordinatesEntity(),new ChapterEntity());
        entities.forEach(entity->
        {
            em.getTransaction().begin();
            assert em.getTransaction().isActive();
            em.persist(entity);
            em.getTransaction().commit();
            assert em.contains(entity);
            em.getTransaction().begin();
            em.detach(entity);
            assert !em.contains(entity);
            em.getTransaction().commit();
            assert !em.getTransaction().isActive();
        });




    }

}
