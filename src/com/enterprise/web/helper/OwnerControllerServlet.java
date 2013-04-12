package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.DiscountRateByPeriodBean;
import com.enterprise.beans.HotelBean;
import com.enterprise.beans.HotelUnavailabilityBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.DiscountRateByPeriodDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;
import com.enterprise.dao.support.HotelUnavailabilityDAOImpl;

/**
 * Servlet implementation class OwnerControllerServlet
 */
public class OwnerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OwnerControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		String[] removeDiscRates = request.getParameterValues("removeDiscRate");

		String roomType = request.getParameter("roomType");
		String discRate = request.getParameter("discRate");

		String startDate = null;
		String endDate = null;

		String start= null;
		String end = null;

		String hotelUnavailStatus = null;

		ArrayList<HotelBean> hotels = (ArrayList<HotelBean>) request.getSession().getAttribute("hotels");
		int id = 0;
		for(HotelBean b : hotels){
			if(start == null && hotelUnavailStatus == null){
				hotelUnavailStatus = request.getParameter(b.getId()+",unavailabilityStatus");
				startDate = request.getParameter("startDate"+ b.getId());
				endDate = request.getParameter("endDate"+ b.getId());
				id = b.getId();
			}

			if(start == null && hotelUnavailStatus == null){
				start = request.getParameter("start"+b.getId());
				end =  request.getParameter("end"+b.getId());
				id = b.getId();
			}
		}

		String hotelStatus = request.getParameter("hotelStatus");


		DBConnectionFactory services = null;
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		if(hotelStatus != null){
			String [] cmd = hotelStatus.split(",");
			if(cmd[1].equals("available")){
				new HotelUnavailabilityDAOImpl(services).delete(Integer.parseInt(cmd[0]));
				request.getRequestDispatcher("/HotelOwnerServlet").forward(request, response);
			}
			else{
				request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
			}
		}

		else if(hotelUnavailStatus != null){
			if(startDate == null || endDate == null){
				request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
			}
			else{
				java.util.Date st = null;
				try {
					st = sdf.parse(startDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				java.util.Date en = null;
				try {
					en = sdf.parse(endDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(st.compareTo(en)>=0){
					request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
				}
				else{

					HotelUnavailabilityBean hub = new HotelUnavailabilityBean();
					hub.setEnd(new java.sql.Date(en.getTime()));
					hub.setStart(new java.sql.Date(st.getTime()));
					hub.setHotelid(id);
					hub.setStatus(hotelUnavailStatus);
					new HotelUnavailabilityDAOImpl(services).insert(hub);
					request.getRequestDispatcher("/HotelOwnerServlet").forward(request, response);
				}
			}
		}

		else if(discRate != null){
			if(start == null || end == null){
				request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
			}
			else{
				java.util.Date st = null;
				try {
					st = sdf.parse(start);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				java.util.Date en = null;
				try {
					en = sdf.parse(end);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(st.compareTo(en)>=0){
					request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
				}
				else{
					ArrayList<String> existingTypes = null;
					String res = null;
					try {
						existingTypes = (ArrayList<String>) new HotelRoomDAOImpl(services).typesThatExistForAHotel(id);
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
					for(String s : existingTypes){
						if(s.contains(roomType)){
							res = s;
							break;
						}
					}
					DiscountRateByPeriodBean b = new DiscountRateByPeriodBean();
					b.setDiscountrate(Double.valueOf(discRate));
					b.setEnd(new Date(en.getTime()));
					b.setStart(new Date(st.getTime()));
					b.setHotelid(id);
					b.setRoomType(res);
					new DiscountRateByPeriodDAOImpl(services).insert(b);
					request.getRequestDispatcher("/HotelOwnerServlet").forward(request, response);
				}
			}
		}

		else if(removeDiscRates != null){
			new DiscountRateByPeriodDAOImpl(services).delete(Integer.parseInt(removeDiscRates[0]));
			request.getRequestDispatcher("/HotelOwnerServlet").forward(request, response);
		}
		else{
			request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
		}
	}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request,response);
		}

	}
