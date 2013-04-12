package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelUnavailabilityBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.BookingDAO;
import com.enterprise.dao.DataAccessException;

public class BookingDAOImpl implements BookingDAO {
	private DBConnectionFactory services = null;

	public BookingDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	
	public BookingBean createBookingBean(ResultSet rs) throws SQLException {
		BookingBean booking = new BookingBean();
		booking.setId(rs.getInt("id"));
		booking.setCustomerid(rs.getInt("consumerID"));
		booking.setHotelid(rs.getInt("hotelID"));
		booking.setStart(rs.getDate("start"));
		booking.setEnd(rs.getDate("end"));
		booking.setPin(rs.getString("PIN"));
		return booking;
	}
	
	@Override
	public void insert(BookingBean booking)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("insert into Booking (hotelID,consumerID,start,\"end\",PIN) values (?,?,?,?,?);");
				stmt.setInt(1, booking.getHotelid());
				stmt.setInt(2, booking.getCustomerid());
				stmt.setDate(3, booking.getStart());
				stmt.setDate(4, booking.getEnd());
				stmt.setString(5, booking.getPin());
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("delete from Booking where id = ?;");
				stmt.setInt(1, id);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public BookingBean findBookingById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		BookingBean booking = new BookingBean();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from Booking where id = ?;");
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					booking = createBookingBean(rs);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return booking;
	}

	@Override
	public List<BookingBean> findAllBookings() throws DataAccessException {
		// TODO Auto-generated method stub
		List<BookingBean> bookingList = new ArrayList<BookingBean>(); 
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from Booking;");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					bookingList.add(createBookingBean(rs));
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookingList;
	}

	@Override
	public List<BookingBean> findBookingsByHotelId(int hotelid)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<BookingBean> bookingList = new ArrayList<BookingBean>();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from Booking where hotelID = ?;");
				stmt.setInt(1, hotelid);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					bookingList.add(createBookingBean(rs));
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bookingList;
	}

	@Override
	public BookingBean findBookingByURL(String url) throws DataAccessException {
		// TODO Auto-generated method stub
		BookingBean booking = new BookingBean();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from Booking where url = ?;");
				stmt.setString(1, url);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					booking = createBookingBean(rs);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return booking;
	}
	
	public Integer insertBookingAndRecords(BookingBean booking, List<BookingRecordBean> records) throws SQLException, ServiceLocatorException{
		Connection con = null;
		Integer ret = null;
		insert(booking);
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select max(id) from Booking;");
			ResultSet rs = stmt.executeQuery();
			int id = 0;
			if(rs.next()){
				id = rs.getInt(1);
			}
			ret = id;
			stmt.close();
			rs.close();
			for(BookingRecordBean record : records){
				record.setBookingid(id);
				new BookingRecordDAOImpl(services).insert(record);
			}
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return ret;
		
	}
}
