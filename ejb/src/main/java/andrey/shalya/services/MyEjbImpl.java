package andrey.shalya.services;

import andrey.shalya.models.HelloEntity;
import andrey.shalya.repositories.HelloRepository;
import andrey.shalya.repositories.impl.HelloRepositoryImpl;
import jakarta.inject.Inject;
import jakarta.inject.Qualifier;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.BeanManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Stateless
@Remote(MyEjb.class)
@Dependent
public class MyEjbImpl implements MyEjb {

    HelloRepositoryImpl helloRepository;

    public MyEjbImpl(){
        helloRepository = new HelloRepositoryImpl();
    }

    public String test(String name) {
        HelloEntity entity = new HelloEntity();
        entity.setName("fcngEJB");
        entity.setId(666L);
        helloRepository.create(entity);
        return "Hello ".concat(name);
    }
}
