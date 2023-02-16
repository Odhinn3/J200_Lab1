package parsers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.Clients;
import repositories.AdressService;
import repositories.ClientService;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class Parser {
    
    @EJB
    ClientService clientservice;
    @EJB
    AdressService adrservice;
    
    public void createXmlFile(List<Clients> clients){
        new DOMParsing(clientservice, adrservice, getXmlFile()).createFile(clients);      
    }
    
    public List<Clients> readDomXml(){
        return new DOMParsing(clientservice, adrservice, getXmlFile()).readXml();
    }
    
    public List<Clients> readSaxXml(){
        return new SAXParsing(clientservice, adrservice, getXmlFile()).readXml();
    }
    
    private File getXmlFile(){
        File xmlfile = new File("d://JavaDev/clients.xml");
        if(!xmlfile.exists()) try {
            xmlfile.createNewFile(); 
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlfile;
    }
}