<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.tra.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript">
var choosen = "";
			function setSelected(selectedValue) {
				choosen = selectedValue;
			}
			function editUser () {
	       		var arr = choosen.split(';');
	       		document.getElementById("fn").value = arr[0];
	       		document.getElementById("ln").value = arr[1];
				document.displayData.submit();
					
			}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Of Users</title>
</head>
<body>
<%
ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
%>

	<h1><%=rb.getString("user.list")%></h1>
	<%	
	List<User> listOfUsers = (List<User>)request.getAttribute("listOfUsers");
	out.print(listOfUsers.size());
	%>
	<form action="editUser.jsp" name="displayData" method="get">
	<input type="hidden" name="fn" id="fn">
	<input type="hidden" name="ln" id="ln">
	<input type="button" name="method" value="Edit" class="button" onclick="editUser();" />
	<table border="1">
	<tr>
		<td>&nbsp;</td>
		<td>First Name</td>
		<td>Last Name</td>
	</tr>
	<c:forEach items="${listOfUsers}" var="us" varStatus="i">
		<tr>
			
			<td><input type="radio" name="sel" value="${us.fname};${us.lname}" id="sel" onclick='setSelected("${us.fname};${us.lname}");'/></td>
			<td><c:out value="${us.fname}"/></td>
			<td><c:out value="${us.lname}"/></td>
		</tr>
	</c:forEach>
	</table>
	</form>
</body>
</html>