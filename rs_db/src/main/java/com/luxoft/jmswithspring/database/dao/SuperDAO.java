package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBUserMapper;
import com.luxoft.jmswithspring.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SuperDAO {

    //TODO: split this class to UserDAO, TransactionDAO, SecurityDAO, LotDAO, SecForBranchDAO, BranchDAO
    //TODO: create BaseDAO<T>, and maybe one "SuperDAO" to handle everything

    private static final Logger log = LoggerFactory.getLogger(SuperDAO.class);

    public static final String INSERT_USER = "INSERT INTO USER (ID, NAME, BIRTH_DATE) VALUES (?, ?, ?)";
    public static final String INSERT_TRANSACTION = "INSERT INTO TRANSACTION (ID, TYPE, CODE, BRANCH_ID, USER_ID) VALUES (?, ?, ?, ?, ?,)";
    public static final String INSERT_SECURITY = "INSERT INTO SECURITY (ID, DES) VALUES (?, ?)";
    public static final String INSERT_SEC_FOR_BRANCH = "INSERT INTO SECURITIES_FOR_BRANCH";
    public static final String INSERT_LOT = "INSERT INTO LOT (ID, TRAN_ID, SEC_ID, DATE, PRICE, AMOUNT) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_BRANCH = "INSERT INTO BRANCH(ID, ADDRESS) VALUES (?, ?)";
    public static final String SELECT_USER = "SELECT * FROM USER WHERE ID = ?";
    public static final String UPDATE_USER = "UPDATE USER SET NAME = ?, SET BIRTH_DATE = ? WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Transaction transaction) {
        insertUser(transaction.getUser());
        insertBranch(transaction.getBranch());
        transaction.getLots().getListOfLots().forEach(lot -> {
            insertSecurity(lot);
            insertLot(lot, transaction.getId());
        });
        insertTransaction(transaction);

    }

    public void safelyInsertUser(User user) {
        if(getUser(user.getUserId()) == null) {
            insertUser(user);
        }
        else {
            updateUser(user);
        }
    }

    public void insertUser(User user) {
        String sql = INSERT_USER;
        jdbcTemplate.update(sql, user.getUserId(), user.getUserName()+" "+user.getSurname(), user.getBirthDate());
        log.info("USER inserted");
    }

    public void updateUser(User user) {
        String sql = UPDATE_USER;
        jdbcTemplate.update(sql, user.getUserName()+" "+user.getSurname(), user.getBirthDate());
        log.info("USER updated");
    }

    public User getUser(int id) {
        String sql = SELECT_USER;
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBUserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("USER selected");
        return user;
    }

    public void safelyInsertTransaction(Transaction transaction) {
        //TODO
    }

    public void insertTransaction(Transaction transaction) {
        String sql = INSERT_TRANSACTION;
        jdbcTemplate.update(sql, transaction.getId(), transaction.getType().toString(), transaction.getBranchId(), transaction.getUser().getUserId());
        log.info("TRANSACTION inserted");
    }

    public void updateTransaction(Transaction transaction) {
        //TODO
    }

    public Transaction getTransaction(int id) {
        //TODO
        return null;
    }

    public void safelyInsertSecurity(Lot lot) {
        //TODO
    }

    public void updateSecurity(Lot lot) {
        //TODO
    }

    public void insertSecurity(Lot lot) {
        String sql = INSERT_SECURITY;
        jdbcTemplate.update(sql, lot.getSecurityId(), lot.getDescription());
        log.info("SECURITY inserted");
    }

    public int getSecurityId(int id) {
        //TODO
        return 0;
    }

    public void insertSecurityForBranch(int branchId, int securityId, String date, AccessType accessType) {
        String sql = INSERT_SEC_FOR_BRANCH;
        jdbcTemplate.update(sql, branchId, securityId, date, accessType.toString());
        log.info("SEC_FOR_BRANCH inserted");
    }

    public void safelyInsertLot(Lot lot) {
        //TODO
    }

    public void insertLot(Lot lot, int transactionId) {
        String sql = INSERT_LOT;
        jdbcTemplate.update(sql, lot.getId(), transactionId, lot.getSecurityId(), lot.getDate(), lot.getPrice(), lot.getAmount());
        log.info("LOT inserted");
    }

    public void updateLot(Lot lot) {
        //TODO
    }

    public Lot getLot(int id) {
        //TODO
        return null;
    }

    public void safelyInsertBranch(Branch branch) {
        //TODO
    }

    public void insertBranch(Branch branch) {
        String sql = INSERT_BRANCH;
        jdbcTemplate.update(sql, branch.getId(), branch.getAddress());
        log.info("BRANCH inserted");
    }

    public void updateBranch(Branch branch) {
        //TODO
    }

    public Branch getBranch(int id) {
        //TODO
        return null;
    }

}
