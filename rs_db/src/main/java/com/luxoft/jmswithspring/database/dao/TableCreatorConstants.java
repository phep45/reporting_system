package com.luxoft.jmswithspring.database.dao;

public class TableCreatorConstants {

    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS USER;";
    public static final String CREATE_TABLE_USER = "CREATE TABLE USER (ID INT NOT NULL, NAME VARCHAR(20), BIRTH_DATE VARCHAR(10), UNIQUE KEY (ID));";

    public static final String DROP_TABLE_TRANSACTION = "DROP TABLE IF EXISTS TRANSACTION;";
    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE TRANSACTION (ID INT NOT NULL, TYPE VARCHAR(6), CODE VARCHAR(2), BRANCH_ID INT, USER_ID INT, UNIQUE KEY (ID), FOREIGN KEY (USER_ID) REFERENCES USER(ID) );";

    public static final String DROP_TABLE_BRANCH = "DROP TABLE IF EXISTS BRANCH;";
    public static final String CREATE_TABLE_BRANCH = "CREATE TABLE BRANCH (ID INT NOT NULL, ADDRESS VARCHAR(100), UNIQUE KEY(ID));";

    public static final String DROP_TABLE_SEC_FOR_BRANCH = "DROP TABLE IF EXISTS SECURITIES_FOR_BRANCH;";
    public static final String CREATE_TABLE_SEC_FOR_BRANCH = "CREATE TABLE SECURITIES_FOR_BRANCH (BRANCH_ID INT NOT NULL, SEC_ID INT NOT NULL, DATE VARCHAR(10), ACCESS VARCHAR(6));";

    public static final String DROP_TABLE_LOT = "DROP TABLE IF EXISTS LOT;";
    public static final String CREATE_TABLE_LOT = "CREATE TABLE LOT (ID INT NOT NULL, TRAN_ID INT, SEC_ID INT, DATE VARCHAR(10), PRICE DOUBLE, AMOUNT INT, UNIQUE KEY(ID), FOREIGN KEY(TRAN_ID) REFERENCES TRANSACTION(ID));";

    public static final String DROP_TABLE_SECURITY = "DROP TABLE IF EXISTS SECURITY;";
    public static final String CREATE_TABLE_SECURITY = "CREATE TABLE SECURITY (ID INT NOT NULL, DESC VARCHAR(100));";

    private TableCreatorConstants() {}

}
