package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.HotelManagerBean;
import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.business.exception.ServiceLocatorException;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.dao.support.HotelManagerDAOImpl;
import com.enterprise.dao.support.HotelOwnerDAOImpl;

/**
 * Servlet implementation class StaffLoginServlet
 */
public class HotelManagerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HotelManagerLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		subFunction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		subFunction(request,response);
	}
	
	protected void subFunction(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		request.getSession().invalidate();

		if(userName == null || userName == "" || password == null || password == ""){
			request.setAttribute("error", "Please fill appropriate values here");
			request.getRequestDispatcher("hotelManager.jsp").forward(request, response);
		}
		else{
			DBConnectionFactory services = null;
			try {
				services = new DBConnectionFactory();
			} catch (com.enterprise.common.ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HotelManagerDAOImpl hm = new HotelManagerDAOImpl(services);
			try {
				if(hm.findOwnerByUserName(userName) != null) {
					HotelManagerBean h = hm.findOwnerByUserName(userName);
					if(h.getPassword().equals(password)) {
						request.getSession().setAttribute("hotelManager", h);
						request.getRequestDispatcher("/HotelManagerServlet").forward(request, response);
					} else {
						request.setAttribute("error", "Inserted incorrect password or user may not exist in role");
						request.getRequestDispatcher("hotelManager.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("error", "Inserted incorrect password or user may not exist in role");
					request.getRequestDispatcher("hotelManager.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.enterprise.common.ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}