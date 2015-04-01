package com.imagsky.dao;

public abstract class FormDAO extends AbstractV7DAO {

	    public static FormDAO getInstance() {
	        return FormDAOImpl.getInstance();
	    }
}
