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
import com.supinfo.suplink.util.CheckNumber;

@WebServlet("/auth/links/*")
public class StateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String delimiter = "[/]";
        String[] tokens = url.split(delimiter);

        if(CheckNumber.checkIfNumber(tokens[4]) && (tokens[5].equals("true") || tokens[5].equals("false"))) {
        	if(tokens[5].equals("true")) {
        		LinkDao linkDao = DaoFactory.getLinkDao();
        		
        		Link link = linkDao.findById(Long.parseLong(tokens[4]));
        		link.setState(false);

            	linkDao.update(link);
            	response.sendRedirect( request.getContextPath()+"/auth/links" );
        	}
        	else {
        		LinkDao linkDao = DaoFactory.getLinkDao();
        		
        		Link link = linkDao.findById(Long.parseLong(tokens[4]));
        		link.setState(true);

            	linkDao.update(link);
            	response.sendRedirect( request.getContextPath()+"/auth/links" );
        	}
        }
        else {
        	response.sendRedirect( request.getContextPath()+"/auth/links" );
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet( request, response );
	}

}
