package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.BookingRecordDAO;
import com.enterprise.dao.DataAccessException;

public class BookingRecordDAOImpl implements BookingRecordDAO {
	private DBConnectionFactory services = null;

	public BookingRecordDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	
	public BookingRecordBean createBookingRecordBean(ResultSet rs) throws SQLException {
		BookingRecordBean bookingRecord = new BookingRecordBean();
		bookingRecord.setBookingid(rs.getInt("bookingID"));
		bookingRecord.setExtrabed(rs.getBoolean("extraBed"));
		bookingRecord.setRoomId(rs.getInt("room"));
		bookingRecord.setRoomtype(rs.getString("roomType"));
		bookingRecord.setHotelid(rs.getInt("hotelID"));
		bookingRecord.setPrice(rs.getDouble("price"));
		bookingRecord.setStart(rs.getDate("start"));
		bookingRecord.setEnd(rs.getDate("end"));
		bookingRecord.setRoomNum(rs.getInt("rmNumber"));
		bookingRecord.setId(rs.getInt("id"));
		return bookingRecord;
	}
	
	@Override
	public void insert(BookingRecordBean record) throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("insert into BookingRecord (bookingID,roomType,extraBed,hotelID,price,start,\"end\") values (?,?,?,?,?,?,?);");
				stmt.setInt(1, record.getBookingid());
				stmt.setString(2, record.getRoomType());
				stmt.setBoolean(3, record.isExtrabed());
				stmt.setInt(4,record.getHotelid());
				stmt.setDouble(5, record.getPrice());
				stmt.setDate(6, record.getStart());
				stmt.setDate(7,record.getEnd());
				
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
	
	public void update(int roomId,int roomNum){
		//TOO DOOO
	}

	@Override
	public void deleteByBooking(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("delete from BookingRecord where bookingID = ?;");
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
	public List<BookingRecordBean> findByBookingId(int id)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		List<BookingRecordBean> bookingRecord = new ArrayList<BookingRecordBean>();
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("select * from BookingRecord where bookingID = ?;");
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					bookingRecord.add(createBookingRecordBean(rs));
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
		return bookingRecord;
	}

	@Override
	public List<BookingRecordBean> findAllBookingRecord()
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<BookingRecordBean> bookingRecord = new ArrayList<BookingRecordBean>();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("select * from BookingRecord;");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					bookingRecord.add(createBookingRecordBean(rs));
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
		return bookingRecord;
	}
	
	public List<BookingRecordBean> findAllBookingRecordWithHotelIDAndRoomType(int hotelId,String type)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<BookingRecordBean> bookingRecord = new ArrayList<BookingRecordBean>();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("select * from BookingRecord where hotelID = ? and roomType = ?;");
				stmt.setInt(1, hotelId);
				stmt.setString(2, type);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					bookingRecord.add(createBookingRecordBean(rs));
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
		return bookingRecord;
	}
	
	public BookingRecordBean findById(int id) throws SQLException, ServiceLocatorException{
		Connection con = null;
		BookingRecordBean res = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from BookingRecord where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				res = createBookingRecordBean(rs);
			}
			stmt.close();
			rs.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return res;		
	}

	public void updateRecord(BookingRecordBean record) throws SQLException, ServiceLocatorException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE BookingRecord SET room  = ? , rmnumber = ? WHERE id = ?;");
			stmt.setInt(1,  record.getRoomId());
			if(record.getRoomId() != 0){
				stmt.setInt(1,  record.getRoomId());
				stmt.setInt(2, record.getRoomNum());
			}
			else{
				stmt.setNull(1,java.sql.Types.INTEGER);
				stmt.setNull(2,java.sql.Types.INTEGER);
			}
			stmt.setInt(3, record.getId());
			stmt.execute();
			
			stmt.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		
	}
	
	public void deleteByRecordId(int id){
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("delete from BookingRecord where id = ?;");
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
}
