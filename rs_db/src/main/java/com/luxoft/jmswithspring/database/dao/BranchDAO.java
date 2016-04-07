package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBBranchMapper;
import com.luxoft.jmswithspring.model.Branch;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class BranchDAO extends BaseDAO<Branch> {

    private static final String INSERT_BRANCH = "INSERT INTO BRANCH(ID, ADDRESS) VALUES (?, ?)";
    private static final String SELECT_BRANCH = "SELECT * FROM BRANCH WHERE ID = ?";
    private static final String UPDATE_BRANCH = "UPDATE BRANCH SET ADDRESS = ? WHERE ID = ?";

    @Override
    public void safelyInsert(Branch branch) {
        if(get(branch.getId()) == null) {
            insert(branch);
        }
        else {
            update(branch);
        }
    }

    @Override
    public void insert(Branch branch) {
        String sql = INSERT_BRANCH;
        jdbcTemplate.update(sql, branch.getId(), branch.getAddress());
        log.info("BRANCH inserted");
    }

    @Override
    public void update(Branch branch) {
        String sql = UPDATE_BRANCH;
        jdbcTemplate.update(sql, branch.getAddress(), branch.getId());
        log.info("BRANCH updated");
    }

    @Override
    public Branch get(int id) {
        String sql = SELECT_BRANCH;
        Branch branch;
        try {
            branch = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBBranchMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return branch;
    }
}
