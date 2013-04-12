package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.HotelOwnerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.ServiceLocatorException;

public interface HotelOwnerDAO {
	HotelOwnerBean findOwnerById(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	void insert(UserBean user,HotelOwnerBean owner) throws DataAccessException, ServiceLocatorException, SQLException;
	void delete(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	List <HotelOwnerBean> findAllOwners() throws DataAccessException, SQLException, ServiceLocatorException;
	HotelOwnerBean findOwnerByUserName(String userName) throws SQLException, ServiceLocatorException;

}
