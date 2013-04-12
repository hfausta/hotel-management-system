package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;

/**
 * Servlet implementation class ManagerControllerServlet
 */
public class ManagerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s= request.getParameter("recordSelected");
		if(s== null || s == "" || request.getSession().getAttribute("hotelManager") == null){
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("HotelManagerServlet").forward(request, response);
		}
		else{
			String [] updates = s.split(",");
			DBConnectionFactory services = null;
			try {
				services = new DBConnectionFactory();
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!updates[1].equals("0")){
				try {
					BookingRecordBean record = (BookingRecordBean) new BookingRecordDAOImpl(services).findById(Integer.parseInt(updates[0]));
					HotelRoomBean room = (HotelRoomBean) new HotelRoomDAOImpl(services).findById(Integer.parseInt(updates[1]));
					record.setRoomId(room.getId());
					record.setRoomNum(room.getRoomnum());

					room.setStatus("UNAVAILABLE");

					new HotelRoomDAOImpl(services).updateRoom(room);

					new BookingRecordDAOImpl(services).updateRecord(record);
					request.getRequestDispatcher("/HotelManagerServlet").forward(request, response);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceLocatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					
					BookingRecordBean record = (BookingRecordBean) new BookingRecordDAOImpl(services).findById(Integer.parseInt(updates[0]));
					if(record.getRoomId() != 0){
						HotelRoomBean room = (HotelRoomBean) new HotelRoomDAOImpl(services).findById(record.getRoomId());
						record.setRoomId(0);
						record.setRoomNum(0);

						room.setStatus("AVAILABLE");

						new HotelRoomDAOImpl(services).updateRoom(room);

						new BookingRecordDAOImpl(services).updateRecord(record);
					}
					
				}
				catch(Exception ex){}
				request.getRequestDispatcher("/HotelManagerServlet").forward(request, response);
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
