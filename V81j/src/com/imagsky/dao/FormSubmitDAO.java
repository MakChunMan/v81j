package com.imagsky.dao;

import java.util.List;

import com.imagsky.exception.BaseDBException;

public abstract class FormSubmitDAO extends AbstractV7DAO {

    public static FormSubmitDAO getInstance() {
        return FormSubmitDAOImpl.getInstance();
    }

}
