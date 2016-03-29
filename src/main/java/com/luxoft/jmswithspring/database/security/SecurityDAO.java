package com.luxoft.jmswithspring.database.security;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public interface SecurityDAO {

    void setDataSource(DataSource dataSource);

    void create(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId);

    void create(Security security, int transactionId);

    Security getSecurity(int id);

    List<Security> listSecurity();

    void delete(int id);

    void update(int lotId, BigDecimal price, int amount, String date, int productId, int transactionId);

    void update(Security security, int transactionId);

}
