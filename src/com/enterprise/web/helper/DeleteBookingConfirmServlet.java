package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.BookingRecordBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.BookingRecordDAOImpl;

/**
 * Servlet implementation class DeleteBookingConfirmServlet
 */
public class DeleteBookingConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBookingConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] del = request.getParameterValues("deleteRecord");
		if(del == null) {
			request.getRequestDispatcher("manageBooking.jsp").forward(request, response);
		} else {
			try {
				DBConnectionFactory services = new DBConnectionFactory();
				request.getSession().setAttribute("del", del);
				ArrayList<BookingRecordBean> delRec = new ArrayList<BookingRecordBean>();
				for(int i = 0;i<del.length;i++) {
					delRec.add(new BookingRecordDAOImpl(services).findById(Integer.parseInt(del[i])));
				}
				request.getSession().setAttribute("delRec", delRec);
				request.getRequestDispatcher("deleteConfirmBooking.jsp").forward(request, response);
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
