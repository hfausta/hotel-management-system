<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.enterprise.beans.*"%>
<%@ page import = "com.enterprise.dao.support.*" %>
<%@ page import="com.enterprise.web.helper.*"%>
<%@ page import="com.enterprise.common.*"%>
<script language="javascript" type="text/javascript" src="dateTime.js">
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking Reservation System - Hotel Owner</title>
</head>
<body>
<form name ="logout" action = "HotelOwnerLoginServlet">
<input type = "submit" value = "Logout">
</form>
<%
ArrayList<ArrayList<String>> hotelHotelTypes = (ArrayList<ArrayList<String>>)request.getSession().getAttribute("hotelHotelTypes");
ArrayList<ArrayList<Integer>> roomsNumofRoomsAvailableForType = (ArrayList<ArrayList<Integer>>)request.getSession().getAttribute("roomsNumofRoomsAvailableForType");
ArrayList<ArrayList<Integer>> roomsRoomsExistPerType = (ArrayList<ArrayList<Integer>>)request.getSession().getAttribute("roomsRoomsExistPerType");
ArrayList<ArrayList<DiscountRateByPeriodBean>> hotelsDiscRateByPeriod = (ArrayList<ArrayList<DiscountRateByPeriodBean>>)request.getSession().getAttribute("hotelsDiscRateByPeriod");
ArrayList<ArrayList<HotelUnavailabilityBean>> hotelsHotelUnavailability = (ArrayList<ArrayList<HotelUnavailabilityBean>>)request.getSession().getAttribute("hotelsHotelUnavailability");
ArrayList<HotelBean> hotels =  (ArrayList<HotelBean>)request.getSession().getAttribute("hotels");
int hotelIndex = 0;
for(HotelBean h : hotels) {
	int typeIndex = 0;
	%>
	<form name = "hotel<%=hotelIndex %>" action = "OwnerControllerServlet">
	<font size = 12><b><%out.println(h.getName());%></b></font><br><br>
	<font size = 10><b><%out.println(h.getAddress());%></b></font><br><br>
	<font size = 6><b>Today's Hotel Occupancy</b></font><br><br>
	<%
		if(hotelHotelTypes.get(hotelIndex).isEmpty()) {
			%>
				Hotel is Empty (No Room Created) <br><br>
			<%
		} else {
			%>
			<table width="70%" border="0" cellspacing="5" cellpadding="0">
			<%
			for(String type : hotelHotelTypes.get(hotelIndex)) {
				%>
				<tr><td><%=type %></td>
				<td>Available Rooms : <% out.println(roomsNumofRoomsAvailableForType.get(hotelIndex).get(typeIndex));%></td>
				<td>Total Rooms : <%out.println(roomsRoomsExistPerType.get(hotelIndex).get(typeIndex)); %></td>
				</tr> 
				<tr></tr>
				<%
				typeIndex++;
			}
			%>
			</table>
			<%
		}
	%>
	<br><br><font size = 6><b>Hotel Discount Rate</b></font><br><br>
	<%
		if(hotelsDiscRateByPeriod.get(hotelIndex).isEmpty()) {
			%>
				No Discount Rate <br><br>
			<%
		} else {
			%>
			<table width="70%" border="0" cellspacing="5" cellpadding="0">
			<form name ="changeDiscountRate" action = "OwnerControllerServlet">
			<%
			for(String type : hotelHotelTypes.get(hotelIndex)) {
				%>
				<tr><td><font size = 3><b><%=type %></b></font></td></tr>
				<%
				for(DiscountRateByPeriodBean d : hotelsDiscRateByPeriod.get(hotelIndex)) {
					boolean found = false;
					%>
					<tr>
						<%
						if(d.getRoomType().equals(type)) {
							String[] split = d.getRoomType().split("\\(");
							%>
							<td></td>
							<td>Start Date : <%=d.getStart() %></td>
							<td>End Date : <%=d.getEnd() %></td>
							<td>Room Type : <%=split[0] %></td>
							<td>Rate : <%=d.getDiscountrate() %></td>
							<td>
							<input type=checkbox name="removeDiscRate" value = <%=d.getId() %>>
							</td>
							<%
							found = true;
						}
						if(found) {
						%>
						<td><input type = "SUBMIT" value = "Remove Discount Rate"></td>
						<%
						}
						%>
					</tr>
					<%
				}
				%>
				<tr></tr>
				<%
			}
			%>
			<tr></tr>
			</form>
			</table>
			<%
		}
		%>
		<form name = "addDiscountRate" action = "OwnerControllerServlet">
		<table width="80%" border="0" cellspacing="5" cellpadding="0"> 
		<tr>
		<td><font size = 3><b>Add New Discount Rate</b></font></td>
		<td>Start<input id="start<%=h.getId() %>" type="text" readonly = "readonly" name="start<%=h.getId() %>" size = "10"><a href="javascript:NewCal('start<%=h.getId() %>','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></td>
		<td>End<input id="end<%=h.getId() %>" type="text" readonly = "readonly" name="end<%=h.getId() %>" size = "10"><a href="javascript:NewCal('end<%=h.getId() %>','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></td>
		<td>
			<select name = "roomType">
				<%
				for(String type : hotelHotelTypes.get(hotelIndex)) {
					String[] split = type.split("\\(");
					%>
					<option value = <%=type %>><%=split[0] %></option>
					<%
				}
				%>
			</select>
		</td>
		<td>Rate<input id = "discRate" type = "TEXT" name = "discRate"></td>
		<td><input type = "SUBMIT" value = "Add"></td>
		</tr>
		</table>
		</form>
		
	<br><br><font size = 6><b>Hotel Unavailability</b></font><br><br>
	<%
		if(hotelsHotelUnavailability.get(hotelIndex).isEmpty()) {
			%>
				No Hotel Unavailability Record<br><br> 
			<%
		} else {
			%>
				<table width="70%" border="0" cellspacing="5" cellpadding="0">
					<tr>
						<td><b>Start Date</b></td>
						<td><b>End Date</b></td>
					</tr>
					<tr>
							<%	
								boolean avail = true;
								for(HotelUnavailabilityBean hu : hotelsHotelUnavailability.get(hotelIndex)) {
									out.print("<td>" + hu.getStart() + "</td>");
									out.print("<td>" + hu.getEnd() + "</td>");	
									%>
									<td>
									<form name = "changeHotelStatus" action = "OwnerControllerServlet">
									<select name = "hotelStatus"/>
										<option value = "<%=hu.getId()+"," %>unavailable"><%=hu.getStatus() %></option>
										<option value = "<%=hu.getId()+"," %>available">AVAILABLE</option>
									</select>
									</td>
									<td>
									<input type = "SUBMIT" value = "Update Hotel Status">
									</form>
									</td>
									<%
								}
							%>
					</tr>
					<tr></tr>
					<tr></tr>
				</table>
				<%
		}
		%>
		<form name = "addHotelUnavailability" action = "OwnerControllerServlet">
		<table width="80%" border="0" cellspacing="5" cellpadding="0"> 
		<tr>
		<td><font size = 3><b>Add New Unavailability Period</b></font></td>
		<td>Start<input id="startDate<%=h.getId() %>" type="text" readonly = "readonly" name="startDate<%=h.getId() %>" size = "10"><a href="javascript:NewCal('startDate<%=h.getId() %>','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></td>
		<td>End<input id="endDate<%=h.getId() %>" type="text" readonly = "readonly" name="endDate<%=h.getId() %>" size = "10"><a href="javascript:NewCal('endDate<%=h.getId() %>','ddmmmyyyy')"><img src="cal.gif" width="15" height="15" border="0" alt="Pick a date"></a></td>
		<td>Status<input id = "unavailabilityStatus" type = "TEXT" name = "<%=hotels.get(hotelIndex).getId()+"," %>unavailabilityStatus"></td>
		<td><input type = "SUBMIT" value = "Add"></td>
		</tr>
		</table>
		</form>
		<br><br>
		<%
	hotelIndex++;
	%>
	</form>
	<%
}
%>


</body>
</html>