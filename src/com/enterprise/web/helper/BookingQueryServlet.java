package com.enterprise.web.helper;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.beans.HotelUnavailabilityBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.BookingDAOImpl;
import com.enterprise.dao.support.BookingRecordDAOImpl;
import com.enterprise.dao.support.DiscountRateByPeriodDAOImpl;
import com.enterprise.dao.support.HotelRoomDAOImpl;
import com.enterprise.dao.support.HotelUnavailabilityDAOImpl;
import com.enterprise.dao.support.RoomTypeDAOImpl;

/**
 * Servlet implementation class BookingQueryServlet
 */
public class BookingQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingQueryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		String hotelId = request.getParameter("hotelSelected");

		if( start == null || end == null || start.equals("") || end.equals("")){
			request.setAttribute("error","Please set all the values above");
			request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");

			try {
				Date st =  sdf.parse(start);
				Date en =  sdf.parse(end);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);
				if(st.after(en)){
					request.setAttribute("error","End date can't be before start date");
					request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
				}
				else if (st.before(cal.getTime())){

					request.setAttribute("error","start date must be after todays date");
					request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
				}
				else if (!areBookingsAllowed(st,en,hotelId)){
					request.setAttribute("error","hotel is unavaiable during this period");
					request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
				}
				else{
					BookingBean booking = new BookingBean();
					booking.setEnd(new java.sql.Date(en.getTime()));
					booking.setStart(new java.sql.Date(st.getTime()));
					booking.setHotelid(Integer.parseInt(hotelId));

					DBConnectionFactory services = new DBConnectionFactory();
					ArrayList<String> hotelTypes = (ArrayList<String>) new HotelRoomDAOImpl(services).typesThatExistForAHotel(booking.getHotelid());
					List<String> typesAvailable = new ArrayList<String>();
					List<Integer> numofRoomsAvailableForType = new ArrayList<Integer>();
					for(String t : hotelTypes){
						List<BookingRecordBean> bookingsExist = new BookingRecordDAOImpl(services).findAllBookingRecordWithHotelIDAndRoomType(booking.getHotelid(), t);
						int roomsExist = (int)new HotelRoomDAOImpl(services).countRoomTypeAndHotelId(booking.getHotelid(), t);
						int roomsBooked = (isRoomTypeAvailable(bookingsExist, booking.getStart(), booking.getEnd()));
						if(roomsBooked < roomsExist){
							typesAvailable.add(t);
							numofRoomsAvailableForType.add(roomsExist-roomsBooked);
						}
					}
					if(typesAvailable.size() == 0){
						request.setAttribute("error","no rooms available");
						request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
					}
					else{
						List<Double> priceRates = new ArrayList<Double>();
						for(String t : typesAvailable){
							priceRates.add(new RoomTypeDAOImpl(services).getPriceRateByRoomType(t));
						}


						List<Date> peakEndDates = new ArrayList<Date>();
						peakEndDates.add(sdf.parse("15-FEB-" + (Integer.parseInt(formatNowYear.format(st))+1)));
						peakEndDates.add(sdf.parse("14-APR-" + (formatNowYear.format(st))));
						peakEndDates.add(sdf.parse("20-JUL-" + (formatNowYear.format(st))));
						peakEndDates.add(sdf.parse("10-OCT-" + (formatNowYear.format(st))));

						List<Date> peakStartDates = new ArrayList<Date>();
						peakStartDates.add(sdf.parse("15-DEC-"+ (formatNowYear.format(st))));
						peakStartDates.add(sdf.parse("25-MAR-" + (formatNowYear.format(st))));
						peakStartDates.add(sdf.parse("1-JUL-" + (formatNowYear.format(st))));
						peakStartDates.add(sdf.parse("20-SEP-" + (formatNowYear.format(st))));

						List<ArrayList<Double>> discRateRoomType = new ArrayList<ArrayList<Double>>();
						for(int i =0 ; i<typesAvailable.size();i++){
							discRateRoomType.add(new ArrayList<Double>());
						}

						for(ArrayList<Double> b : discRateRoomType){
							b = new ArrayList<Double>();
						}

						Calendar startCal = Calendar.getInstance();
						startCal.setTime(st);
						Calendar endCal = Calendar.getInstance();
						endCal.setTime(en);
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
								Double discRate = new DiscountRateByPeriodDAOImpl(services).getDiscountRateByDate((new java.sql.Date(current.getTime())), Integer.parseInt(hotelId),s );
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

						HttpSession session = request.getSession();
						session.setAttribute("booking", booking);
						session.setAttribute("roomPricePerTypes",pricePerRoomType);
						request.getSession().setAttribute("typesAvailable", typesAvailable);
						request.getSession().setAttribute("numofRoomsAvailableForType", numofRoomsAvailableForType);
						request.getSession().setAttribute("pricePerRoomType", pricePerRoomType);
						request.getRequestDispatcher("chooseRoomType.jsp").forward(request, response);
					}
				}
			}
			catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	private boolean areBookingsAllowed(Date st, Date en, String hotelId) throws ServiceLocatorException {
		DBConnectionFactory services = new DBConnectionFactory();

		List<HotelUnavailabilityBean> unavail = (List<HotelUnavailabilityBean>) new HotelUnavailabilityDAOImpl(services).findByHotelId(Integer.parseInt(hotelId));
		int count = 0;
		boolean ret = false;
		for(HotelUnavailabilityBean b : unavail){
			if((st.compareTo(b.getEnd()) <= 0) && (st.compareTo(b.getStart()) >= 0)){
				count++;
			}
			else if((en.compareTo(b.getEnd()) <= 0) && (en.compareTo(b.getStart()) >= 0)){
				count++;
			}
			else if((st.compareTo(b.getEnd()) <= 0) && (st.compareTo(b.getStart())) >= 0){
				count++;
			}

			else if((st.compareTo(b.getStart()) <= 0) && (en.compareTo(b.getEnd())) >= 0){
				count++;
			}
		}
		if(count > 0){
			return false;
		}
		else{
			return true;
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
