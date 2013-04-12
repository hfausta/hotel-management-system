package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.ConsumerBean;
import com.enterprise.beans.HotelBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingDAOImpl;
import com.enterprise.dao.support.ConsumerDAOImpl;
import com.enterprise.dao.support.HotelDAOImpl;

/**
 * Servlet implementation class PaymentServlet
 */
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String cardNumber = request.getParameter("cardNumber");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		BookingBean booking = (BookingBean)session.getAttribute("booking");
		ArrayList<BookingRecordBean> bookingRecord = (ArrayList<BookingRecordBean>)session.getAttribute("bookingRecords"); 
		
		if(booking == null || bookingRecord == null){
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("/ConsumerHomeServlet").forward(request, response);
		}
		else{
			if(name == null || name == "" || address == null || address == "" || phone == null || phone == "" || email == null || email == "" || cardNumber == null || cardNumber == "" || month == null || month == "" || year == null || year == ""){
				request.setAttribute("error", "Please fill in all values");
				request.getRequestDispatcher("payment.jsp").forward(request, response);
			}
			else{
				Pattern emailPattern = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",Pattern.CASE_INSENSITIVE);
				Pattern numericPattern = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
				
				if(!emailPattern.matcher(email).matches()){
					request.setAttribute("error", "invalid email inserted");
					request.getRequestDispatcher("payment.jsp").forward(request, response);
				}
				else if (!numericPattern.matcher(phone).matches()){
					request.setAttribute("error", "invalid phone number inserted");
					request.getRequestDispatcher("payment.jsp").forward(request, response);
				}
				else if(!numericPattern.matcher(cardNumber).matches()){
					request.setAttribute("error", "invalid card number inserted");
					request.getRequestDispatcher("payment.jsp").forward(request, response);
				}
				else {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
					Date date = null;
					try {
						date = sdf.parse("1-"+month+"-"+year);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(date.before(new Date())){
						request.setAttribute("error", "invalid card expiry inserted");
						request.getRequestDispatcher("payment.jsp").forward(request, response);
					}
					else{
						Integer pin = (int) ((Math.random() * 9999)+1000);
						System.out.println(pin);
						booking.setPin(pin.toString());
						DBConnectionFactory services = null;
						try {
							services = new DBConnectionFactory();
						} catch (ServiceLocatorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						UserBean user = new UserBean();
						user.setAddress(address);
						user.setEmail(email);
						user.setName(name);
						user.setPhoneNumber(Integer.parseInt(phone));
						HotelBean hotel = null;
						try {
							hotel = new HotelDAOImpl(services).findById(booking.getHotelid());
						} catch (DataAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ServiceLocatorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.setAttribute("userBean", user);
						session.setAttribute("booking", booking);
						session.setAttribute("bookingRecord", bookingRecord);
						request.setAttribute("hotelName", hotel.getName());
						request.setAttribute("hotelAddress", hotel.getAddress());
						request.setAttribute("cardNumber", cardNumber);
						request.getRequestDispatcher("checkOut.jsp").forward(request, response);
						//Integer consumerId = new ConsumerDAOImpl(services).insert(user);
						//booking.setCustomerid(consumerId);
					}
					
				
					
				}
				
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
