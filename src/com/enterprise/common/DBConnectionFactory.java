package com.enterprise.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.*;
import javax.sql.DataSource;

/**
 * @author $author
 */
public class DBConnectionFactory extends AbstractJndiLocator {

	private DataSource ds;

	public DBConnectionFactory() throws ServiceLocatorException {
	}
	
	public DBConnectionFactory(DataSource ds) throws ServiceLocatorException {
		this.ds = ds;
	}
	
	public Connection createConnection() throws ServiceLocatorException {
		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
			throw new ServiceLocatorException("Unable to create connection: " + e.getMessage(), e);
		}
	}

	/**
	 * Finds a data source by looking up the initial context
	 * @return
	 * @throws ServiceLocatorException
	 */
	public DataSource getDataSource() throws ServiceLocatorException {
		if (ds == null) {
			try {
				ds = (DataSource) lookup("jdbc/HSQLDB");
			} catch (NamingException e) {
				throw new ServiceLocatorException("Unable to locate data source; " + e.getMessage(), e);
			}
		}
		return ds;
	}
}
