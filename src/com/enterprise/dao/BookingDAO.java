package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.BookingBean;
import com.enterprise.beans.BookingRecordBean;
import com.enterprise.beans.ConsumerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.ServiceLocatorException;

public interface BookingDAO {
	void insert(BookingBean booking) throws DataAccessException;
	void delete(int id) throws DataAccessException;
	BookingBean findBookingById(int id) throws DataAccessException;
	List <BookingBean> findAllBookings() throws DataAccessException;
	List <BookingBean> findBookingsByHotelId(int hotelid) throws DataAccessException;
	BookingBean findBookingByURL(String url) throws DataAccessException;
	Integer insertBookingAndRecords(BookingBean booking, List<BookingRecordBean> records) throws SQLException, ServiceLocatorException;
}
