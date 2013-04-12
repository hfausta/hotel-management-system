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
<title>Hotel Booking System - Consumer - Manage Booking</title>
</head>
<body>
<%
ArrayList<String> typesAvailable = (ArrayList<String>) request.getSession().getAttribute("typesAvailable");
ArrayList<Integer> numofRoomsAvailableForType = (ArrayList<Integer>) request.getSession().getAttribute("numofRoomsAvailableForType");
ArrayList<Double> pricePerRoomType = (ArrayList<Double>) request.getSession().getAttribute("pricePerRoomType");
ArrayList<BookingRecordBean> records = (ArrayList<BookingRecordBean>) request.getSession().getAttribute("records");
BookingBean booking = (BookingBean) request.getSession().getAttribute("booking");
HotelBean hotel = (HotelBean) request.getSession().getAttribute("hotel");
%>
<form name ="logout" action = "ConsumerHomeServlet">
<input type = "submit" value = "Logout">
</form>
<font size = 12><b>Booking Details</b></font><br>
<table width="50%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td>Booking ID :</td>
		<td><%=booking.getId() %></td>
	</tr>
	<tr>
		<td>Customer ID : </td>
		<td><%=booking.getCustomerid()%></td>
	</tr>
	<tr>
		<td>Hotel Name : </td>
		<td><%=hotel.getName() %></td>
	</tr>
	<tr>
		<td>Hotel Address : </td>
		<td><%=hotel.getAddress() %></td>
	</tr>
	<tr>
		<td>Start Date : </td>
		<td><%=booking.getStart() %></td>
	</tr>
	<tr>
		<td>End Date : </td>
		<td><%=booking.getEnd() %></td>
	</tr>
	<tr></tr><tr></tr>
</table>
<table width="50%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td><font size = 12><b>Price List</b></font></td>
	</tr>
	<form name = "deleteBooking" action = "DeleteBookingConfirmServlet">
	<%
		double tot = 0;
		for(BookingRecordBean b : records) {
			tot = tot + b.getPrice();
			%>
			<tr>
				<td><%=b.getRoomtype() %></td>
				<%
				if(b.isExtrabed()) {
					out.print("<td>" + "with extra bed (+30)" + "</td>");
					tot = tot + 30;
				} else {
					out.print("<td></td>");
				}
				%>
				<td><%=b.getPrice() %></td>
				<td><input type=checkbox name="deleteRecord" value = "<%=b.getId()%>"></td>
				<td><input type="submit" value = "Remove"></td>
			</tr>
			<%
		}
	%>
	<tr>
		<td>Total Price : </td>
		<td></td>
		<td><%=tot %></td>
	</tr>
	</form>
	<tr></tr><tr></tr>
</table>
<table width="80%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td><font size = 12><b>Add Room To Booking</b></font></td>
	</tr>
	<tr>
	<form name = "addBooking" action = "AddBookingConfirmServlet">
		<%
		if(typesAvailable.isEmpty()) {
			out.println("<td>There is No Rooms Available<td>");
		} else {
			%>
			<td><select name = "type">
			<%
			int index = 0;
			for(String t : typesAvailable) {
				%>
				<option value = "<%=t%>"><%=t%>-$<%=pricePerRoomType.get(index) %></option>
				<%
				index++;
			}
			%>
			</select></td>
			<td>Extra Bed - $30 Extra<input type=checkbox name="addRecord" value = "extrabed"></td>
			<td><input type="submit" value = "Add Room"></td>
			<%
		}
		%>
	</form>
	</tr>
</table>
</body>
</html>