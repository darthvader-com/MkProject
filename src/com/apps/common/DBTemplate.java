package com.apps.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.apps.dao.UserDAO;

/**
 * MYSQL 사용을 위한 DBTemplate
 * 
 * @author AHJ
 */
public abstract class DBTemplate {
	
	protected Properties commonProperties = new Properties();
	protected Properties sqlProperties = new Properties();
	private String path = UserDAO.class.getResource("../common/").getPath();

	public DBTemplate() {}
	
	/**
	 * MySQL 데이터베이스 연결을 위해 사용함 
	 * 
	 * @author AHJ	 
	 * @param conn
	 * @return Connection
	 */
	public Connection connectDB(Connection conn) {
		try {
			commonProperties.load(new FileReader(path + "common.properties"));
			sqlProperties.load(new FileReader(path + "sql.properties"));

			String url = commonProperties.getProperty("DB_HOST") + commonProperties.getProperty("DB_NAME") + "?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, commonProperties.getProperty("DB_USER"), commonProperties.getProperty("DB_PASSWORD"));
			conn.setAutoCommit(false);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return conn;
	}
	
	/** 
	 * SELECT Transaction 처리용
	 * 
	 * @author AHJ
	 * @param conn
	 * @param rtn
	 * @throws Exception
	 */
	public void processTx(Connection conn, int rtn) throws Exception {
		if (rtn == 1) {
			conn.commit();
		} else {
			conn.rollback();
			throw new Exception("processTX Error");
		}
	}
	
	/**
	 * INSERT, UPDATE, DELETE Transaction 처리용
	 * 
	 * @author AHJ
	 * @param rs
	 * @param pstmt
	 * @param conn
	 * @throws Exception
	 */
	public void readTx(ResultSet rs, PreparedStatement pstmt, Connection conn) throws Exception {
		try {
			if (rs != null)    rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null)  conn.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}

}
