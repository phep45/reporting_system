package com.luxoft.jmswithspring.database.security;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class SecurityJDBCTemplate implements SecurityDAO {

    private static final Logger log = LoggerFactory.getLogger(SecurityJDBCTemplate.class);

    private static final String INSERT_SECURITY = "insert into Security (lot_id, price, amount, date, product_id) values (?, ?, ?, ?, ?)";
    private static final String SELECT_SECURITY = "select * from Security where id = ?";
    private static final String SELECT_ALL = "select * from Security";
    private static final String DELETE_SECURITY = "delete from Security where id = ?";
    private static final String UPDATE_SECURITY = "update Security set price = ?, amount = ?, date =?, product_id, where lot_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public void create(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId) {
        String sql = INSERT_SECURITY;
        jdbcTemplate.update(sql, lotId, price.floatValue(), amount, date, productId);
        log.info("Create: {}", sql);
    }

    @Override
    public void create(Security security, int transactionId) {
        create(security.getLotId(), security.getPrice(), security.getAmount(), security.getDate(), security.getProductId(), transactionId);
    }

    @Override
    public Security getSecurity(int id) {
        String sql = SELECT_SECURITY;
        Security security = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBSecurityMapper());
        log.info("Get: {}", sql);
        return security;
    }

    @Override
    public List<Security> listSecurity() {
        String sql = SELECT_ALL;
        List<Security> securities = jdbcTemplate.query(sql, new DBSecurityMapper());
        log.info("List: {}", sql);
        return securities;
    }

    @Override
    public void delete(int id) {
        String sql = DELETE_SECURITY;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId) {
        String sql = UPDATE_SECURITY;
        jdbcTemplate.update(sql, lotId, price, amount, date, productId, transactionId);
        log.info("Update: {}", sql);
    }

    @Override
    public void update(Security security, int transactionId) {
        update(security.getLotId(), security.getPrice(), security.getAmount(), security.getDate(), security.getProductId(), transactionId);
    }
}
