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
import com.enterprise.beans.RoomTypeBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.support.RoomTypeDAOImpl;

/**
 * Servlet implementation class SelectRoomType
 */
public class SelectRoomTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectRoomTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> types = (ArrayList<String>) request.getSession().getAttribute("typesAvailable");
		ArrayList<String> selectedRoomsForRoomType = new ArrayList<String>();
		
		if(request.getSession().getAttribute("booking") == null || types == null){
			request.setAttribute("error", "Please start from this page");
			request.getRequestDispatcher("/ConsumerHomeServlet").forward(request,response);
		}
		else{
			int zeroCount = 0;
			for(int i =0 ; i < types.size(); i++){
				String current =(String) request.getParameter(""+i);
				selectedRoomsForRoomType.add((String) request.getParameter(""+i));
				if(current.equals("0")){
					zeroCount++;
				}
			}
			
			
			if(zeroCount == types.size()){
				request.getSession().invalidate();
				request.setAttribute("error", "No rooms selected");
				request.getRequestDispatcher("/ConsumerHomeServlet").forward(request,response);				
			}
			else{
				List<Double> pricePerRoomType = (List<Double>) request.getSession().getAttribute("roomPricePerTypes");
				List<BookingRecordBean> records = new ArrayList<BookingRecordBean>();
				List<String> typesSelected = new ArrayList<String>();
				BookingBean booking = (BookingBean) request.getSession().getAttribute("booking");
		
				for(int i =0 ; i < types.size() ; i ++){
					for(int j = 0; j < Integer.parseInt(selectedRoomsForRoomType.get(i));j++){
						BookingRecordBean record = new BookingRecordBean();
						record.setEnd(booking.getEnd());
						record.setStart(booking.getStart());
						record.setHotelid(booking.getHotelid());
						record.setRoomtype(types.get(i));
						record.setPrice(pricePerRoomType.get(i));
						records.add(record);
						int roomNum = i +1;
						if(!typesSelected.contains(types.get(i))){
							typesSelected.add(types.get(i));
						}
					}
				}
				
				
				
				request.getSession().setAttribute("typesSelected", typesSelected);
				request.getSession().setAttribute("bookingRecords", records);
				request.getRequestDispatcher("extraBed.jsp").forward(request, response);
				
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
