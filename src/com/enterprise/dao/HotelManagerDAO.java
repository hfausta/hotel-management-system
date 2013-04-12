package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.HotelManagerBean;
import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.ServiceLocatorException;

public interface HotelManagerDAO {
	
	HotelManagerBean findManagerById(int id) throws DataAccessException, SQLException, ServiceLocatorException;
	void insert(UserBean user,HotelManagerBean manager) throws DataAccessException, ServiceLocatorException, SQLException;
	void delete(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	List <HotelManagerBean> findAllManagers() throws DataAccessException, SQLException, ServiceLocatorException;
	HotelManagerBean findOwnerByUserName(String userName) throws SQLException, ServiceLocatorException;
}
