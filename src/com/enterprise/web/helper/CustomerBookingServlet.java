package com.enterprise.web.helper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.BookingDAOImpl;

/**
 * Servlet implementation class CustomerBookingServlet
 */
public class CustomerBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerBookingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("bookingid");
		//request.getSession().invalidate();
		if(id != null){
			DBConnectionFactory services = null;
			try {
				services = new DBConnectionFactory();
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BookingBean booking = new BookingDAOImpl(services).findBookingById(Integer.parseInt(id));
			request.getSession().setAttribute("booking", booking);
			System.out.println(booking.getId());
			if(booking.getId()==0){
				request.setAttribute("error", "booking doesn't exist");
				request.getRequestDispatcher("loginBookingPage.jsp").forward(request, response);			
			}
			else{
				Calendar cal =  Calendar.getInstance();
				cal.setTime(booking.getStart());
				cal.add(Calendar.DATE, -2);
				Date exp = cal.getTime();
				if(exp.compareTo(new Date())>=0){
					request.getRequestDispatcher("loginBookingPage.jsp").forward(request, response);
				}
				else{
					request.setAttribute("error", "Can't access booking due to time period");
					request.getRequestDispatcher("loginBookingPage.jsp").forward(request, response);
				}
			}
		}
		else{
			request.setAttribute("error", "No Id specified");
			request.getRequestDispatcher("loginBookingPage.jsp").forward(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
