package parsers;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import repositories.AdressService;
import repositories.ClientService;
import models.*;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
public class SAXParsing {
    private final File file;
    private final ClientService clientservice;
    private final AdressService adrservice;

    public SAXParsing(ClientService clientservice, AdressService adrservice, File file) { 
        this.clientservice = clientservice;
        this.adrservice = adrservice;
        this.file = file;
    }
    
    
    
    
    public List<Clients> readXml(){
        List<Clients> clientslist = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            InnerSax innerparser = new InnerSax(clientslist);
            parser.parse(file, innerparser);   
        } catch (SAXException ex) {
            Logger.getLogger(SAXParsing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SAXParsing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SAXParsing.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientslist.forEach(c -> {
            System.out.println(c.getClientname().toString());
        });
        System.out.println("readSaxXml is done!");
        return clientslist;
    }
    
    private class InnerSax extends DefaultHandler {
        
        private List<Clients> clientsList;


        public InnerSax(List<Clients> clientsList) {
            this.clientsList = clientsList;
        }
        
        

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {    
            if(qName.equals("client")){
                String idclientstr = attributes.getValue("id");
                if(idclientstr.matches("[\\d]+")){
                    Integer clid = Integer.valueOf(idclientstr);
                    System.out.println(clid);
                    Clients client = new Clients();
                    client.setClientid(clid);
                    client.setClientname(attributes.getValue("clientname"));
                    client.setClienttype(attributes.getValue("clienttype"));
                    Date date;
                    date = new Date(Long.valueOf(attributes.getValue("regdate")));
                    client.setRegdate(date);
                    System.out.println(qName);
                    
                    clientsList.add(client);
                    
                }         
            } if(qName.equals("adress")){
                System.out.println(qName);
                String idadrstr = attributes.getValue("adressid");
                System.out.println("adrid = " + idadrstr);
                if(idadrstr.matches("[\\d]+")){
                    Integer adrid = Integer.valueOf(idadrstr);                                  
                    Adresses adress = new Adresses();
                    adress.setAdressid(adrid);
                    adress.setIp(attributes.getValue("ip"));
                    adress.setMac(attributes.getValue("mac"));
                    adress.setModel(attributes.getValue("model"));
                    adress.setLocationadress(attributes.getValue("loc"));
                    Integer clientid = Integer.parseInt(attributes.getValue("clientid"));                  
                    for(Clients cl : clientsList){
                        if(cl.getClientid().equals(clientid)){
                            Clients client;
                            client = cl;
                            adress.setClientid(client);
                            client.getAdressesSet().add(adress);
                        }
                    }       
                }              
            }      
        }            
    }           
}