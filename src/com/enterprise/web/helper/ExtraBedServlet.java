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
import com.enterprise.beans.HotelBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.HotelDAOImpl;

/**
 * Servlet implementation class ExtraBedServlet
 */
public class ExtraBedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExtraBedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arr = request.getParameterValues("extraBed");
		List<String>checkBoxValues = null;

		if(arr== null){
			checkBoxValues = new ArrayList<String>();
		}
		else{
			String[] vals = request.getParameterValues("extraBed");
			if(vals == null){
				request.setAttribute("error","Please start from this page");
				request.getRequestDispatcher("consumerHome.jsp").forward(request, response);
			}
			else{
				checkBoxValues =  Arrays.asList(vals);
			}
		}
		ArrayList<BookingRecordBean> records = (ArrayList<BookingRecordBean>) request.getSession().getAttribute("bookingRecords");
		if(records == null){
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("/ConsumerHomeServlet").forward(request, response);
		}
		else{
		for(Integer i =0 ; i < records.size() ; i++){
			if(checkBoxValues.contains(i.toString())){
				records.get(i).setExtrabed(true);

			}
			else{
				records.get(i).setExtrabed(false);
			}
		}
		DBConnectionFactory services = null;
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HotelBean hotel = null;
		try {
			BookingBean booking = (BookingBean) request.getSession().getAttribute("booking");
			int hotelID = booking.getHotelid();
			hotel = new HotelDAOImpl(services).findById(hotelID);
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
		request.getSession().setAttribute("hotelName", hotel.getName());
		request.getSession().setAttribute("hotelAddress",hotel.getAddress());
		request.getRequestDispatcher("payment.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
