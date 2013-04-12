package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelBean;
import com.enterprise.beans.HotelManagerBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingDAOImpl;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.HotelDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;

/**
 * Servlet implementation class HotelManagerServlet
 */
public class HotelManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		DBConnectionFactory services = null;
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HotelManagerBean manager = (HotelManagerBean) request.getSession().getAttribute("hotelManager");
		if(manager == null){
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("hotelManagerHome.jsp").forward(request, response);
		}
		else{
		HotelBean hotel = null;
		try {
			hotel = (HotelBean) new HotelDAOImpl(services).findByManagerId(manager.getId());
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
		
		List<BookingBean> bookings = new BookingDAOImpl(services).findBookingsByHotelId(hotel.getId());
		List<ArrayList<BookingRecordBean>> bookingBookingRecords = new ArrayList<ArrayList<BookingRecordBean>>();
		List<ArrayList<HotelRoomBean>> roomRooms = new ArrayList<ArrayList<HotelRoomBean>>();
		List<String> roomTypes = null;
		try {
			roomTypes = new HotelRoomDAOImpl(services).typesThatExistForAHotel(hotel.getId());
			for(String s : roomTypes){
				roomRooms.add((ArrayList<HotelRoomBean>) new HotelRoomDAOImpl(services).availableForRoomTypeAtHotel(s, hotel.getId()));
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
		
		for(BookingBean b : bookings){
			bookingBookingRecords.add((ArrayList<BookingRecordBean>) new BookingRecordDAOImpl(services).findByBookingId(b.getId()));
		}
		
		
		request.getSession().setAttribute("availableRoomsByType", roomRooms);
		request.getSession().setAttribute("roomTypes", roomTypes);
		request.getSession().setAttribute("bookingBookingRecords", bookingBookingRecords);
		request.getSession().setAttribute("hotelId", hotel.getId());
		request.getRequestDispatcher("hotelManagerHome.jsp").forward(request, response);
	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
