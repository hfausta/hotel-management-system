package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.HotelRoomBean;
import com.enterprise.common.ServiceLocatorException;

public interface HotelRoomDAO{
	void insert (HotelRoomBean room) throws DataAccessException;
	void delete(int id) throws DataAccessException; 
	void deleteByHotelId(int id) throws DataAccessException;
	List<HotelRoomBean> findByHotelId(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	List<HotelRoomBean> findByRoomType(String type) throws DataAccessException;
	int countRoomTypeAndHotelId(int id,String type) throws DataAccessException, ServiceLocatorException, SQLException;
	List<HotelRoomBean> findAllHotelRooms() throws DataAccessException;
	List<String> typesThatExistForAHotel(int id)throws DataAccessException, ServiceLocatorException, SQLException;
}
