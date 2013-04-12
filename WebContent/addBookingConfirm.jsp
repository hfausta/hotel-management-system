<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking System - Adding Confirmation</title>
</head>
<body>
	<font size = 6><center><b>Confirm to Add Record</b></center></font><br>
	<form name = "cancel" action = "ViewBookingServlet">
		<center><input type = "submit" value = "Cancel"></center>
	</form>
	<form name = "confirm" action = "AddBookingServlet">
		<center><input type = "submit" value = "Confirm"></center>
	</form>
</body>
</html>