package com.supinfo.suplink.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.bean.User;
import com.supinfo.suplink.dao.DaoFactory;
import com.supinfo.suplink.dao.UserDao;

/**
 * Servlet implementation class ActivatedServlet
 */
@WebServlet("/activated/*")
public class ActivatedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
        String delimiter = "[/]";
        String[] tokens = url.split(delimiter);
        UserDao userDao = DaoFactory.getUserDao();
		
		User user = userDao.findByActivated(tokens[3]);
		user.setActivated("1");

    	userDao.update(user);
    	response.sendRedirect( request.getContextPath()+"/login" );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet( request, response );
	}

}
