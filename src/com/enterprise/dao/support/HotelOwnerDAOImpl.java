package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.ConsumerBean;
import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.HotelOwnerDAO;

public class HotelOwnerDAOImpl implements HotelOwnerDAO {
	private DBConnectionFactory services = null;

	public HotelOwnerDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	@Override
	public HotelOwnerBean findOwnerById(int id) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		HotelOwnerBean hotelOwner = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelOwner where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				hotelOwner = createHotelOwnerBean(rs);
			}
			rs.close();
			stmt.close();
			
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return hotelOwner;
	}

	private HotelOwnerBean createHotelOwnerBean(ResultSet rs) throws SQLException {
		HotelOwnerBean hotelOwner = new HotelOwnerBean();
		hotelOwner.setId(rs.getInt("id"));
		hotelOwner.setPassword("password");
		hotelOwner.setUsername("userName");
		return hotelOwner;
	}
	@Override
	public void insert(UserBean user, HotelOwnerBean owner)
			throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		new UserDAOImpl(services).insert(user);
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select max(id) from User;");
			int id = stmt.executeQuery().getInt(1);
			stmt = con.prepareStatement("insert into HotelOwner values (?,?,?);");
			stmt.setInt(1, id);
			stmt.setString(2, owner.getUsername());
			stmt.setString(3, owner.getPassword());
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
	public void delete(int id) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("delete from HotelOwner where id = ?;");
			stmt.setInt(1,id);
			stmt.execute();
			stmt.close();
			if(con!=null){
				con.close();	
			}
		}
		finally{
			new UserDAOImpl(services).delete(id);
		}
		
	}

	@Override
	public List<HotelOwnerBean> findAllOwners() throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		List<HotelOwnerBean> owners = new ArrayList<HotelOwnerBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelOwner;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				owners.add(createHotelOwnerBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return owners;
	}

	@Override
	public HotelOwnerBean findOwnerByUserName(String userName) throws SQLException, ServiceLocatorException {
		Connection con = null;
		HotelOwnerBean owner = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelOwner where userName = ?;");
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				owner = createHotelOwnerBean(rs);
			}
			stmt.close();
			rs.close();
			}
		finally{
			if(con != null){
				con.close();
			}
		}
		return owner;
	}
	
	public void initialiseHotelOwnerDB() throws DataAccessException, ServiceLocatorException, SQLException{
		for(int i =0 ;i < 5 ; i++){
			UserBean user = new UserBean();
			user.setAddress(i + " Street, New York");
			user.setEmail("comp9321.assignment2.ch@gmail.com");
			user.setName("owner " + i);
			user.setPhoneNumber(i);
			
			HotelOwnerBean owner = new HotelOwnerBean();
			owner.setUsername("owner " + i);
			owner.setPassword("owner " + i);
			
			insert(user, owner);
					
		}
	}

}
