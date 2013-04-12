package com.enterprise.dao;

import java.sql.SQLException;
import java.util.List;

import com.enterprise.beans.ConsumerBean;
import com.enterprise.beans.UserBean;
import com.enterprise.common.ServiceLocatorException;

public interface ConsumerDAO {
	ConsumerBean findConsumerById(int id) throws DataAccessException, ServiceLocatorException, SQLException;
	void insert(UserBean user,ConsumerBean customer) throws DataAccessException, ServiceLocatorException, SQLException;
	void delete(int id) throws DataAccessException, SQLException, ServiceLocatorException;
	List <ConsumerBean> findAllConsumers() throws DataAccessException, SQLException, ServiceLocatorException;
}
