package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.RoomTypeBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.RoomTypeDAO;

public class RoomTypeDAOImpl implements RoomTypeDAO {
DBConnectionFactory services = null;
public RoomTypeDAOImpl(DBConnectionFactory services){
	this.services = services;
}
	@Override
	public void insert(RoomTypeBean rtype) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection conn = null;
		try{
			conn = services.createConnection();
			PreparedStatement stmt = conn.prepareStatement("insert into RoomType values (?,?,?,?);");
			stmt.setString(1, rtype.getId());
			stmt.setInt(2, rtype.getPeople());
			stmt.setBoolean(3, rtype.isExtraBed());
			stmt.setDouble(4, rtype.getPrice());
			stmt.execute();
			stmt.close();
		}
		finally {
			if(conn != null){
				conn.close();
			}
		}
		
	}

	@Override
	public void delete(String rtype) throws DataAccessException, SQLException, ServiceLocatorException {
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("delete from RoomType where id = ?;");
			stmt.setString(1, rtype);
			stmt.execute();
			stmt.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		
	}

	@Override
	public RoomTypeBean findByRoomType(String rtype) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		RoomTypeBean type = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from RoomType where id = ?;");
			stmt.setString(1, rtype);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				type = createRoomTypeBean(rs);
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return type;
	}

	private RoomTypeBean createRoomTypeBean(ResultSet rs) throws SQLException {
		RoomTypeBean type = new RoomTypeBean();
		type.setExtraBed(rs.getBoolean("extraBed"));
		type.setId(rs.getString("id"));
		type.setPeople(rs.getInt("numPeople"));
		type.setPrice(rs.getDouble("priceRate"));
		return type;
	}
	@Override
	public List<RoomTypeBean> findAllRoomTypes() throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		List<RoomTypeBean> types = new ArrayList<RoomTypeBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from RoomType order by id;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				types.add(createRoomTypeBean(rs));
			}
			rs.close();
			stmt.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return types;
	}
	
	public Double getPriceRateByRoomType(String type) {
		Connection con = null;
		Double rate = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("select priceRate from RoomType where ? = id;");
				stmt.setString(1, type);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					rate = rs.getDouble(1);
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
		return rate;
	}

}
