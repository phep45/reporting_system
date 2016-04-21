package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.AccessType;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Security;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SecuritiesForBranchesDAO extends BaseDAO<SecuritiesForBranches> {

    public static final String INSERT_SECURITY = "INSERT INTO SECURITIES_FOR_BRANCH (BRANCH_ID, SEC_ID, DATE, ACCESS) VALUES (?, ?, ?, ?)";
    public static final String GET_ACCESS_BY_SEC_ID = "SELECT ACCESS FROM SECURITIES_FOR_BRANCH WHERE SEC_ID = ?";

    @Override
    @Transactional
    public void insert(SecuritiesForBranches securitiesForBranches) {
        String sql = INSERT_SECURITY;
        List<Security> securities = securitiesForBranches.getSecurities();
        securities.forEach(security -> {
            security.getSecurityIds().forEach(secId -> {
                jdbcTemplate.update(sql, security.getBranchId(), secId, security.getDate(), security.getAccessType().toString());
                log.info("SECURITY_ID added");
            });
        });
    }

    @Override
    public void safelyInsert(SecuritiesForBranches securitiesForBranches) {
        //TODO
        insert(securitiesForBranches);
    }

    @Override
    public SecuritiesForBranches get(int id) {
        //TODO
        return null;
    }

    @Override
    public void update(SecuritiesForBranches securitiesForBranches) {
        //TODO
    }

    public AccessType getAccessType(int securityId) {
        String sql = GET_ACCESS_BY_SEC_ID;
        String accessType = jdbcTemplate.queryForObject(sql, String.class, securityId);
        log.info("SELECT access type for sec_id = {}", securityId);
        return AccessType.valueOf(accessType);
    }
}
