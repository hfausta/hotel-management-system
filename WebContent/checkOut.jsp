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
<title>Hotel Booking Request - Booking Confirmation Page</title>
</head>
<body>
	<%
		ArrayList<BookingRecordBean> bookingRecord = (ArrayList<BookingRecordBean>) session
				.getAttribute("bookingRecord");
		BookingBean booking = (BookingBean) session.getAttribute("booking");
		UserBean user = (UserBean) session.getAttribute("userBean");
		String hotelName = (String) request.getAttribute("hotelName");
		String hotelAddress = (String) request.getAttribute("hotelAddress");
		String cardNumber = (String) request.getAttribute("cardNumber");
		if (cardNumber == null || cardNumber == "" || hotelAddress == null
				|| hotelAddress == "" || hotelName == null
				|| hotelName == "" || user == null || booking == null) {
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("consumerHome.jsp").forward(
					request, response);
		} else {
	%>
	<form name="checkout" action="CheckOutServlet">
		<table width="80%" border="0" cellspacing="10" cellpadding="0">
			<tr>
				<td><font size=12><b>Booking Details</b></font></td>
			</tr>
			<tr>
				<td>Hotel Name</td>
				<td><%=hotelName%></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><%=hotelAddress%></td>
			</tr>
			<tr>
				<td>Start Date</td>
				<td><%=booking.getStart()%></td>
			</tr>
			<tr>
				<td>End Date</td>
				<td><%=booking.getEnd()%></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>Booked Rooms</b></font></td>
			</tr>
			<%
				int i = 1;
					double price = 0;
					for (BookingRecordBean b : bookingRecord) {
						out.print("<tr>");
						out.print("<td>Room " + i + "</td>");
						String[] split = b.getRoomtype().split("\\(");
						out.print("<td>" + split[0] + "</td>");
						out.print("<td>$" + b.getPrice() + "</td>");
						price = price + b.getPrice();
						if (b.isExtrabed()) {
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
					<%
						out.print("$" + price);
					%>
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>Consumer Details</b></font></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><%=user.getName()%></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><%=user.getAddress()%></td>
			</tr>
			<tr>
				<td>Phone</td>
				<td><%=user.getPhoneNumber()%></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><%=user.getEmail()%></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>Payment Details</b></font></td>
			</tr>
			<tr>
				<td>Card Number</td>
				<td><%=cardNumber%></td>
			</tr>
			<tr></tr>
			<tr>
				<td><font size=12><b>You Will Receive an Email Regarding to Your Booking URL and PIN Shortly After Booking Has Been Confirmed</b></font></td>
			</tr>
			<tr></tr>
			<tr>
				<td><input type="SUBMIT" value="Confirm Booking"></td>
				</form>
				<td>
				<form name = "homeButton" action = "ConsumerHomeServlet">
					<input type = "SUBMIT" value = "Cancel">
				</form>
				</td>
			</tr>
		</table>
		<%
			}
		%>
</body>
</html>