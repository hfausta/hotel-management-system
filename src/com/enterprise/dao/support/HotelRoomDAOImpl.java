package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.HotelRoomDAO;

public class HotelRoomDAOImpl implements HotelRoomDAO {
	private DBConnectionFactory services = null;

	public HotelRoomDAOImpl(DBConnectionFactory services){
		this.services = services;
	}

	@Override
	public void insert(HotelRoomBean room) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByHotelId(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HotelRoomBean> findByHotelId(int id) throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		List<HotelRoomBean> hotelRooms = new ArrayList<HotelRoomBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelRoom where hotelID = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				hotelRooms.add(createHotelRoomBean(rs));
			}
			stmt.close();
			rs.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return hotelRooms;
	}

	private HotelRoomBean createHotelRoomBean(ResultSet rs) throws SQLException {
		HotelRoomBean hotelRoom = new HotelRoomBean();
		hotelRoom.setHotelid(rs.getInt("id"));
		hotelRoom.setRoomnum(rs.getInt("roomNum"));
		hotelRoom.setStatus(rs.getString("status"));
		hotelRoom.setType(rs.getString("type"));
		hotelRoom.setId(rs.getInt("id"));
		//System.out.println(hotelRoom.getType() + hotelRoom.getHotelid());
		return hotelRoom;
	}

	@Override
	public List<HotelRoomBean> findByRoomType(String type)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countRoomTypeAndHotelId(int id, String type)throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		int res = 0;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select count(*) from HotelRoom where hotelID = ? and \"type\" = ?;");
			stmt.setInt(1, id);
			stmt.setString(2, type);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
			res = rs.getInt(1);
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
	
	public List<String> typesThatExistForAHotel(int id)throws DataAccessException, ServiceLocatorException, SQLException {
		Connection con = null;
		List<String> types = new ArrayList<String>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select distinct \"type\",id  from HotelRoom where hotelID = ? order by id;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String s= rs.getString(1);
				if(!types.contains(s))
				types.add(s);
			}
			stmt.close();
			rs.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return types;	
	}

	@Override
	public List<HotelRoomBean> findAllHotelRooms() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<HotelRoomBean> availableForRoomTypeAtHotel(String roomType,int hotelId) throws ServiceLocatorException, SQLException{
		Connection con = null;
		List<HotelRoomBean> roomsAvail = new ArrayList<HotelRoomBean>();
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select *  from HotelRoom where hotelID = ? and \"type\" = ? and 'AVAILABLE' = status ;");
			stmt.setInt(1, hotelId);
			stmt.setString(2, roomType);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				roomsAvail.add(createHotelRoomBean(rs));
			}
			stmt.close();
			rs.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
		return roomsAvail;		
	}
	
	
	
	public HotelRoomBean findById(int id) throws ServiceLocatorException, SQLException{
		Connection con = null;
		HotelRoomBean res = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select * from HotelRoom where id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				res = createHotelRoomBean(rs);
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
	
	public void updateRoom(HotelRoomBean room) throws SQLException, ServiceLocatorException{
		Connection con = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE HotelRoom SET status = ? WHERE id = ?;");
			stmt.setInt(2, room.getId());
			stmt.setString(1, room.getStatus());
		    stmt.execute();
			stmt.close();
		}
		finally{
			if(con != null){
				con.close();
			}
		}
	}

}
