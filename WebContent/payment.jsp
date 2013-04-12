<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.enterprise.beans.*"%>
<%@ page import="com.enterprise.web.helper.*"%>
<%@ page import="com.enterprise.dao.support.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking Request - Payment Detail</title>
</head>
<body>
<form name = "homeButton" action = "ConsumerHomeServlet">
<input type = "SUBMIT" value = "Home">
</form>
	<form name="payment" action="PaymentServlet">
		<table width="80%" border="0" cellspacing="10" cellpadding="0">
			<tr>
				<td><font size=12><b>Booking Details</b></font></td>
			</tr>
			<%
				BookingBean booking = (BookingBean)session.getAttribute("booking");
				ArrayList<BookingRecordBean> bookingRecord = (ArrayList<BookingRecordBean>)session.getAttribute("bookingRecords"); 
				if(booking == null || bookingRecord == null){
					request.setAttribute("error", "Please start from this page.");
					request.getRequestDispatcher("/ConsumerHomeServlet").forward(request,response);				
				}
				else{
				%>
			<tr>
				<%
				out.print("<td>Hotel Name</td>");
				out.print("<td>" + request.getSession().getAttribute("hotelName") + "</td>");
				%>
			</tr>
			<tr>
				<%
				out.print("<td>Hotel Address</td>");
				out.print("<td>" + request.getSession().getAttribute("hotelAddress") + "</td>");
				%>
			</tr>
			<tr>
				<%
				out.print("<td>Start Date</td>");
				out.print("<td>" + booking.getStart() + "</td>");
				%>
			</tr>
			<tr>
				<%
				out.print("<td>End Date</td>");
				out.print("<td>" + booking.getEnd() + "</td>");
				%>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>Booking Price List</b></font></td>
			</tr>
			<%
				int i = 1;
				double price = 0;
				for(BookingRecordBean b : bookingRecord) {
					out.print("<tr>");
					out.print("<td>Room " + i + "</td>");
					String[] split = b.getRoomtype().split("\\(");
					out.print("<td>" + split[0] + "</td>");
					out.print("<td>$" + b.getPrice() + "</td>");
					price = price + b.getPrice();
					if(b.isExtrabed()) {
						out.print("<td>Excluding Extra Bed ($30 Extra)</td>");
						price = price + 30D;
					}
					out.print("</tr>");
					i++;
				}
				%>
			<tr>
				<td>Total Price :</td>
				<td></td>
				<td>
					<%out.print("$" + price); %>
				</td>
			</tr>
			<tr>
				<td><font size=12><b>Insert Your Details</b></font></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type="TEXT" name="name"></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><input type="TEXT" name="address"></td>
			</tr>
			<tr>
				<td>Phone</td>
				<td><input type="TEXT" name="phone"></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="TEXT" name="email"></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>Payment Method</b></font></td>
			</tr>
			<tr>
				<td>Card Details</td>
				<td><input type="TEXT" name="cardNumber"></td>
			</tr>
			<tr>
				<td>Card Expiry</td>
				<td>Month</td>
				<td>Year</td>
			</tr>
			<tr>
				<td></td>
				<td><select name="month" />
					<option value="1">JAN</option>
					<option value="2">FEB</option>
					<option value="3">MAR</option>
					<option value="4">APR</option>
					<option value="5">MAY</option>
					<option value="6">JUN</option>
					<option value="7">JUL</option>
					<option value="8">AUG</option>
					<option value="9">SEP</option>
					<option value="10">OCT</option>
					<option value="11">NOV</option>
					<option value="12">DEC</option> </select></td>
				<td><select name="year" />
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option> </select></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><input type="SUBMIT"
					value="Submit Customer Details and Payment Method"></td>
			</tr>
		</table>
		<%
		String error = (String)request.getAttribute("error");
		if(error != null && error != ""){
			out.println(error + "<br>");
			request.removeAttribute("error");
		}
				
	}%>
	</form>
</body>
</html>