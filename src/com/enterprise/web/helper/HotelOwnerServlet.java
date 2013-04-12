package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.DiscountRateByPeriodBean;
import com.enterprise.beans.HotelBean;
import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.beans.HotelUnavailabilityBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.DiscountRateByPeriodDAOImpl;
import com.enterprise.dao.support.HotelDAOImpl;
import com.enterprise.dao.support.HotelOwnerDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;
import com.enterprise.dao.support.HotelUnavailabilityDAOImpl;

/**
 * Servlet implementation class HotelOwnerServlet
 */
public class HotelOwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelOwnerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HotelOwnerBean owner = (HotelOwnerBean) request.getSession().getAttribute("hotelOwner");
		if(owner == null){
			
		} else{
			DBConnectionFactory services = null;
			ArrayList<ArrayList<String>> hotelHotelTypes = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<Integer>> roomsNumofRoomsAvailableForType = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> roomsRoomsExistPerType = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<DiscountRateByPeriodBean>> hotelsDiscRateByPeriod = new ArrayList<ArrayList<DiscountRateByPeriodBean>>();
			ArrayList<ArrayList<HotelUnavailabilityBean>> hotelsHotelUnavailability = new ArrayList<ArrayList<HotelUnavailabilityBean>>();
			ArrayList<Integer> numofRoomsAvailableForType = new ArrayList<Integer>();
			ArrayList<Integer> roomsExistPerType = new ArrayList<Integer>();
			ArrayList<DiscountRateByPeriodBean> getDiscRatesByHotelId = new ArrayList<DiscountRateByPeriodBean>();
			ArrayList<HotelUnavailabilityBean> hotelUnavailability = new ArrayList<HotelUnavailabilityBean>();
			List<HotelBean> hotels =  new ArrayList<HotelBean>();
			try {
				services = new DBConnectionFactory();
			} catch (ServiceLocatorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				hotels = (List<HotelBean>) new HotelDAOImpl(services).findByOwnerId(owner.getId());
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
			Date currDate = new Date();
			for(HotelBean h : hotels){
				ArrayList<String> hotelTypes = null;
				try {
					hotelTypes = (ArrayList<String>) new HotelRoomDAOImpl(services).typesThatExistForAHotel(h.getId());
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
				getDiscRatesByHotelId = (ArrayList<DiscountRateByPeriodBean>) new DiscountRateByPeriodDAOImpl(services).findAllDiscountRatesByHotel(h.getId());
				hotelsDiscRateByPeriod.add(getDiscRatesByHotelId);
				hotelUnavailability = (ArrayList<HotelUnavailabilityBean>) new HotelUnavailabilityDAOImpl(services).findByHotelId(h.getId());
				hotelsHotelUnavailability.add(hotelUnavailability);
				for(String t : hotelTypes){
					
					//List<BookingRecordBean> bookingsExist = new BookingRecordDAOImpl(services).findAllBookingRecordWithHotelIDAndRoomType(h.getId(), t);
					List<HotelRoomBean> rooms = null;
					try {
						rooms = new HotelRoomDAOImpl(services).availableForRoomTypeAtHotel(t, h.getId());
					} catch (ServiceLocatorException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Integer roomsExist = 0;
					try {
						roomsExist = (int)new HotelRoomDAOImpl(services).countRoomTypeAndHotelId(h.getId(), t);
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
					roomsExistPerType.add(roomsExist);
					//int roomsBooked = (isRoomTypeAvailable(bookingsExist, new Date(), new Date()));
					//int roomsBooked = isRoomTypeAvailable(rooms);
					numofRoomsAvailableForType.add(rooms.size());
				}
				hotelHotelTypes.add(hotelTypes);
				roomsNumofRoomsAvailableForType.add(numofRoomsAvailableForType);
				roomsRoomsExistPerType.add(roomsExistPerType);
			}
			request.getSession().setAttribute("hotelHotelTypes", hotelHotelTypes);
			request.getSession().setAttribute("roomsNumofRoomsAvailableForType", roomsNumofRoomsAvailableForType);
			request.getSession().setAttribute("roomsRoomsExistPerType", roomsRoomsExistPerType);
			request.getSession().setAttribute("hotelsDiscRateByPeriod", hotelsDiscRateByPeriod);
			request.getSession().setAttribute("hotelsHotelUnavailability",hotelsHotelUnavailability);
			request.getSession().setAttribute("owner", owner);
			request.getSession().setAttribute("hotels", hotels);
			request.getRequestDispatcher("hotelOwnerHome.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private int isRoomTypeAvailable(List<BookingRecordBean> bookingsExist,Date startDate,Date endDate){
		int count = 0;
		boolean ret = false;
		for(BookingRecordBean b : bookingsExist){
			if((startDate.compareTo(b.getEnd()) <= 0) && (startDate.compareTo(b.getStart()) >= 0)){
				count++;
			}
			else if((endDate.compareTo(b.getEnd()) <= 0) && (endDate.compareTo(b.getStart()) >= 0)){
				count++;
			}
			else if((startDate.compareTo(b.getEnd()) <= 0) && (startDate.compareTo(b.getStart())) >= 0){
				count++;
			}
			
			else if((startDate.compareTo(b.getStart()) <= 0) && (endDate.compareTo(b.getEnd())) >= 0){
				count++;
			}
		}
		return count;
		
	}
	
	private int isRoomTypeAvailable(List<HotelRoomBean> hotelRooms){
		int count = 0;
		for(HotelRoomBean b : hotelRooms){
			if(b.getStatus().equals("UNAVAILABLE")){
				count++;
			}
			
		}
		return count;
		
	}

}
