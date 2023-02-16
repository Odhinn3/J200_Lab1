package repositories;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.Adresses;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class AdressesServiceImpl implements AdressService {
    
    @EJB
    AdressRepository repo;

    @Override
    public List<Adresses> findByClientId(Integer clientid) {
        return repo.findByClientId(clientid);
    }
    
    @Override
    public Adresses findAdressById(Integer adressid) {
        return repo.findAdressById(adressid);
    }

    @Override
    public Integer saveAdress(Adresses adress) {
        return repo.saveAdress(adress);
    }

    @Override
    public void updateAdress(Adresses adress) {
        repo.updateAdress(adress);
    }

    @Override
    public void deleteAdress(Adresses adress) {
        repo.deleteAdress(adress);
    }

    @Override
    public int getMaxId() {
        return repo.getMaxId();
    }    
}