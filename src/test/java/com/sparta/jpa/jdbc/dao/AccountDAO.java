package com.sparta.jpa.jdbc.dao;

import com.sparta.jpa.jdbc.vo.AccountVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    private static final String url = "jdbc:postgresql://localhost:5432/messenger";
    private static final String username = "teasun";
    private static final String password = "pass";
    //SQL 관련 명령어
    private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
        + "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
    // coalesce 은 Postgresql 용 IFNULL
    private final String ACCOUNT_GET = "SELECT * FROM account WHERE ID = ?";
    //JDBC 관련 변수
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    //CRUD 기능의 메소드 구현
    //계정 등록
    public Integer insertAccount(AccountVO vo) {
        var id = -1;
        try {
            String[] returnId = {"id"};
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(ACCOUNT_INSERT, returnId);
            stmt.setString(1, vo.getUsername());
            stmt.setString(2, vo.getPassword());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public AccountVO selectAccount(Integer id) {
        AccountVO account = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(ACCOUNT_GET);
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                account = new AccountVO();
                account.setId(rs.getInt("ID"));
                account.setUsername(rs.getString("USERNAME"));
                account.setPassword(rs.getString("PASSWORD"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

}
