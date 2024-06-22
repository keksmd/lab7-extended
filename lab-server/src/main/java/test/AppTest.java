package test;

import com.alexkekiy.server.data.entities.ChapterEntity;
import com.alexkekiy.server.data.entities.CoordinatesEntity;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.data.entities.Weapon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppTest {
    private EntityManager em;

    @Before
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("local");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @Test
    public void shouldPersistSpaceMarine() {
        SpaceMarineEntity spm = new SpaceMarineEntity("sp", new CoordinatesEntity(1L, 0.0f), 3L, true, 5f, Weapon.COMBI_PLASMA_GUN, new ChapterEntity("biba", "boba"));
        em.persist(spm);
        assertTrue(em.contains(spm));
        em.detach(spm);
        assertFalse(em.contains(spm));
    }


    @After
    public void close() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }
}
