<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Booking Request - Room Type</title>
</head>
<body>
<form name = "homeButton" action = "ConsumerHomeServlet">
<input type = "SUBMIT" value = "Home">
</form>
<%
ArrayList<String> types = (ArrayList<String>) request.getSession().getAttribute("typesAvailable");
ArrayList<Integer> numofRooms = (ArrayList<Integer>) request.getSession().getAttribute("numofRoomsAvailableForType");
ArrayList<Double> pricePerRoomType = (ArrayList<Double>) request.getSession().getAttribute("pricePerRoomType");
if ( types == null || numofRooms == null){
	request.setAttribute("error", "Please start from this page.");
	request.getRequestDispatcher("/consumerHome.jsp").forward(request,response);
}
else{
	out.println("<form name =\"pickRoomType\" action = \"SelectRoomTypeServlet\">");
	
	types = (ArrayList<String>) request.getSession().getAttribute("typesAvailable");
	numofRooms = (ArrayList<Integer>) request.getSession().getAttribute("numofRoomsAvailableForType");
%>
	<font size = 12><b>Available Rooms</b></font><br>
	Pick Number of Rooms Which You Would Like to Book for Each Type<br><br><br>
	<table width="50%" border="0" cellspacing="5" cellpadding="0">
<%
	if (types != null && numofRooms != null){
	
		//for(int i = 0 ; i < types.size() ; i++){
		//	out.println(types.get(i) + " AVAILABLE : " + numofRooms.get(i) + "<br>" );
		//}
		//loop will depend on the num rooms from prev servlet
		int currType =0;
		for(String t : types) {
			out.print("<tr><td>" + t + "</td>");
			//available room type will depend on the num rooms available from prev servlet
			out.print("<td><select name = \""+currType+"" +  "\">");
			for(int i = 0; i <= numofRooms.get(currType); i++) {
					out.print("<option value =" + i + ">" +i+ "</option>");
			}
			out.print("</td>");
			String[] split = t.split("\\(");
			out.print("<td>The Total Price for Each of " + split[0] + " " + pricePerRoomType.get(currType) + "</td>");
			out.print("</tr><tr></tr><tr></tr></select>");
			currType++;
		}
	}
	out.println("<tr></tr><tr></tr><tr></tr><tr><td><input type = \"SUBMIT\" value = \"Submit Room Type Selection\"></td></tr>");
%>
	</table>
<%
	String error = (String)request.getAttribute("error");
	if(error != null && error != ""){
		out.println(error + "<br>");
		request.removeAttribute("error");
	}

}
%>
</form>
</body>
</html>