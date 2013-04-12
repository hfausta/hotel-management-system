package com.enterprise.dao.support;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.DiscountRateByPeriodBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.DataAccessException;
import com.enterprise.dao.DiscountRateByPeriodDAO;

public class DiscountRateByPeriodDAOImpl implements DiscountRateByPeriodDAO {
	private DBConnectionFactory services = null;

	public DiscountRateByPeriodBean createDiscountRateBean(ResultSet rs) throws SQLException {
		DiscountRateByPeriodBean discountRate = new DiscountRateByPeriodBean();
		discountRate.setHotelid(rs.getInt("hotelID"));
		discountRate.setDiscountrate(rs.getDouble("discRate"));
		discountRate.setStart(rs.getDate("start"));
		discountRate.setEnd(rs.getDate("end"));
		discountRate.setId(rs.getInt("id"));
		discountRate.setRoomType(rs.getString("roomType"));
		return discountRate;
	}
	
	public DiscountRateByPeriodDAOImpl(DBConnectionFactory services){
		this.services = services;
	}
	@Override
	public void insert(DiscountRateByPeriodBean drpb)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("insert into DiscountRate (hotelID,roomType,start,\"end\",discrate) values (?,?,?,?,?);");
				stmt.setInt(1, drpb.getHotelid());
				stmt.setString(2, drpb.getRoomType());
				stmt.setDate(3, drpb.getStart());
				stmt.setDate(4, drpb.getEnd());
				stmt.setDouble(5, drpb.getDiscountrate());
				
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
	public void delete(int id) {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("delete from DiscountRate where id = ?;");
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
	public List<DiscountRateByPeriodBean> findAllDiscountRatesByHotel(int hotelid)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection con = null;
		List<DiscountRateByPeriodBean> discountList = new ArrayList<DiscountRateByPeriodBean>();
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select * from DiscountRate where hotelID = ?;");
				stmt.setInt(1, hotelid);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					discountList.add(createDiscountRateBean(rs));
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
		return discountList;
	}

	@Override
	public List<DiscountRateByPeriodBean> findAllDiscountRates()
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<DiscountRateByPeriodBean> discountList = new ArrayList<DiscountRateByPeriodBean>();
		Connection con = null;
		try {
			con = services.createConnection();
			try {
				PreparedStatement stmt = con.prepareStatement("select * from DiscountRate;");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					discountList.add(createDiscountRateBean(rs));
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
		return discountList;
	}

	@Override
	public DiscountRateByPeriodBean findDiscountRatesById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		DiscountRateByPeriodBean drbp = new DiscountRateByPeriodBean();
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement("select * from DiscountRate where id = ?;");
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					drbp = createDiscountRateBean(rs);
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
		return drbp;
	}
	
	public Double getDiscountRateByDate(Date curr, int hotelId, String roomType) throws ServiceLocatorException, SQLException{
		Connection con = null;
		Double rate = null;
		try{
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("select discRate from DiscountRate where ? between start and \"end\" and ? = hotelid and ? = roomtype;" );
			stmt.setDate(1, curr);
			stmt.setInt(2, hotelId);
			stmt.setString(3, roomType);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				rate = rs.getDouble(1);
			}
			rs.close();
			stmt.close();
		}
		finally {
			if(con != null){
				con.close();
			}
		}
		return rate;
	}
}
