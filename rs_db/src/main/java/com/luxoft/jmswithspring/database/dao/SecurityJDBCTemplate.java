package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.database.mapper.DBSecurityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Component
public class SecurityJDBCTemplate extends GenericDAO<Security> {

    private static final Logger log = LoggerFactory.getLogger(SecurityJDBCTemplate.class);

    private static final String INSERT_SECURITY = "insert into Security (id, price, amount, date, product_id, transaction_id) values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SECURITY = "select * from Security where id = ?";
    private static final String SELECT_ALL = "select * from Security";
    private static final String DELETE_SECURITY = "delete from Security where id = ?";
    private static final String UPDATE_SECURITY = "update Security set price = ?, amount = ?, date =?, product_id, where id = ?";

    public void setDataSource(DataSource dataSource) {

    }

    public void create(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId) {
        String sql = INSERT_SECURITY;
        jdbcTemplate.update(sql, lotId, price.toString(), amount, date, productId, transactionId);
        log.info("Create: {}", sql);
    }

    public void create(Security security, int transactionId) {
        create(security.getLotId(), security.getPrice(), security.getAmount(), security.getDate(), security.getProductId(), transactionId);
    }

    public Security get(int id) {
        String sql = SELECT_SECURITY;
        Security security;
        try {
            security = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBSecurityMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("Get: {}", sql);
        return security;
    }

    public List<Security> list() {
        String sql = SELECT_ALL;
        List<Security> securities = jdbcTemplate.query(sql, new DBSecurityMapper());
        log.info("List: {}", sql);
        return securities;
    }

    public void delete(int id) {
        String sql = DELETE_SECURITY;
        jdbcTemplate.update(sql, id);
    }

    public void update(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId) {
        String sql = UPDATE_SECURITY;
        jdbcTemplate.update(sql, lotId, price.toString(), amount, date, productId, transactionId);
        log.info("Update: {}", sql);
    }

    public void update(Security security, int transactionId) {
        update(security.getLotId(), security.getPrice(), security.getAmount(), security.getDate(), security.getProductId(), transactionId);
    }

}
