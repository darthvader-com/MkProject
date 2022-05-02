package com.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.apps.common.DBTemplate;
import com.apps.dto.UserDTO;

public class UserDAO extends DBTemplate{

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// DB연결
	public UserDAO() throws Exception {
		conn = connectDB(conn);
	}

	// 가입
	public int register(UserDTO dto) throws Exception{
		int rtn = 0;
		String SQL = sqlProperties.getProperty("REGISTER");

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());

			rtn = pstmt.executeUpdate();

			processTx(conn, rtn);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return rtn;
	}

	// 사용자리스트
	public List<UserDTO> listUser() throws Exception {
		String SQL = sqlProperties.getProperty("LISTUSER");
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setUserId(rs.getString("user_id"));
				dto.setName(rs.getString("name"));
				dto.setPassword(rs.getString("password"));
				list.add(dto);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			readTx(rs, pstmt, conn);
		}
		return list;
	}

}
