package com.tra;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * Servlet implementation class JAXBUtils
 */
public class JAXBUtils extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger lgr = Logger.getLogger("com.tra.JAXBUtils");
       
    
    public JAXBUtils() {
        super();
        ;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		String xml = request.getParameter("xml");
		RegisterService rsr = new RegisterService();
		try{
		User newUsr = (User) JAXBHelper.unmarshal(User.class, new String(xml));
		rsr.saveUser(newUsr);
		}
		catch(JAXBException e){
			lgr.log(Level.WARNING, "Cannot unmarshall the object", e);
		}
		
		
	}

}
