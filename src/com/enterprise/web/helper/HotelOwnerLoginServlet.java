package com.enterprise.web.helper;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.HotelOwnerDAOImpl;

/**
 * Servlet implementation class HotelOwnerLoginServlet
 */
public class HotelOwnerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelOwnerLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		request.getSession().invalidate();
		if(userName == null || userName == "" || password == null || password == ""){
			request.setAttribute("error", "Please fill appropriate values here");
			request.getRequestDispatcher("hotelOwner.jsp").forward(request, response);
		} else {
			DBConnectionFactory services = null;
			try {
				services = new DBConnectionFactory();
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HotelOwnerDAOImpl ho = new HotelOwnerDAOImpl(services);
			try {
				if(ho.findOwnerByUserName(userName) != null) {
					HotelOwnerBean h = ho.findOwnerByUserName(userName);
					if(h.getPassword().equals(password)) {
						request.getSession().setAttribute("hotelOwner", h);
						request.getRequestDispatcher("/HotelOwnerServlet").forward(request, response);
					} else {
						request.setAttribute("error", "Inserted incorrect password or user may not exist in role");
						request.getRequestDispatcher("hotelOwner.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("error", "Inserted incorrect password or user may not exist in role");
					request.getRequestDispatcher("hotelOwner.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceLocatorException e) {
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
