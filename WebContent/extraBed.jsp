<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.enterprise.beans.*"%>
<%@ page import="com.enterprise.web.helper.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking Request - Extra Bed</title>
</head>
<body>
<form name = "homeButton" action = "ConsumerHomeServlet">
<input type = "SUBMIT" value = "Home">
</form>
<form name ="pickExtraBed" action = "ExtraBedServlet">
<font size = 12><b>Extra Bed</b></font><br>
	Tick The Check Box if You Would Like to Add Extra Bed for The Specified Room Type<br><br>
	<table width="50%" border="0" cellspacing="5" cellpadding="0">
<%
//loop will depend on the room type from previous servlet
ArrayList<String> typesSelected  = (ArrayList<String>)request.getSession().getAttribute("typesSelected");
ArrayList<BookingRecordBean> records = (ArrayList<BookingRecordBean>)request.getSession().getAttribute("bookingRecords");
if(records == null || typesSelected == null){
	request.setAttribute("error", "Please start from this page.");
	request.getRequestDispatcher("/ConsumerHomeServlet").forward(request,response);
}
else{
int count = 1;
for(BookingRecordBean r : records) {
	out.println("<tr><td>");
	String[] split = r.getRoomtype().split("\\(");
	out.println("Room " + count + " " + split[0] + " </td>");
	if(!r.getRoomType().contains("Single Room")) {
		out.println("<td>Add Extra Bed");
%>
	<input type=checkbox name="extraBed" value = "<%=count-1%>"></td>
<%		
	}
	count++;
	out.println("</td></tr>");
}
%>
<tr></tr><tr></tr><tr>
<td><input type = "SUBMIT" value = "Submit Extra Bed Requests"></td>
</tr>
</table>
<% } %>
</form>
</body>
</html>