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
<title>Hotel Booking Reservation Systems - Hotel Manager</title>
</head>
<body>
<form name ="logout" action = "HotelManagerLoginServlet">
<input type = "submit" value = "Logout">
</form>
<font size = 12><b>List of Bookings</b></font><br><br>
	<table width="50%" border="0" cellspacing="5" cellpadding="0"/>
		<%
			ArrayList<ArrayList<BookingRecordBean>> bookingBookingRecord = (ArrayList<ArrayList<BookingRecordBean>>) session.getAttribute("bookingBookingRecords");
			ArrayList<String> roomTypes = (ArrayList<String>) session.getAttribute("roomTypes");
			ArrayList<ArrayList<HotelRoomBean>> availableRoomsByType = (ArrayList<ArrayList<HotelRoomBean>>) session.getAttribute("availableRoomsByType");
			
			if(bookingBookingRecord == null || roomTypes == null || availableRoomsByType == null){
				request.setAttribute("error", "Please start from this page");
				request.getRequestDispatcher("staffLogin.jsp").forward(request,response);
			}
			
			if(bookingBookingRecord == null || bookingBookingRecord.size() == 0) {
				out.print("<tr><td>No Booking Has Been Made</td></tr>");
			} else {
				for(ArrayList<BookingRecordBean> b : bookingBookingRecord) {
					%>
					<tr>
					<td>Booking ID : <%out.print(b.get(0).getBookingid()); %></td>
					<td>Start Date : <%out.print(b.get(0).getStart()); %></td>
					<td>End Date   : <%out.print(b.get(0).getEnd()); %></td>
					<form name ="" action = "RemoveBookingServlet"/>
					<td><input type = "checkbox" name = "removeBooking" value = <%=b.get(0).getBookingid() %>></input></td>
					<td><input type = "submit" value = "Remove Booking"></input></td>
					</form>
					</tr>
					<%
					int i = 1;
					for(BookingRecordBean br : b) {
						%>
						<form name ="" action = "ManagerControllerServlet"/>
						<tr>
						<td></td>
						<td>Record <%out.print(br.getId() + " "); String[] split = br.getRoomType().split("\\("); out.print(split[0]);%></td>
							<%
								int index = roomTypes.indexOf(br.getRoomtype());
							%>
							<td><select name = "recordSelected"/>
							<%
								if(br.getRoomNum() == 0){
									out.print("<option value =" + br.getId() + ",0>Not Allocated</option>");
								} else {
									out.print("<option value =" + br.getId() + "," + br.getRoomId() + ">" + br.getRoomNum() + "</option>");
								}
							%>
							<%
							if(br.getRoomId() == 0){
							for(HotelRoomBean h : availableRoomsByType.get(index)) {
									if(br.getRoomNum() != h.getRoomnum()){
										out.print("<option value =" + br.getId() + "," + h.getId() + ">" + h.getRoomnum() + "</option>");
									}
							}
							}
							else{
								out.print("<option value =" + br.getId() + ",0>Unallocate</option>");	
							}
							i++;
							%>
							</select>
							</td>
							<td><input type = "SUBMIT" value = "Update Record"/></td>
						</tr>
						</form>
						<%
					}
				}	
			}
		%>
	</table>	
</body>
</html>