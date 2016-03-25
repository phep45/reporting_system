package com.luxoft.jmswithspring.database.transaction;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;

import javax.sql.DataSource;
import java.util.List;

public interface TransactionDAO {

    void setDataSource(DataSource dataSource);

    void create(int id, OperationType operationType, String code, int branchId, int userId);

    Transaction getTransaction(int id);

    List<Transaction> listTransactions();

    void delete(int id);

    void update(int id, OperationType operationType, String code, int branchId, int userId);

}
