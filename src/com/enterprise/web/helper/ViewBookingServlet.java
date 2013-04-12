package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.DiscountRateByPeriodDAOImpl;
import com.enterprise.dao.support.HotelDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;
import com.enterprise.dao.support.RoomTypeDAOImpl;

/**
 * Servlet implementation class ViewBookingServlet
 */
public class ViewBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookingBean booking = (BookingBean) request.getSession().getAttribute("booking");
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String pin = (String) request.getParameter("PIN");
		if(pin == null || booking == null){
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else{
			if(booking.getPin().equals(pin)){
				DBConnectionFactory services = null;
				try {
					services = new DBConnectionFactory();
				} catch (ServiceLocatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<BookingRecordBean> records = (ArrayList<BookingRecordBean>) new BookingRecordDAOImpl(services).findByBookingId(booking.getId());
				
				ArrayList<String> hotelTypes = null;
				try {
					hotelTypes = (ArrayList<String>) new HotelRoomDAOImpl(services).typesThatExistForAHotel(booking.getHotelid());
				} catch (DataAccessException e6) {
					// TODO Auto-generated catch block
					e6.printStackTrace();
				} catch (ServiceLocatorException e6) {
					// TODO Auto-generated catch block
					e6.printStackTrace();
				} catch (SQLException e6) {
					// TODO Auto-generated catch block
					e6.printStackTrace();
				}
				List<String> typesAvailable = new ArrayList<String>();
				List<Integer> numofRoomsAvailableForType = new ArrayList<Integer>();
				HotelBean hotel = null;
				try {
					hotel = new HotelDAOImpl(services).findById(booking.getHotelid());
				} catch (DataAccessException e6) {
					// TODO Auto-generated catch block
					e6.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceLocatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getSession().setAttribute("hotel", hotel);
				for(String t : hotelTypes){
					List<BookingRecordBean> bookingsExist = new BookingRecordDAOImpl(services).findAllBookingRecordWithHotelIDAndRoomType(booking.getHotelid(), t);
					int roomsExist = 0;
					try {
						roomsExist = (int)new HotelRoomDAOImpl(services).countRoomTypeAndHotelId(booking.getHotelid(), t);
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
					int roomsBooked = (isRoomTypeAvailable(bookingsExist, booking.getStart(), booking.getEnd()));
					if(roomsBooked < roomsExist){
						typesAvailable.add(t);
						numofRoomsAvailableForType.add(roomsExist-roomsBooked);
					}
				}
				List<Double> priceRates = new ArrayList<Double>();
				for(String t : typesAvailable){
					priceRates.add(new RoomTypeDAOImpl(services).getPriceRateByRoomType(t));
				}
				
				
				List<Date> peakEndDates = new ArrayList<Date>();
				try {
					peakEndDates.add(sdf.parse("15-FEB-" + (Integer.parseInt(formatNowYear.format(booking.getStart()))+1)));
				} catch (NumberFormatException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				} catch (ParseException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				try {
					peakEndDates.add(sdf.parse("14-APR-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e5) {
					// TODO Auto-generated catch block
					e5.printStackTrace();
				}
				try {
					peakEndDates.add(sdf.parse("20-JUL-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				try {
					peakEndDates.add(sdf.parse("10-OCT-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				List<Date> peakStartDates = new ArrayList<Date>();
				try {
					peakStartDates.add(sdf.parse("15-DEC-"+ (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					peakStartDates.add(sdf.parse("25-MAR-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					peakStartDates.add(sdf.parse("1-JUL-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					peakStartDates.add(sdf.parse("20-SEP-" + (formatNowYear.format(booking.getStart()))));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List<ArrayList<Double>> discRateRoomType = new ArrayList<ArrayList<Double>>();
				for(int i =0 ; i<typesAvailable.size();i++){
					discRateRoomType.add(new ArrayList<Double>());
				}
				
				for(ArrayList<Double> b : discRateRoomType){
					b = new ArrayList<Double>();
				}
				
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(booking.getStart());
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(booking.getEnd());
				ArrayList<Boolean> peakedDates = new ArrayList<Boolean>();
				for (; !startCal.after(endCal); startCal.add(Calendar.DATE, 1)) {
				    Date current = startCal.getTime();
				    boolean peaked = false;
				    for(int i =0 ; i < peakEndDates.size(); i++){
				    	if((current.compareTo(peakStartDates.get(i)) >= 0) && (current.compareTo(peakEndDates.get(i)) <= 0)){
				    		peaked = true;
				    		break;
				    	}
				    }
				    peakedDates.add(peaked);
				    int i =0;
				    for(String s : typesAvailable){
					Double discRate = null;
					try {
						discRate = new DiscountRateByPeriodDAOImpl(services).getDiscountRateByDate((new java.sql.Date(current.getTime())), booking.getHotelid(),s );
					} catch (ServiceLocatorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(discRate == null){
						discRateRoomType.get(i).add(new Double(1));
					}
					else{
						discRateRoomType.get(i).add(discRate);
					}
					i++;
				    }
				    peaked=false;
				}
				List<Double> pricePerRoomType = new ArrayList<Double>();
				for(int i = 0; i < typesAvailable.size(); i ++){
					Double price = new Double(0);
					for(int j = 0 ; j < peakedDates.size(); j++) {
						Double currDayPriceForType = priceRates.get(i);
						if(peakedDates.get(j)){
							currDayPriceForType = currDayPriceForType * 1.4D;
						}
						currDayPriceForType = currDayPriceForType * (discRateRoomType.get(i).get(j));
						price = price + (currDayPriceForType);
					}
					pricePerRoomType.add(price);
				}
				
				request.getSession().setAttribute("typesAvailable", typesAvailable);
				request.getSession().setAttribute("numofRoomsAvailableForType", numofRoomsAvailableForType);
				request.getSession().setAttribute("pricePerRoomType", pricePerRoomType);
				request.getSession().setAttribute("roomPricePerTypes",pricePerRoomType);
				request.getSession().setAttribute("records", records);
				request.getSession().setAttribute("booking", booking);
				request.getRequestDispatcher("manageBooking.jsp").forward(request, response);
				
			}
			else{

			}
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

}
