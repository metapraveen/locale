
    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ResourceBundle,java.util.*,com.tra.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script language="JavaScript" type="text/JavaScript" src = "validations.js"></script>
		<script language="javascript">
			var choosen = "";
			function setSelected(selectedValue) {
				choosen = selectedValue;
			}
			function addUser() {
				document.userForm.op.value="add";
				document.userForm.action="RegisterServlet";
				document.userForm.submit();
			}
			function editUser () {
	      		document.userForm.userId.value=choosen;
	      		if(choosen == ""){
		      		alert("Please select the record to edit");
	      		}
	      		document.userForm.op.value="edit";
	      		document.userForm.action="RegisterServlet";	
				document.userForm.submit();
					
			}
			function deleteUser(){
				document.userForm.userId.value=choosen;
	      		if(choosen == ""){
		      		alert("Please select the record to delete");
	      		}
	      		document.userForm.op.value="delete";
	      		document.userForm.action="RegisterServlet";	
				document.userForm.submit();
			}
		</script>
		<style>
			body
			{
				font-size:80%;
				font-family:verdana,arial,'sans serif';
				background-color:#FFFFF0;
				color:#000080;
				margin:10px;
			}
			thead {background-color:green;}
			tr{background-color:#EAF2D3;}
			input.add_button{color:red;}
			input.edit_button{color:red;}
			input.delete_button{color:red;}
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="css/menu.css">
		<title>Insert title here</title>
	</head>
	<body>
		<%
		ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
		%>
		
		<h1 style="color:sienna;margin-left:20px"><%=rb.getString("locale.header.name")%></h1>
		<%
			List<User> listOfUsers = (List<User>)request.getAttribute("listOfUsers");
		%>
		<form name="userForm" method="post">
			<input type="hidden" name="op" id="op"/>
			<input type="hidden" name="userId" id="userId"/>
			<table id="users" width="50%" border="1" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<td width="5%" class="head_odd"><%=rb.getString("locale.select")%></td>
						<td width="10%" class="head_odd"><%=rb.getString("locale.user.fname")%></td>
						<td width="10%" class="head_odd"><%=rb.getString("locale.user.lname")%></td>
					</tr>
				</thead>
				<c:forEach items="${listOfUsers}" var="us" varStatus="i">
					<tr id="data">
						<td><input type="radio" name="sel" value="${us.userId}" id="sel" onclick='setSelected("${us.userId}");'/></td>
						<td><c:out value="${us.fname}"/></td>
						<td><c:out value="${us.lname}"/></td>
					</tr>
				</c:forEach>														
			</table>
			<input name="bAdd"	type="button" onclick="addUser();" class="add_button" value='<%=rb.getString("locale.add")%>'>
			<input name="bEdit" type="button" onclick="editUser();" class="edit_button" value='<%=rb.getString("locale.edit")%>'>
			<input name="bDelete" type="button" onclick="deleteUser();" class="delete_button" value='<%=rb.getString("locale.delete")%>'>
		</form>
	</body>
</html>