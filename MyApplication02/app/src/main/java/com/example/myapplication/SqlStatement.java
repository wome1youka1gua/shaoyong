package com.example.myapplication;

public class SqlStatement {
    static final String[] SQL_CREATE_ALL = {
            " CREATE SCHEMA IF NOT EXISTS ShopManager;"
            ,
            " CREATE TABLE ShopManager.Member(" +
                    " memId   VARCHAR(11)  PRIMARY KEY," +
                    " name    VARCHAR(25)  NOT NULL," +
                    " sex     VARCHAR(2)  NOT NULL DEFAULT('未知')," +
                    " age     INTEGER(3)  NOT NULL," +
                    " workplace  VARCHAR(100)," +
                    " phone    CHAR(11)   NOT NULL UNIQUE," +
                    " CONSTRAINT CSex CHECK(sex IN('男','女','未知')), " +
                    " CONSTRAINT CAge CHECK(age BETWEEN 0 AND 130) " +
                    "); "
            ,
            " CREATE TABLE ShopManager.Commodity(" +
                    "comKey     VARCHAR(11) 	PRIMARY KEY," +
                    "name       VARCHAR(25)		NOT NULL UNIQUE," +
                    "kind       VARCHAR(5)		NOT NULL," +
                    "num        INTEGER(8)		NOT NULL," +
                    "price      DECIMAL(10,2)	NOT NULL," +
                    "CONSTRAINT 	CNum  				CHECK(num >= 0)," +
                    "CONSTRAINT 	CPrice 				CHECK(price >= 0)" +
                    ");"
            ,
            " CREATE TABLE ShopManager.PerferInfo(                              " +
                    " memId VARCHAR(11) PRIMARY KEY REFERENCES Member(memId),   " +
                    " point INTEGER(8)	NOT NULL,	                            " +
                    " vipLevel 		INTEGER(1) 	NOT NULL,		                " +
                    " CONSTRAINT 	CPoint 	CHECK(point >= 0),                  " +
                    " CONSTRAINT 	CVipLevelPI CHECK(vipLevel BETWEEN 1 AND 5) " +
                    ");"
            ,
            " CREATE TABLE ShopManager.PerferPolicy(          " +
                    "vipLevel INTEGER(1) 	NOT NULL,   						" +
                    "comKey VARCHAR(12) NOT NULL REFERENCES Commodity(comKey),	" +
                    "discount DECIMAL(3,2)NOT NULL,								" +
                    "startTime 	DATETIME		NOT NULL,						" +
                    "endTime 		DATETIME	NOT NULL,					    " +
                    "PRIMARY KEY (vipLevel, comKey, startTime),                 " +
                    "CONSTRAINT 	CDiscount 	CHECK(discount BETWEEN 0 AND 1)," +
                    "CONSTRAINT 	CVipLevelPP CHECK(vipLevel BETWEEN 1 AND 5)," +
                    "CONSTRAINT 	CTime 		CHECK(startTime < endTime)      " +
                    ");"
            ,
            "CREATE TABLE ShopManager.PurchaseRecord(                    " +
                    " memId				VARCHAR(11) 	NOT NULL REFERENCES Member(memId),     " +
                    " comKey 			VARCHAR(12) 	NOT NULL REFERENCES Commodity(comKey), " +
                    " time			 	DATETIME			NOT NULL,					       " +
                    " num 				INTEGER(8)		NOT NULL,						       " +
                    " PRIMARY KEY (memId, comKey, time),                                       " +
                    " CONSTRAINT 	CNumPR  			CHECK(num >= 0)                        " +
                    ");"
    };

    static final String[] SQL_DROP_ALL = {
            "DROP TABLE ShopManager.Member",
            "DROP TABLE ShopManager.Commodity",
            "DROP TABLE ShopManager.PerferInfo",
            "DROP TABLE ShopManager.PerferPolicy",
            "DROP TABLE ShopManager.PurchaseRecord",
    };
}
