package com.tra;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger lgr = Logger.getLogger("com.tra.DisplayServlet");
    
    public DisplayServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		User usr = new User();
		usr.setUserId(Integer.parseInt(request.getParameter("id")));
		usr.setFname(request.getParameter("fn"));
		usr.setFname(request.getParameter("ln"));
		RegisterService registerservice = new RegisterService();
		/*List<User> u = registerservice.getUserById(usr);
		Iterator<User> iterator = u.iterator();
		while (iterator.hasNext()) {
			User us = (User)iterator.next();
			String result = registerservice.updateUser(us);
			if(result.equals(Constants.SUCCESS)){
				List<User> list = registerservice.getUsers();
				rd = request.getRequestDispatcher("ListUsers.jsp");
				request.setAttribute("listOfUsers", list);
				rd.forward(request, response);
			}
		}
		*/
	}

}
