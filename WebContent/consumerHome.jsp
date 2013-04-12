<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.enterprise.beans.*"%>
<%@ page import="com.enterprise.web.helper.*"%>
<html>
<script language="javascript" type="text/javascript" src="dateTime.js">
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Hotel Booking Reservation System</title>
</head>
<body>
<form name ="createBookingQuery" action = "BookingQueryServlet">
<%
ArrayList<HotelBean> hotels = (ArrayList<HotelBean>) request.getAttribute("hotels");
if(hotels == null){
	request.getRequestDispatcher("/ConsumerHomeServlet").forward(request, response);
}
%>
<table width="30%" border="0" cellspacing="5" cellpadding="0">
<%
hotels = (ArrayList<HotelBean>) request.getAttribute("hotels");
%>
<tr>
	<td><b><center><font size = 12><%out.println("Select Hotel");%></font></b></center></td>
</tr>
<tr>
	<td><center><%out.println("<select name= \"hotelSelected\">");
	for(HotelBean h : hotels) {
		out.println("<option value =" + h.getId() + ">" + h.getName() + "-"+h.getAddress() + "</option>");
	}
	%></center></td>
</tr>
</SELECT>	
<tr></tr>
<tr></tr>
<tr></tr>
<tr>
	<td><center><font size = 12><b>Insert Booking Date</b></font></center></td>
</tr>
<tr>
	<td><center>Start Date
	<input id="startDate" type="text" name="startDate" readonly = "readonly" size = "10"><a href="javascript:NewCal('startDate','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></center></td>
</tr>
<tr>
	<td><center>End Date
	<input id="endDate" type="text" name="endDate" readonly = "readonly" size = "10"><a href="javascript:NewCal('endDate','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></center></td>
</tr>
</SELECT>
<tr></tr>
<tr></tr>
<tr></tr>
<tr>
	<td><center><input type = "SUBMIT" value = "Submit Booking Request"></center></td>
</tr>
</table>
<%
String error = (String)request.getAttribute("error");
if(error != null && error != ""){
	out.println(error + "<br>");
	request.removeAttribute("error");
}
%>
</form>	
</body>
</html>