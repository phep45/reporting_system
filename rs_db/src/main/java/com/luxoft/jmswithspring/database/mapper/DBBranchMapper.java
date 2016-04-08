package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.Branch;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBBranchMapper implements RowMapper<Branch> {

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ADDRESS = "ADDRESS";

    @Override
    public Branch mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt(COLUMN_ID);
        String address = resultSet.getString(COLUMN_ADDRESS);

        return new Branch(id, address);
    }
}
