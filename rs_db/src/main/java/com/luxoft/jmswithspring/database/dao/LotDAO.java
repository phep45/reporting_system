package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBLotMapper;
import com.luxoft.jmswithspring.model.Lot;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LotDAO {

    private static final Logger log = LoggerFactory.getLogger(LotDAO.class);

    private static final String INSERT_LOT = "INSERT INTO LOT (ID, TRAN_ID, SEC_ID, DATE, PRICE, AMOUNT) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LOT = "SELECT * FROM LOT WHERE ID = ?";
    private static final String UPDATE_LOT = "UPDATE LOT SET TRAN_ID = ?, SEC_ID = ?, DATE = ?, PRICE = ?, AMOUNT = ? WHERE ID = ?";
    private static final String SELECT_LOTS_BY_TRAN_ID = "SELECT * FROM LOT WHERE TRAN_ID = ?";
    private static final String SELECT_DESC_FROM_SECURITY = "SELECT DESC FROM SECURITY WHERE ID = ?";
    private static final String INSERT_SECURITY = "INSERT INTO SECURITY (ID, DESC) VALUES (?, ?)";
    private static final String SELECT_SECURITY = "SELECT ID FROM SECURITY WHERE ID = ?";
    private static final String UPDATE_SECURITY = "UPDATE SECURITY SET DESC = ? WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void safelyInsert(Lot lot, int transactionId) {
        if(get(lot.getId()) == null) {
             insert(lot, transactionId);
        }
        else {
            update(lot, transactionId);
        }
    }

    public void insert(Lot lot, int transactionId) {
        String sql = INSERT_LOT;
        jdbcTemplate.update(sql, lot.getId(), transactionId, lot.getSecurityId(), lot.getDate(), lot.getPrice(), lot.getAmount());
        log.info("LOT inserted");
    }

    public void update(Lot lot, int transactionId) {
        String sql = UPDATE_LOT;
        jdbcTemplate.update(sql, transactionId, lot.getSecurityId(), lot.getDate(), lot.getPrice(), lot.getAmount(), lot.getId());
        log.info("LOT updated");
    }

    public Lot get(int id) {
        String sql = SELECT_LOT;
        Lot lot;
        try {
            lot = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBLotMapper());
            String des = getSecurityDescription(lot.getSecurityId());
            lot.setDescription(des);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("LOT selected");
        return lot;
    }

    public List<Lot> getLotByTranId(int transactionId) {
        String sql = SELECT_LOTS_BY_TRAN_ID;
        List<Lot> list;
        try {
            list = jdbcTemplate.query(sql, new Object[]{transactionId}, new DBLotMapper());
        } catch (EmptyResultDataAccessException e) {
            log.info("getLotByTranId empty result set exception");
            return new ArrayList<>();
        }
        return list;
    }

    public String getSecurityDescription(int id) {
        String sql = SELECT_DESC_FROM_SECURITY;
        String desc;
        try {
            desc = jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return desc;
    }

    public void safelyInsertSecurity(Lot lot) {
        if(getSecurityId(lot.getSecurityId()) < NumberUtils.INTEGER_ZERO) {
            insertSecurity(lot);
        }
        else {
            updateSecurity(lot);
        }
    }

    public void updateSecurity(Lot lot) {
        String sql = UPDATE_SECURITY;
        jdbcTemplate.update(sql, lot.getDescription(), lot.getSecurityId());
        log.info("SECURITY updated");
    }

    public void insertSecurity(Lot lot) {
        String sql = INSERT_SECURITY;
        jdbcTemplate.update(sql, lot.getSecurityId(), lot.getDescription());
        log.info("SECURITY inserted");
    }

    public int getSecurityId(int id) {
        String sql= SELECT_SECURITY;
        int secId = NumberUtils.INTEGER_MINUS_ONE;
        try {
            secId = jdbcTemplate.queryForObject(sql, Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            return NumberUtils.INTEGER_MINUS_ONE;
        }
        return secId;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
