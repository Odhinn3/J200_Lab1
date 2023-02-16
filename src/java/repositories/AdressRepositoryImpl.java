package repositories;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.Adresses;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class AdressRepositoryImpl implements AdressRepository {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Adresses> findByClientId(Integer clientid) {
        List<Adresses> adresslist = new ArrayList<>(em.find(Clients.class, clientid).getAdressesSet());
        return adresslist;  
    }
    
    @Override
    public Adresses findAdressById(int adressid) {
        Adresses adress = em.find(Adresses.class, adressid);
        if (adress!= null){
            return adress;
        } else {
            return null;
        }
    }

    @Override
    public Integer saveAdress(Adresses adress) {
        em.persist(adress);
        em.flush();
        return adress.getAdressid();
    }

    @Override
    public void updateAdress(Adresses adress) {
        em.merge(adress);
        em.flush();
    }

    @Override
    public void deleteAdress(Adresses adress) {
        if(!em.contains(adress)){
            adress = em.merge(adress);
        } em.remove(adress);
    }
    
    @Override
    public int getMaxId(){
        return (Integer) em.createNativeQuery("select max(adressid) from j200.adresses;").getSingleResult();
    }   
}