package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.ConsumerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.ConsumerDAO;
import com.enterprise.dao.DataAccessException;

public class ConsumerDAOImpl implements ConsumerDAO {
	private DBConnectionFactory services = null;

	public ConsumerDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	@Override
	public ConsumerBean findConsumerById(int id) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		ConsumerBean consumer = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Consumer where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				consumer = createConsumerBean(rs);
				stmt.close(); 
			}
			rs.close();
			stmt.close();
			con.close();
		}
		catch (DataAccessException ex){throw ex;}
		finally {
		 if(con != null){
			 con.close();
		 }
		}
		return consumer;
			
		}

	private ConsumerBean createConsumerBean(ResultSet rs) throws SQLException {
		ConsumerBean consumer = new ConsumerBean();
		consumer.setId(rs.getInt("id"));
		return consumer;
	}


	public Integer insert(UserBean user)
			throws DataAccessException, ServiceLocatorException, SQLException {
		new UserDAOImpl(services).insert(user);
		Connection con = null;
		Integer id = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select max(id) from Consumer;");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			System.out.println(id);
			stmt = con.prepareStatement("insert into Consumer values (?);");
			stmt.setInt(1, id+1);
			stmt.execute();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return id+1;
	}

	@Override
	public void delete(int id) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("delete from Consumer where id = ?;");
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
	public List<ConsumerBean> findAllConsumers() throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		List<ConsumerBean> consumers = new ArrayList<ConsumerBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from Consumer;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				consumers.add(createConsumerBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con!=null){
				con.close();
			}
		}
		return consumers;
	}
	@Override
	public void insert(UserBean user, ConsumerBean customer)
			throws DataAccessException, ServiceLocatorException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
