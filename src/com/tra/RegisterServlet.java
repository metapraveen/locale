package com.tra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.transerainc.tam.util.HttpStatus;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet implements Constants {
	private static final long serialVersionUID = 1L;
	private static final Logger lgr = LoggerFactory.getLogger(RegisterServlet.class);
	//private static Logger lgr = Logger.getLogger("com.tra.RegisterServlet");

	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
		Locale locale = request.getLocale();
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = null;
		User usr = new User();
		RegisterService rsr = new RegisterService();
		System.out.println("here  ");
		System.out.println(request.getParameter("op"));
		if (request.getParameter("op") == null) {
			try{
				lgr.trace("Hello World!");
				lgr.debug("How are you today?");
				lgr.info("I am fine.");
				lgr.warn("I love programming.");
				lgr.error("I am programming.");
			}catch (Exception e) {
				System.out.println("logging error");
				System.out.println(e);
			}
			
			request.setAttribute("listOfUsers", rsr.getUsers());
			rd = request.getRequestDispatcher("ListUsers.jsp");
			rd.forward(request, response);
		} else if (request.getParameter("op").equals("save")) {
			usr.setUserId(Integer.parseInt(request.getParameter("userId") == null
					|| request.getParameter("userId").equals("") ? "0"
					: request.getParameter("userId")));
			usr.setFname(request.getParameter("fName"));
			usr.setLname(request.getParameter("lName"));
			rsr.saveUser(usr);
			String httpResponse = "";
			/*try {
				String url = "http://10.1.1.219:8080/locale/JAXBUtils";
				String xml = JAXBHelper.marshal(usr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("xml", xml);
				HttpStatus status = HttpUtil.doHttpPost(url, params);
				if (status.getResponse() == null
						|| status.getResponse().trim().equals("")) {
					throw new IllegalArgumentException(
							"No/Invalid response from back-end.");
				}
			} catch (JAXBException e) {
				lgr.warn("Cannot marshall the object", e);
			} catch (IllegalArgumentException e) {
				lgr.warn("No response from the servlet", e);
			} catch (Exception e) {
				lgr.warn("Connection failed", e);
			}
			try{
			//	MailUtil.sendEmail(locale);
			}
			catch(Exception e){
			//	lgr.warn("Could not send the email", e);
			}*/
			// handle error condn here
			request.setAttribute("listOfUsers", rsr.getUsers());
			rd = request.getRequestDispatcher("ListUsers.jsp");
			rd.forward(request, response);
		} else if (request.getParameter("op").equals("edit")) {
			usr.setUserId(Integer
					.parseInt(request.getParameter("userId") == null ? "0"
							: request.getParameter("userId")));
			usr = rsr.getUserById(usr);
			request.setAttribute("op", "edit");
			request.setAttribute("usr", usr);
			rd = request.getRequestDispatcher("AddEditUsers.jsp");
			rd.forward(request, response);
		} else if (request.getParameter("op").equals("delete")) {
			usr.setUserId(Integer
					.parseInt(request.getParameter("userId") == null ? "0"
							: request.getParameter("userId")));
			rsr.deleteUser(usr);
			request.setAttribute("listOfUsers", rsr.getUsers());
			rd = request.getRequestDispatcher("ListUsers.jsp");
			rd.forward(request, response);
		} else if (request.getParameter("op").equals("add")) {
			request.setAttribute("op", "add");
			rd = request.getRequestDispatcher("AddEditUsers.jsp");
			rd.forward(request, response);
		}
	}
}
