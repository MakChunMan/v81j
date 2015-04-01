package com.imagsky.dao;

import java.util.List;

import com.imagsky.exception.BaseDBException;

public abstract class FormFieldDAO extends AbstractV7DAO {

	 public static FormFieldDAO getInstance() {
	        return FormFieldDAOImpl.getInstance();
	    }
}
