package com.imagsky.dao;

import java.util.List;

import com.imagsky.exception.BaseDBException;

public abstract class AppDAO extends AbstractV7DAO {

	    public static AppDAO getInstance() {
	        return AppDAOImpl.getInstance();
	    }
}
