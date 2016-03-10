package com.supinfo.suplink.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.bean.Link;
import com.supinfo.suplink.dao.DaoFactory;
import com.supinfo.suplink.dao.LinkDao;

@WebServlet("/auth/links/delete/*")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
        String delimiter = "[/]";
        String[] tokens = url.split(delimiter);
        
        LinkDao linkDao = DaoFactory.getLinkDao();
        
		Link link = linkDao.findById(Long.parseLong(tokens[5]));
		link.setState(false);
		link.setDeleted(true);
    	
		linkDao.update(link);
    	
    	response.sendRedirect( request.getContextPath()+"/login" );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet( request, response );
	}

}
