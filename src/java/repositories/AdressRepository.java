
package repositories;

import java.util.List;
import models.Adresses;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
public interface AdressRepository {
    
    List<Adresses> findByClientId(Integer clientid);
    
    Adresses findAdressById(int adressid);
    
    Integer saveAdress(Adresses adress);
    
    void updateAdress(Adresses adress);
    
    void deleteAdress(Adresses adress); 
    
    int getMaxId();
}