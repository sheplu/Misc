package com.supinfo.suplink.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.suplink.bean.Link;
import com.supinfo.suplink.dao.DaoFactory;
import com.supinfo.suplink.dao.LinkDao;
import com.supinfo.suplink.util.CheckNumber;

@WebFilter( urlPatterns = "/*" )
public class RedirectFilter implements Filter {
    
    public void init( FilterConfig config ) throws ServletException {
    }

    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException,ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
    	String url = req.getRequestURI();
        String delimiter = "[/]";
        String[] tokens = url.split(delimiter);
        
        if(tokens.length < 3) {
        	res.sendRedirect( req.getContextPath()+"/login" );
            return;
        }
        else {
        	if(CheckNumber.checkIfNumber(tokens[2]) == false) {
        		chain.doFilter( req, res );
        	}
        	else {
        		Link link = new Link();
        		LinkDao linkDao = DaoFactory.getLinkDao();
        		link.setShortened(Integer.parseInt(tokens[2]));
        		link = linkDao.findLink(link);
        		
                if(link == null) {
                	res.sendRedirect( req.getContextPath()+"/login" );
                }
                else {
                	String redirect = link.getUrl();
                	redirect = redirect.replaceAll("[http://]", "");
                	res.sendRedirect("http://" + redirect);
                }
        	}
        }    
    }

    public void destroy() {
        
    }
}
