package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.HotelBean;
import com.enterprise.common.ServiceLocatorException;

public interface HotelDAO {
	HotelBean findById(int id) throws DataAccessException, SQLException, ServiceLocatorException;
	List <HotelBean> findByOwnerId(int id) throws DataAccessException, SQLException, ServiceLocatorException;
	HotelBean findByManagerId(int id) throws DataAccessException, SQLException, ServiceLocatorException;
	void insert(HotelBean hotel) throws DataAccessException, ServiceLocatorException, SQLException;
	void delete(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	List <HotelBean> findAllHotels() throws DataAccessException, SQLException, ServiceLocatorException;
}
