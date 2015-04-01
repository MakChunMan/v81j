package com.imagsky.dao;

import java.util.List;

import com.imagsky.exception.BaseDBException;

public abstract class ModAboutPageDAO extends AbstractV7DAO {

    public static ModAboutPageDAO getInstance() {
        return ModAboutPageDAOImpl.getInstance();
    }
}
