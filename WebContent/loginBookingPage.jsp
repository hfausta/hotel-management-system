<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking System - Consumer Booking</title>
</head>
<body>
<%
String error = (String)request.getAttribute("error");
if(error != null && error != ""){
	out.println(error + "<br>");
	request.removeAttribute("error");
}
else{
%>
<form name = "" action = "ViewBookingServlet" method = "post">
<font size = 6><center><b>Insert Your Booking PIN</b></center></font><br>
<center><input type = "TEXT" name = "PIN"></input></center>
<center><input type = "SUBMIT" value = "Login"></center>
</form>
<%} %>
</body>
</html>