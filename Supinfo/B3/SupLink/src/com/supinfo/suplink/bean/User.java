package com.supinfo.suplink.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.supinfo.suplink.util.Encrypt;

@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String mail;
    private String password;
    private String activated;
    private String deleted;
    
    @OneToMany(mappedBy="user")
    private List<Link> links;

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getMail() {
    	return mail;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public String getActivated() {
    	return activated;
    }
    
    public String getDeleted() {
    	return deleted;
    }
    
    
    //setter
    public void setId( Long id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }
    
    public void setMail( String mail ) {
    	this.mail = mail;
    }
    
    public void setPassword( String password ) {
    	this.password = Encrypt.encryptPassword(password);
    }
    
    public void setActivated( String activated ) {
    	this.activated = activated;
    }
    
    public void setDeleted( String deleted ) {
    	this.deleted = deleted;
    }
    
    
    
    public List<Link> getLink() {
        return links;
    }
    
    public void setLink( Link link ) {
    	this.links.add(link);
    }
}