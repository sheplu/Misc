package com.supinfo.suplink.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "click")
public class Click {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String country;
    private String referer;
    private String date;
    
    @ManyToOne
    @JoinColumn(name="link_fk")
    private Link link;
    
    //getter
    public Long getId() {
    	return id;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getReferer() {
    	return referer;
    }
    
    public String getDate() {
    	return date;
    }
    
    //setter
    public void setId( Long id ) {
        this.id = id;
    }

    public void setCountry( String country ) {
        this.country = country;
    }
    
    public void setReferer( String referer ) {
    	this.referer = referer;
    }
    
    public void setDate(String date) {
    	this.date = date;
    }
    
    public Link getLink() {
        return link;
    }
    
    public void setLink( Link link ) {
        this.link = link;
    }
}
