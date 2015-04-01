package com.imagsky.dao;

import com.imagsky.exception.BaseDBException;
import com.imagsky.dao.AbstractDbDAO;

/**
 *
 * @author jasonmak
 */
public abstract class AbstractV7DAO extends AbstractDbDAO{

    public abstract Object CNT_update(Object obj) throws BaseDBException;

}
