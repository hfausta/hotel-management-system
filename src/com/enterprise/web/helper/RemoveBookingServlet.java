package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.BookingDAOImpl;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;

/**
 * Servlet implementation class RemoveBookingServlet
 */
public class RemoveBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] bookingid = request.getParameterValues("removeBooking");
		
		if(bookingid == null){
			
		}
		else{
			List <String> bookingids = Arrays.asList(bookingid);
			DBConnectionFactory services = null;
			try {
				services = new DBConnectionFactory();
			} catch (ServiceLocatorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(String s : bookingids){
				BookingBean booking = new BookingDAOImpl(services).findBookingById(Integer.parseInt(s));
				List<BookingRecordBean> records = new BookingRecordDAOImpl(services).findByBookingId(Integer.parseInt(s));
				for(BookingRecordBean record : records){
					if(record.getRoomId() != 0){
						HotelRoomBean hotelRoom = null;
						try {
							hotelRoom = new HotelRoomDAOImpl(services).findById(record.getRoomId());
						} catch (ServiceLocatorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						hotelRoom.setStatus("AVAILABLE");
						try {
							new HotelRoomDAOImpl(services).updateRoom(hotelRoom);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ServiceLocatorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				new BookingRecordDAOImpl(services).deleteByBooking(Integer.parseInt(s));
				new BookingDAOImpl(services).delete(Integer.parseInt(s));
			}
		}
		request.getRequestDispatcher("/HotelManagerServlet").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
