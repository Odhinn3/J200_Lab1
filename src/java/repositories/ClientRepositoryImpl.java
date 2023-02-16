package repositories;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */

@Stateless
public class ClientRepositoryImpl implements ClientRepository {
    
    @PersistenceContext
    EntityManager em;
    
    
    
    @Override
    public List<Clients> findAllClients() {

        Query query =  em.createNamedQuery("Clients.findAll");
        return query.getResultList(); 
    }
    

    @Override
    public Clients findById(Integer id) {
        return em.find(Clients.class, id);
    }

    @Override
    public Integer saveClient(Clients client) {
        em.persist(client);
        em.flush();
        return client.getClientid();
    }

    @Override
    public void updateClient(Clients client) {
        em.merge(client);
        em.flush();
    }    

    @Override
    public List<Clients> findByClientname(String clientname) {
        String sql = "SELECT * FROM \"J200\".\"Clients\" WHERE clientname='" + clientname + "'";
        return em.createNativeQuery(sql, Clients.class).getResultList();
    }

    @Override
    public List<Clients> findByClientaddress(String address) {
        String sql = "select * from \"J200\".\"Clients\" c\n" +
            "join \"J200\".\"Addresses\" a\n" +
            "on a.clientid = c.clientid\n" +
            "where a.locationaddress ='" + address + "'";
        return em.createNativeQuery(sql, Clients.class).getResultList();
    }
    
    @Override
    @Transactional
    public void deleteClient(Clients client) {
        if(!em.contains(client)){
            client = em.merge(client);
        } em.remove(client);
    }
    
    @Override
    public int getMaxId(){
        return (Integer) em.createNativeQuery("select max(clientid) from j200.clients;").getSingleResult();
    } 
}