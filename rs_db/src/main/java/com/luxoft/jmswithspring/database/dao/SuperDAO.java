package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBBranchMapper;
import com.luxoft.jmswithspring.database.mapper.DBLotMapper;
import com.luxoft.jmswithspring.database.mapper.DBTransactionMapper;
import com.luxoft.jmswithspring.database.mapper.DBUserMapper;
import com.luxoft.jmswithspring.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuperDAO {

    //TODO: split this class to UserDAO, TransactionDAO, SecurityDAO, LotDAO, SecForBranchDAO, BranchDAO
    //TODO: create BaseDAO<T>, and maybe one "SuperDAO" to handle everything

    private static final Logger log = LoggerFactory.getLogger(SuperDAO.class);

    private static final String INSERT_USER = "INSERT INTO USER (ID, NAME, BIRTH_DATE) VALUES (?, ?, ?)";
    private static final String INSERT_TRANSACTION = "INSERT INTO TRANSACTION (ID, TYPE, CODE, BRANCH_ID, USER_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_SECURITY = "INSERT INTO SECURITY (ID, DESC) VALUES (?, ?)";
    private static final String INSERT_SEC_FOR_BRANCH = "INSERT INTO SECURITIES_FOR_BRANCH";
    private static final String INSERT_LOT = "INSERT INTO LOT (ID, TRAN_ID, SEC_ID, DATE, PRICE, AMOUNT) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_BRANCH = "INSERT INTO BRANCH(ID, ADDRESS) VALUES (?, ?)";
    private static final String SELECT_USER = "SELECT * FROM USER WHERE ID = ?";
    private static final String UPDATE_USER = "UPDATE USER SET NAME = ?, BIRTH_DATE = ? WHERE ID = ?";
    private static final String SELECT_TRANSACTION = "SELECT * FROM TRANSACTION WHERE ID = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE TRANSACTION SET TYPE = ?, CODE = ?, BRANCH_ID = ?, USER_ID = ? WHERE ID = ?";
    private static final String SELECT_SECURITY = "SELECT ID FROM SECURITY WHERE ID = ?";
    private static final String UPDATE_SECURITY = "UPDATE SECURITY SET DESC = ? WHERE ID = ?";
    private static final String SELECT_LOT = "SELECT * FROM LOT WHERE ID = ?";
    private static final String UPDATE_LOT = "UPDATE LOT SET TRAN_ID = ?, SEC_ID = ?, DATE = ?, PRICE = ?, AMOUNT = ? WHERE ID = ?";
    private static final String SELECT_LOTS_BY_TRAN_ID = "SELECT * FROM LOT WHERE TRAN_ID = ?";
    private static final String SELECT_BRANCH = "SELECT * FROM BRANCH WHERE ID = ?";
    private static final String UPDATE_BRANCH = "UPDATE BRANCH SET ADDRESS = ? WHERE ID = ?";
    public static final String SELECT_DESC_FROM_SECURITY = "SELECT DESC FROM SECURITY WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void safelyInsert(Transaction transaction) {
        safelyInsertUser(transaction.getUser());
        safelyInsertBranch(transaction.getBranch());
        safelyInsertTransaction(transaction);
        transaction.getLots().getListOfLots().forEach(lot -> {
            safelyInsertSecurity(lot);
            safelyInsertLot(lot, transaction.getId());
        });


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
        jdbcTemplate.update(sql, user.getUserName()+" "+user.getSurname(), user.getBirthDate(), user.getUserId());
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
        if(getTransaction(transaction.getBranchId()) == null) {
            insertTransaction(transaction);
        }
        else {
            updateTransaction(transaction);
        }
    }

    public void insertTransaction(Transaction transaction) {
        String sql = INSERT_TRANSACTION;
        jdbcTemplate.update(sql, transaction.getId(), transaction.getType().toString(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getUser().getUserId());
        log.info("TRANSACTION inserted");
    }

    public void updateTransaction(Transaction transaction) {
        String sql = UPDATE_TRANSACTION;
        jdbcTemplate.update(sql, transaction.getType().toString(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getUser(), transaction.getId());
        log.info("TRANSACTION updated");
    }

    public Transaction getTransaction(int id) {
        String sql = SELECT_TRANSACTION;
        Transaction transaction;
        try {
            transaction = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBTransactionMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("TRANSACTION selected");
        User user = getUser(transaction.getUser().getUserId());
        transaction.setUser(user);
        List<Lot> lotList = getLotByTranId(transaction.getId());
        Lots lots = new Lots();
        lots.setListOfLots(lotList);
        transaction.setLots(lots);
        Branch branch = getBranch(transaction.getBranchId());
        transaction.setBranch(branch);
        return transaction;
    }

    public void safelyInsertSecurity(Lot lot) {
        if(getSecurityId(lot.getSecurityId()) < 0) {
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
        int secId = -1;
        try {
            secId = jdbcTemplate.queryForObject(sql, Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
        return secId;
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

    public void insertSecurityForBranch(int branchId, int securityId, String date, AccessType accessType) {
        String sql = INSERT_SEC_FOR_BRANCH;
        jdbcTemplate.update(sql, branchId, securityId, date, accessType.toString());
        log.info("SEC_FOR_BRANCH inserted");
    }

    public void safelyInsertLot(Lot lot, int transactionId) {
        if(getLot(lot.getId()) == null) {
            insertLot(lot, transactionId);
        }
        else {
            updateLot(lot, transactionId);
        }
    }

    public void insertLot(Lot lot, int transactionId) {
        String sql = INSERT_LOT;
        jdbcTemplate.update(sql, lot.getId(), transactionId, lot.getSecurityId(), lot.getDate(), lot.getPrice(), lot.getAmount());
        log.info("LOT inserted");
    }

    public void updateLot(Lot lot, int transactionId) {
        String sql = UPDATE_LOT;
        jdbcTemplate.update(sql, transactionId, lot.getSecurityId(), lot.getDate(), lot.getPrice(), lot.getAmount(), lot.getId());
        log.info("LOT updated");
    }

    public Lot getLot(int id) {
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

    public void safelyInsertBranch(Branch branch) {
        if(getBranch(branch.getId()) == null) {
            insertBranch(branch);
        }
        else {
            updateBranch(branch);
        }
    }

    public void insertBranch(Branch branch) {
        String sql = INSERT_BRANCH;
        jdbcTemplate.update(sql, branch.getId(), branch.getAddress());
        log.info("BRANCH inserted");
    }

    public void updateBranch(Branch branch) {
        String sql = UPDATE_BRANCH;
        jdbcTemplate.update(sql, branch.getAddress(), branch.getId());
        log.info("BRANCH updated");
    }

    public Branch getBranch(int id) {
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
