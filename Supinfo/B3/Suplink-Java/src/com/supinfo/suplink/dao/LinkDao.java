package com.supinfo.suplink.dao;


import java.util.List;

import com.supinfo.suplink.bean.Link;
import com.supinfo.suplink.bean.User;

public interface LinkDao {
	abstract Link add(Link link);
	abstract Link findLink(Link link);
	abstract List<Link> findAll(User user);
	abstract void update(Link link);
	abstract Link findById(Long id);
	abstract void remove(Link link);
}
