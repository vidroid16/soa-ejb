package andrey.shalya.repositories;

import andrey.shalya.models.HelloEntity;

import javax.ejb.Local;

public interface HelloRepository {
    void create(HelloEntity entity);
}
