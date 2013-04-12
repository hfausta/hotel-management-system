<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Hotel Booking Reservation System - Hotel
	Manager Login</title>
</head>
<body>
	<form name="hotelManagerLogin" action="HotelManagerLoginServlet" method = "post">
		<table width="30%" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td><center>
						<font size=12><b>Hotel Manager Login</b></font>
					</center></td>
			</tr>
			<tr>
				<td><center>
						Username <input id="username" type="text" name="username"
							size="10">
					</center></td>
			</tr>
			<tr>
				<td><center>
						Password <input id="password" type="password" name="password"
							size="10">
					</center></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><center>
						<input type="SUBMIT" value="Login">
					</center></td>
			</tr>
		</table>
	</form>
</body>
</html>