package com.supinfo.suplink.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "link")
public class Link
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    private String name;
    private String url;
    private Integer shortened;
    private String date;
    private Boolean state;
    private Boolean deleted;
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;
    
    @OneToMany(mappedBy="link")
    private List<Click> clicks;

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getUrl() {
    	return url;
    }
    
    public Integer getShortened() {
    	return shortened;
    }
    
    public String getDate() {
    	return date;
    }
    
    public Boolean getState() {
    	return state;
    }
    
    public Boolean getDeleted() {
    	return deleted;
    }
    
    
    //setter
    public void setId( Long id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }
    
    public void setUrl( String url ) {
    	this.url = url;
    }
    
    public void setShortened( Integer shortened ) {
    	this.shortened = shortened;
    }
    
    public void setDate(String date) {
    	this.date = date;
    }
    
    public void setState(Boolean state) {
    	this.state = state;
    }
    
    public void setDeleted(Boolean deleted) {
    	this.deleted = deleted;
    }
    
    public User getUSer() {
        return user;
    }
    
    public void setUser( User user ) {
        this.user = user;
    }
}