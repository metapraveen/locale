<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8"%>
<%@include file="messages.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tra.*"%>
<%@ page import="java.util.regex.Matcher"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.ResourceBundle" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<style>
		body
		{
			font-size:75%;
			font-family:verdana,arial,'serif';
			background-color:#FFFFF0;
			color:#000080;
			margin:10px;
		}
		input.save_button{color:red;}
		input.cancel_button{color:red;}
	</style>
		<script language="javascript">
			function checkLanguage(){
				var l_lang;
				var myVar = '<%= request.getLocale() %>';
				  if (navigator.language){ //  For FireFox
				    l_lang = navigator.language;
				    alert("Language is "+l_lang);
				  }
				  else if (navigator.userLanguage) //  For IE
				    l_lang = navigator.userLanguage;
				  else
				    l_lang = "en";

			}
			function checkField()
				{
				  validCharsString=/^[a-zA-Z.]+$/;
				  var fname = document.getElementById("fName").value;
				  var lname = document.getElementById("lName").value;
				  if (!(fname.match(/[\u3041-\u3096\u30A0-\u30FF\u3400-\u4DB5\u4E00-\u9FCB\uF900-\uFA6A.]+/g) && lname.match(/[\u3041-\u3096\u30A0-\u30FF\u3400-\u4DB5\u4E00-\u9FCB\uF900-\uFA6A.]+/g)))
				  {
					return false;
				  }
				  else
					return true;
				
				}
			function saveUser(){
				var fname = document.getElementById("fName").value;
				var lname = document.getElementById("lName").value;
				if(fname == "" || fname == null || lname == "" || lname == null){
					alert("Input field cannot be null");
				}
				document.userForm.action="RegisterServlet";
				document.userForm.op.value="save";
				document.userForm.submit();
				}
		
			function updateUser(){
				document.userForm.action="RegisterServlet";
				document.userForm.op.value="save";
				document.userForm.submit();
			}
		</script>
		<title>Insert title here</title>
	</head>
	<body>
		<%
		ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", request.getLocale());
		%>
		<h1><%=rb.getString("locale.user.list")%></h1>
		<form name="userForm" method="post">
			<input type="hidden" name="userId" id="userId" value='<%=request.getAttribute("usr") == null ? "0" : ((User)request.getAttribute("usr")).getUserId()%>'/>
			<input type="hidden" name="op" id="op"/>
			<% 
			if(request.getAttribute("op").equals("add")){
			%>
				<input type="hidden" name="userId" id="userId">
				<%=rb.getString("locale.user.fname")%>:&nbsp;<input type="text" name="fName" id="fName"/><br/>
				<%=rb.getString("locale.user.lname")%>:&nbsp;<input type="text" name="lName" id="lName"/><br/>
				<input name="bSave" type="button" onClick="saveUser()" class="save_button" value='<%=rb.getString("locale.save")%>'>
				<input name="bCancel" type="button" onClick="back();" class="cancel_button" value='<%=rb.getString("locale.cancel")%>'>
			<%
			}else if(request.getAttribute("op").equals("edit")){
			%>
				<%=rb.getString("locale.user.fname")%>:<input type="text" name="fName" value='<%=((User)request.getAttribute("usr")).getFname()%>' id="fName" name="fName"/><br/>
				<%=rb.getString("locale.user.lname")%>:<input type="text" name="lName" value='<%=((User)request.getAttribute("usr")).getLname()%>' id="lName" name="lName"/><br/>
				<input name="bSave" type="button" onClick="updateUser()" class="save_button" value='<%=rb.getString("locale.update")%>'>
				<input name="bCancel" type="button" onClick="back();" class="cancel_button" value='<%=rb.getString("locale.cancel")%>'>
			<%
			} 
			%>
		</form>
	</body>
</html>