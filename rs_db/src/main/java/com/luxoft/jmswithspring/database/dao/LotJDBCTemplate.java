package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.Lot;
import com.luxoft.jmswithspring.database.mapper.DBLotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Component
public class LotJDBCTemplate extends GenericDAO<Lot> {

    private static final Logger log = LoggerFactory.getLogger(LotJDBCTemplate.class);

    private static final String INSERT_SECURITY = "insert into Lot (id, price, amount, date, product_id, transaction_id) values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SECURITY = "select * from Lot where id = ?";
    private static final String SELECT_ALL = "select * from Lot";
    private static final String DELETE_SECURITY = "delete from Lot where id = ?";
    private static final String UPDATE_SECURITY = "update Lot set price = ?, amount = ?, date =?, product_id, where id = ?";

    public void setDataSource(DataSource dataSource) {

    }

    public void create(int lotId, BigDecimal price, int amount, String date, int securityId, int transactionId) {
        String sql = INSERT_SECURITY;
        jdbcTemplate.update(sql, lotId, price.doubleValue(), amount, date, securityId, transactionId);
        log.info("Create: {}", sql);
    }

    public void create(Lot lot, int transactionId) {
        create(lot.getId(), lot.getPrice(), lot.getAmount(), lot.getDate(), lot.getSecurityId(), transactionId);
    }

    public Lot get(int id) {
        String sql = SELECT_SECURITY;
        Lot lot;
        try {
            lot = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBLotMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("Get: {}", sql);
        return lot;
    }

    public List<Lot> list() {
        String sql = SELECT_ALL;
        List<Lot> securities = jdbcTemplate.query(sql, new DBLotMapper());
        log.info("List: {}", sql);
        return securities;
    }

    public void delete(int id) {
        String sql = DELETE_SECURITY;
        jdbcTemplate.update(sql, id);
    }

    public void update(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId) {
        String sql = UPDATE_SECURITY;
        jdbcTemplate.update(sql, lotId, price.doubleValue(), amount, date, productId, transactionId);
        log.info("Update: {}", sql);
    }

    public void update(Lot lot, int transactionId) {
        update(lot.getId(), lot.getPrice(), lot.getAmount(), lot.getDate(), lot.getSecurityId(), transactionId);
    }

}
