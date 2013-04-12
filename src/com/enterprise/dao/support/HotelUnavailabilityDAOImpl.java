package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.HotelUnavailabilityBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.HotelUnavailabilityDAO;

public class HotelUnavailabilityDAOImpl implements HotelUnavailabilityDAO {
	private DBConnectionFactory services = null;

	public HotelUnavailabilityDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	
	public HotelUnavailabilityBean createHotelUnavailabilityBean(ResultSet rs) throws SQLException {
		HotelUnavailabilityBean hab = new HotelUnavailabilityBean();
		hab.setHotelid(rs.getInt("hotelid"));
		hab.setStart(rs.getDate("start"));
		hab.setEnd(rs.getDate("end"));
		hab.setStatus(rs.getString("status"));
		hab.setId(rs.getInt("id"));
		return hab;
	}
	
	@Override
	public void insert(HotelUnavailabilityBean hab) throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("insert into HotelUnavailability (hotelID,status,start,\"end\") values (?,?,?,?);");
				stmt.setInt(1, hab.getHotelid());
				stmt.setString(2, hab.getStatus());
				stmt.setDate(3, hab.getStart());
				stmt.setDate(4, hab.getEnd());
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
				PreparedStatement stmt = con.prepareStatement("delete from HotelUnavailability where id = ?");
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
	public List<HotelUnavailabilityBean> findByHotelId(int hotelid) throws DataAccessException {
		// TODO Auto-generated method stub
		List<HotelUnavailabilityBean> hotelUnavailabilityList = new ArrayList<HotelUnavailabilityBean>(); 
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from HotelUnavailability where hotelid = ?;");
				stmt.setInt(1, hotelid);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					hotelUnavailabilityList.add(createHotelUnavailabilityBean(rs));
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
		
		return hotelUnavailabilityList;
	}

	@Override
	public List<HotelUnavailabilityBean> findAllHotelUnavailability()
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<HotelUnavailabilityBean> hotelUnavailabilityList = new ArrayList<HotelUnavailabilityBean>(); 
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from HotelUnavailability");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					hotelUnavailabilityList.add(createHotelUnavailabilityBean(rs));
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
		return hotelUnavailabilityList;
	}

}
