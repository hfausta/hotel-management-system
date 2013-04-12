package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.HotelManagerBean;
import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.HotelManagerDAO;

public class HotelManagerDAOImpl implements HotelManagerDAO {
	private DBConnectionFactory services = null;

	public HotelManagerDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	@Override
	public HotelManagerBean findManagerById(int id) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		HotelManagerBean hotelManager = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelManager where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				hotelManager = createHotelManagerBean(rs);
			}
			rs.close();
			stmt.close();
			
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return hotelManager;
	}

	private HotelManagerBean createHotelManagerBean(ResultSet rs) throws SQLException {
		HotelManagerBean hotelManager = new HotelManagerBean();
		hotelManager.setId(rs.getInt("id"));
		hotelManager.setPassword("password");
		hotelManager.setUsername("userName");
		return hotelManager;
	}
	@Override
	public void insert(UserBean user, HotelManagerBean manager)
			throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		new UserDAOImpl(services).insert(user);
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select max(id) from User;");
			int id = stmt.executeQuery().getInt(1);
			stmt = con.prepareStatement("insert into HotelManager values (?,?,?);");
			stmt.setInt(1, id);
			stmt.setString(2, manager.getUsername());
			stmt.setString(3, manager.getPassword());
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
			PreparedStatement stmt = con.prepareStatement("delete from HotelManager where id = ?;");
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
	public List<HotelManagerBean> findAllManagers() throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		List<HotelManagerBean> managers = new ArrayList<HotelManagerBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelManager;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				managers.add(createHotelManagerBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return managers;
	}

	@Override
	public HotelManagerBean findOwnerByUserName(String userName)throws SQLException, ServiceLocatorException {
		Connection con = null;
		HotelManagerBean manager = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelManager where userName = ?;");
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				manager = createHotelManagerBean(rs);
			}
			stmt.close();
			rs.close();
			}
		finally{
			if(con != null){
				con.close();
			}
		}
		return manager;
	}
	
	public void initialiseHotelManagerfDB() throws DataAccessException, ServiceLocatorException, SQLException{
		for(int i =0 ;i < 5 ; i++){
			UserBean user = new UserBean();
			user.setAddress(i + " Street, New York");
			user.setEmail("comp9321.assignment2.ch@gmail.com");
			user.setName("manager " + i);
			user.setPhoneNumber(i);
			
			HotelManagerBean manager = new HotelManagerBean();
			manager.setUsername("manager " + i);
			manager.setPassword("manager " + i);
			
			insert(user, manager);
					
		}
	}

}
