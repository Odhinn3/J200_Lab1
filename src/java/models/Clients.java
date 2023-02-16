package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Entity
@Table(name = "clients", catalog = "postgres", schema = "J200")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findByClientid", query = "SELECT c FROM Clients c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Clients.findByClientname", query = "SELECT c FROM Clients c WHERE c.clientname = :clientname"),
    @NamedQuery(name = "Clients.findByClienttype", query = "SELECT c FROM Clients c WHERE c.clienttype = :clienttype"),
    @NamedQuery(name = "Clients.findByRegdate", query = "SELECT c FROM Clients c WHERE c.regdate = :regdate")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
//    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clientid")
    private Integer clientid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "clientname", length = 100)
    private String clientname;
    @Column(name = "clienttype", length = 20)
    private String clienttype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "regdate")
    @Temporal(TemporalType.DATE)
    private Date regdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid", fetch = FetchType.LAZY)
    private Set<Adresses> adressesSet = new HashSet<>();

    public Clients() {
    }

    public Clients(String clientname, String clienttype, Date regdate) {
        this.clientname = clientname;
        this.clienttype = clienttype;
        this.regdate = regdate;
    }
    
    public Clients(Integer clientid, String clientname, String clienttype, Date regdate) {
        this.clientid = clientid;
        this.clientname = clientname;
        this.clienttype = clienttype;
        this.regdate = regdate;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClienttype() {
        return clienttype;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

//    @XmlTransient
    public Set<Adresses> getAdressesSet() {
        return adressesSet;
    }

    public void setAdressesSet(Set<Adresses> adressesSet) {
        this.adressesSet = adressesSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientid != null ? clientid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.clientid == null && other.clientid != null) || (this.clientid != null && !this.clientid.equals(other.clientid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clients{" + "clientid=" + clientid + ", clientname=" + clientname + ", clienttype=" + clienttype + ", regdate=" + regdate + '}';
    }
}
