package com.supinfo.suplink.dao;

import com.supinfo.suplink.bean.User;

public interface UserDao {
	abstract User add(User user);
	abstract Long findIfExist(String mail);
	abstract User findConnectable(User user);
	abstract User findByActivated(String activated);
	abstract void update(User user);
}
