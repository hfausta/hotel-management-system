package com.enterprise.web.helper;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingDAOImpl;
import com.enterprise.dao.support.ConsumerDAOImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckOutServlet
 */
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnectionFactory services = null;
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<BookingRecordBean> bookingRecord = (ArrayList<BookingRecordBean>) request.getSession().getAttribute("bookingRecord");
		BookingBean booking = (BookingBean)request.getSession().getAttribute("booking");
		UserBean user = (UserBean)request.getSession().getAttribute("userBean");
		if(user == null || booking == null || bookingRecord == null ){
			request.setAttribute("error","Please start from this page");
			request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
		}
		try {
			Integer consumerId = new ConsumerDAOImpl(services).insert(user);
			booking.setCustomerid(consumerId);
			booking.setId(new BookingDAOImpl(services).insertBookingAndRecords(booking, bookingRecord));
			
		      // Recipient's email ID needs to be mentioned.
		      String to = user.getEmail();
		      
		      // Sender's email ID needs to be mentioned
		      String from = "csje047@cse.unsw.edu.au";

		      // Assuming you are sending email from localhost
		      String host = "smtp";

		      // Get system properties
		      Properties properties = System.getProperties();

		      // Setup mail server
		      properties.setProperty("mail.smtp.host", host);

		      // Get the default Session object.
		      Session session = Session.getDefaultInstance(properties);

		      try{
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));

		         // Set To: header field of the header.
		         message.addRecipient(Message.RecipientType.TO,
		                                  new InternetAddress(to));

		         // Set Subject: header field
		         message.setSubject("Your Booking Details Made At " + new Date().toString());

		         // Now set the actual message
		         message.setText("BOOKING URL = http://"+InetAddress.getLocalHost().getHostName()+":8080/ass3/CustomerBookingServlet?bookingid="+booking.getId() +'\n' + "PIN =" + booking.getPin());

		         // Send message
		         Transport.send(message);
		         System.out.println("Sent message successfully....");
		      }catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().invalidate();
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
