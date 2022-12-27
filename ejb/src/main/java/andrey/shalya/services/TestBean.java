package andrey.shalya.services;

import jakarta.inject.Named;
import jdk.jfr.Name;

import javax.enterprise.context.ApplicationScoped;

@Named("testBean")
@ApplicationScoped
public class TestBean {
    public int get(){
        return 666;
    }
}
