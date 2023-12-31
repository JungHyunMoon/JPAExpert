package com.sparta.jpa.jdbc.template;

import com.sparta.jpa.jdbc.vo.AccountVO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<AccountVO> {

    @Override
    public AccountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        var vo = new AccountVO();

        vo.setId(rs.getInt("ID"));
        vo.setUsername(rs.getString("USERNAME"));
        vo.setPassword(rs.getString("PASSWORD"));
        return vo;
    }
}
