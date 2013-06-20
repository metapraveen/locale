<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
%>
<h1><%=rb.getString("editheader.name")%></h1>
<form action="DisplayServlet" name="displayData">
		<%=rb.getString("user.fname")%>:<input type="text" name="fn" value='<%=request.getParameter("fn")%>' id="id" name="id"/><br/>
		<%=rb.getString("user.lname")%>:<input type="text" name="ln"  value='<%=request.getParameter("ln")%>'/><br/>
										<input type="submit"/>
</form>
</body>
</html>