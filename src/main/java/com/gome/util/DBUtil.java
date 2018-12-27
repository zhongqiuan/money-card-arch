package com.gome.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author azq
 * @createtime 2018年12月18日
 */
public class DBUtil {

	private static ComboPooledDataSource dataSource;

	static {

		try {
			dataSource = new ComboPooledDataSource();
			HashMap<String, String> map = ReadPropUtil.readProperties();
			dataSource.setDriverClass(map.get("jdbcdriver"));
			dataSource.setJdbcUrl(map.get("jdbc.url"));
			dataSource.setUser(map.get("jdbc.username"));
			dataSource.setPassword(map.get("jdbc.password"));
			dataSource.setMaxPoolSize(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return conn;
	}
}
