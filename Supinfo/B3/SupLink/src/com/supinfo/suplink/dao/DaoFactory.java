package com.supinfo.suplink.dao;

import com.supinfo.suplink.dao.jpa.JpaLinkDao;
import com.supinfo.suplink.dao.jpa.JpaUserDao;
import com.supinfo.suplink.util.PersistenceManager;


public class DaoFactory {
	public static UserDao getUserDao() {
	       return new JpaUserDao(PersistenceManager.getEntityManagerFactory());
	}
	
	public static LinkDao getLinkDao() {
	       return new JpaLinkDao(PersistenceManager.getEntityManagerFactory());
	} 
}
