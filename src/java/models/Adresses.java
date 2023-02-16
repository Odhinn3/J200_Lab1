package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Entity
@Table(name = "adresses", catalog = "postgres", schema = "J200")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresses.findAll", query = "SELECT a FROM Adresses a"),
    @NamedQuery(name = "Adresses.findByClientid", query = "SELECT a FROM Adresses a WHERE a.clientid = :clientid"),
    @NamedQuery(name = "Adresses.findByIp", query = "SELECT a FROM Adresses a WHERE a.ip = :ip"),
    @NamedQuery(name = "Adresses.findByMac", query = "SELECT a FROM Adresses a WHERE a.mac = :mac"),
    @NamedQuery(name = "Adresses.findByModel", query = "SELECT a FROM Adresses a WHERE a.model = :model"),
    @NamedQuery(name = "Adresses.findByLocationadress", query = "SELECT a FROM Adresses a WHERE a.locationadress = :locationadress")})
public class Adresses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull
    @Basic(optional = false)
    @Column(name = "adressid")
    private Integer adressid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ip", length = 25)
    private String ip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mac", length = 20)
    private String mac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "model", length = 100)
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "locationadress", length = 200)
    private String locationadress;
    @JoinColumn(name = "clientid", referencedColumnName = "clientid")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clients clientid;

    public Adresses() {
    }

    public Adresses(String ip, String mac, String model, String locationadress) {  
        this.ip = ip;
        this.mac = mac;
        this.model = model;
        this.locationadress = locationadress;
    }
    
    public Adresses(Integer adressid, String ip, String mac, String model, String locationadress) {
        this.adressid = adressid;
        this.ip = ip;
        this.mac = mac;
        this.model = model;
        this.locationadress = locationadress;
    }

    public Integer getAdressid() {
        return adressid;
    }

    public void setAdressid(Integer adressid) {
        this.adressid = adressid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocationadress() {
        return locationadress;
    }

    public void setLocationadress(String locationadress) {
        this.locationadress = locationadress;
    }

    @XmlTransient
    public Clients getClientid() {
        return clientid;
    }

    public void setClientid(Clients clientid) {
        this.clientid = clientid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adressid != null ? adressid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresses)) {
            return false;
        }
        Adresses other = (Adresses) object;
        if ((this.adressid == null && other.adressid != null) || (this.adressid != null && !this.adressid.equals(other.adressid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Adresses{" + "adressid=" + adressid + ", ip=" + ip + ", mac=" + mac + ", model=" + model + ", locationadress=" + locationadress + '}';
    } 
}