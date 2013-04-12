<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.enterprise.beans.*"%>
<%@ page import = "com.enterprise.dao.support.*" %>
<%@ page import="com.enterprise.web.helper.*"%>
<%@ page import="com.enterprise.common.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking System - Removing Confirmation</title>
</head>
<body>
	<font size = 6><center><b>Confirm to Remove Records</b></center></font><br>
	<form name = "cancel" action = "ViewBookingServlet">
		<center><input type = "submit" value = "Cancel"></center>
	</form>
	<form name = "confirm" action = "DeleteBookingServlet">
		<center><input type = "submit" value = "Confirm"></center>
	</form>
</body>
</html>