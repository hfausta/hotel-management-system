package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.UserDAO;

public class UserDAOImpl implements UserDAO {
private DBConnectionFactory services = null;

	public UserDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	@Override
	public UserBean findUserById(int id) throws ServiceLocatorException, SQLException {
		Connection con = null;
		UserBean user = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from \"User\" where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user = createUserBean(rs);
			}
			rs.close();
			stmt.close();
		}
		catch (DataAccessException ex){throw ex;}
		finally {
		 if(con != null){
			 con.close();
		 }
		}
		return user;
			
		}
		
	private UserBean createUserBean(ResultSet rs) throws SQLException {
		UserBean user = new UserBean();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setAddress(rs.getString("address"));
		user.setPhoneNumber(rs.getInt("phone"));
		user.setEmail(rs.getString("email"));
		return user;
	}

	@Override
	public void insert(UserBean user) throws ServiceLocatorException, SQLException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("insert into \"User\" (name,address,phoneNum,email) values (?,?,?,?);");
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getAddress());
			stmt.setInt(3, user.getPhoneNumber());
			stmt.setString(4, user.getEmail());
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
	public void delete(int id) throws SQLException, ServiceLocatorException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("delete from \"User\" where id = ?;");
			stmt.setInt(1, id);
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
	public List<UserBean> findAllUsers() throws Exception {
		List<UserBean> users = new ArrayList<UserBean>();
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from \"User\";");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				users.add(createUserBean(rs));
			}
			rs.close();
			stmt.close();
			
		}
		catch (Exception ex){throw ex;}
		finally {
			 if(con != null){
				 con.close();
			 }
			}
		return users;
	}
	@Override
	public UserBean findByEmail(String email) throws SQLException, ServiceLocatorException {
		Connection con = null;
		UserBean user = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from \"User\" where email = ?;");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				user = createUserBean(rs);
			}
			rs.close();
			stmt.close();
			
		}
		finally{
		con.close();
		}
		return user;
	}

}
