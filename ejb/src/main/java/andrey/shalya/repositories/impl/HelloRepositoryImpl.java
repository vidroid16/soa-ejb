package andrey.shalya.repositories.impl;

import andrey.shalya.models.HelloEntity;
import andrey.shalya.repositories.HelloRepository;
import jakarta.inject.Named;
import org.eclipse.persistence.jpa.PersistenceProvider;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;

public class HelloRepositoryImpl implements HelloRepository {

    public HelloRepositoryImpl(){
        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("spring");
        em = entityManagerFactory.createEntityManager();
    }
    private final EntityManager em;

    @Override
    public void create(HelloEntity entity) {
        em.getTransaction().begin();
        em.persist(entity);
        int a = 4;
        em.getTransaction().commit();
    }
}
