package com.imagsky.dao;

import java.util.List;

import com.imagsky.exception.BaseDBException;

public abstract class AppImageDAO extends AbstractV7DAO {

	 public static AppImageDAO getInstance() {
	        return AppImageDAOImpl.getInstance();
	    }
}
