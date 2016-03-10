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
import com.supinfo.suplink.util.Encrypt;
import com.supinfo.suplink.util.Mail;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static final String PARAM_PSEUDO = "pseudo";
	public static final String PARAM_MAIL = "mail";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_CONFIRM = "confirm";
	public static final String VIEW = "/WEB-INF/views/register.jsp";
	public static final String LOGIN = "/login";
	public static final String REGISTER = "/register";
	

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( VIEW ).forward( request, response );
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String pseudo = request.getParameter( PARAM_PSEUDO );
    	String mail = request.getParameter( PARAM_MAIL );
    	String password = request.getParameter( PARAM_PASSWORD );
    	String confirm = request.getParameter( PARAM_CONFIRM );
    	
    	if(!(password.equals(confirm)) || password == null || password.isEmpty() || pseudo == null || pseudo.isEmpty() || mail == null || mail.isEmpty()) {
    		response.sendRedirect( request.getContextPath()+ REGISTER );
    	}
    	else {
    		String concat = pseudo + password;
    		concat = Encrypt.encryptPassword(concat).substring(0,6);
    		
    		User user = new User();
        	user.setName( pseudo );
        	user.setMail( mail );
        	user.setPassword( password );
        	user.setActivated(concat);
        	
        	UserDao userDao = DaoFactory.getUserDao();
        	Long ifExist = userDao.findIfExist(mail);
        	if(ifExist == 0) {
        		userDao.add( user );
        		Mail email = new Mail();
        		email.send("envoyeur", mail, "Nouveau compte SupLink", "Votre lien d'activation SupLink est http://localhost:8080/SupLink/activated/" + concat );
        		response.sendRedirect( request.getContextPath()+ LOGIN );
        	}
        	else {
        		response.sendRedirect( request.getContextPath()+ REGISTER );
        	}       
    	}
    }

}
