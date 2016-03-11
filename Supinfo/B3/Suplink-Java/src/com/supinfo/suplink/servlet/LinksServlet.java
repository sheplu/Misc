package com.supinfo.suplink.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.supinfo.suplink.bean.Link;
import com.supinfo.suplink.bean.User;
import com.supinfo.suplink.dao.DaoFactory;
import com.supinfo.suplink.dao.LinkDao;
import com.supinfo.suplink.util.Encrypt;

@WebServlet("/auth/links")
public class LinksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String PARAM_NAME = "name";
	public static final String PARAM_URL = "url";
	public static final String ATT_SESSION_USER = "user";
	public static final String ATT_LINK = "links";
	public static final String VIEW = "/WEB-INF/views/links.jsp";
	public static final String LINKS = "/auth/links";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User user = new User();
        user = (User) session.getAttribute(ATT_SESSION_USER);
        
        LinkDao linkDao = DaoFactory.getLinkDao();
        List<Link> links = linkDao.findAll(user);
        
        request.setAttribute(ATT_LINK, links);
        
		request.getRequestDispatcher( VIEW ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter(PARAM_NAME);
    	String url = request.getParameter(PARAM_URL);
    	
    	if(name == null || name.isEmpty() || url == null || url.isEmpty()) {
    		response.sendRedirect( request.getContextPath()+ LINKS );
    	}
    	else {
    		Link link = new Link();
    		Date date = new Date();
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		
    		HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            
            String shortened = name + url;
    		int iShortened = Integer.parseInt(Encrypt.encryptPassword(shortened).replaceAll("[a-zA-Z]", "").substring(0,6));
    		
    		link.setName(name);
    		link.setUrl(url);
    		link.setShortened(iShortened);
    		link.setUser((User) session.getAttribute( ATT_SESSION_USER ));
    		link.setDate(formatter.format(date.getTime()));
    		link.setState(true);
    		link.setDeleted(false);
    		
        	LinkDao linkDao = DaoFactory.getLinkDao();
        	linkDao.add( link );
        	response.sendRedirect( request.getContextPath()+ LINKS );      
    	}
	}
	
	

}
