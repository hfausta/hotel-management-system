package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.HotelBean;
import com.enterprise.beans.HotelManagerBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.HotelDAO;

public class HotelDAOImpl implements HotelDAO {
	private DBConnectionFactory services = null;

	public HotelDAOImpl (DBConnectionFactory services){
		this.services = services;
	}	
	

	@Override
	public HotelBean findById(int id) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		HotelBean hotel = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Hotel where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				hotel = createHotelBean(rs);
			}
			rs.close();
			stmt.close();
			
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return hotel;
	}

	private HotelBean createHotelBean(ResultSet rs) throws SQLException {
		HotelBean hotel = new HotelBean();
		hotel.setName(rs.getString("name"));
		hotel.setAddress(rs.getString("address"));
		hotel.setId(rs.getInt("id"));
		hotel.setManagerid(rs.getInt("managerID"));
		hotel.setOwnerid(rs.getInt("ownerID"));
		hotel.setPhone(rs.getInt("phone"));
		return hotel;
	}


	@Override
	public List<HotelBean> findByOwnerId(int id) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		List<HotelBean> hotels = new ArrayList<HotelBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Hotel where ownerID = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				hotels.add(createHotelBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return hotels;
	}

	@Override
	public HotelBean findByManagerId(int id) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		HotelBean hotel = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Hotel where managerID = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				hotel = createHotelBean(rs);
			}
			rs.close();
			stmt.close();
			
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return hotel;
	}

	@Override
	public void insert(HotelBean hotel) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("insert into Hotel (name,managerID,ownerID,address,phone) values (?,?,?,?,?);");
			stmt.setString(1,hotel.getName());
			stmt.setInt(2, hotel.getManagerid());
			stmt.setInt(3,hotel.getOwnerid());
			stmt.setString(4, hotel.getAddress());
			stmt.setInt(5, hotel.getPhone());
			stmt.execute();
			stmt.close();
		}
		catch (DataAccessException ex){throw ex;}
		finally {
			 if(con != null){
				 con.close();
			 }
			}
	}

	@Override
	public void delete(int id) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("delete from Hotel where id = ?;");
			stmt.setInt(1,id);
			stmt.execute();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();	
			}
		}
	}
	
	@Override
	public List<HotelBean> findAllHotels() throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		List<HotelBean> hotels = new ArrayList<HotelBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Hotel;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				hotels.add(createHotelBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return hotels;
	}

}
