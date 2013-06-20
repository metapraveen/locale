<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
%>

<h1><%=rb.getString("header.name")%></h1>
	<form action="RegisterServlet" method="post">
		<%=rb.getString("user.fname")%>:<input type="text" name="fname"/><br/>
		<%=rb.getString("user.lname")%>:<input type="text" name="lname"/><br/>
										<input type="submit"/>
	</form>
</body>

</html>