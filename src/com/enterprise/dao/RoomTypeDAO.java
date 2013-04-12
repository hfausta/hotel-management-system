package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.RoomTypeBean;
import com.enterprise.common.ServiceLocatorException;

public interface RoomTypeDAO {
	void insert(RoomTypeBean rtype) throws DataAccessException, ServiceLocatorException, SQLException;
	void delete(String rtype) throws DataAccessException, SQLException, ServiceLocatorException;
	RoomTypeBean findByRoomType(String rtype) throws DataAccessException, ServiceLocatorException, SQLException;
	List<RoomTypeBean> findAllRoomTypes() throws DataAccessException, ServiceLocatorException, SQLException;

}
