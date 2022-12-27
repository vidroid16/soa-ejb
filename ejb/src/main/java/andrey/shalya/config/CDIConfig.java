package andrey.shalya.config;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class CDIConfig {

//    @Produces
//    @PersistenceContext
//    private EntityManager makeEntityManager(){
//        EntityManagerFactory entityManagerFactory =
//                Persistence.createEntityManagerFactory("spring");
//        return entityManagerFactory.createEntityManager();
//    }

    @Produces
    @PersistenceContext(unitName = "spring")
    private EntityManager entityManager;
}
