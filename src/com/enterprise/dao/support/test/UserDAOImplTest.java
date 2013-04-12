package com.enterprise.dao.support.test;

import org.junit.Test;

import com.enterprise.beans.UserBean;
import com.enterprise.common.DBConnectionFactory;
import com.enterprise.common.ServiceLocatorException;
import com.enterprise.dao.support.UserDAOImpl;

import junit.framework.TestCase;
public class UserDAOImplTest {
	@Test
	public void insertUser() throws Exception{
		DBConnectionFactory services = new DBConnectionFactory();
		UserBean user = new UserBean();
		user.setAddress("Address");
		user.setEmail("email");
		user.setName("name");
		user.setPhoneNumber(1);
		
		UserDAOImpl udi = new UserDAOImpl(services);
		udi.insert(user);
		
	}
}
