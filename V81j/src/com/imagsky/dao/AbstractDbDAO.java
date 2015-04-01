package com.imagsky.dao;

import com.imagsky.exception.BaseDBException;
import com.imagsky.sqlframework.GenericDatabaseException;
import com.imagsky.sqlframework.NullSQLType;
import com.imagsky.sqlframework.SQLProcessor;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.JPAUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.utility.UUIDUtil;
import com.imagsky.common.SystemConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.TreeMap;
import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;

public abstract class AbstractDbDAO {

    protected static SQLProcessor APPDB_PROCESSOR(String appCode, String dbID) {
        return SQLProcessor.getInstance(appCode, dbID);
    }
    
    protected EntityManagerFactory factory;
    protected UserTransaction transaction;

    public AbstractDbDAO() {
        factory = Persistence.createEntityManagerFactory(SystemConstants.PERSISTENCE_NAME);
        try {
            transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        } catch (Exception e) {
            logError("AbstractDbDAO UserTransaction initialize failed: ", e);
        }
    }

    public void txnBegin() throws BaseDBException {
        try {
            if (transaction == null) {
                transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            }
            transaction.begin();
        } catch (Exception e) {
            throw new BaseDBException("BEGIN TRANSACTION ERROR", "ErrorCodeConstants.ERR_SQL_ERROR_ABSTRACT_DBDAO", "", e);
        }
        //em.getTransaction().begin();

    }

    public void txnCommit() throws BaseDBException {
        try {
            if (transaction == null) {
                transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            }
            transaction.commit();
        } catch (Exception e) {
            throw new BaseDBException("COMMIT TRANSACTION ERROR", "ErrorCodeConstants.ERR_SQL_ERROR_ABSTRACT_DBDAO", "", e);
        }
    }

    public void txnRollback() throws BaseDBException {
        try {
            if (transaction == null) {
                transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            }
            transaction.rollback();
        } catch (Exception e) {
            throw new BaseDBException("ROLLBACK TRANSACTION ERROR", "ErrorCodeConstants.ERR_SQL_ERROR_ABSTRACT_DBDAO", "", e);
        }
    }

    public abstract List<Object> findAll() throws BaseDBException;

    public abstract Object findBySample(Object obj) throws BaseDBException;

    public abstract List<Object> findAllByPage(String orderByField, int startRow, int chunksize) throws BaseDBException;
    
    /**
     * V7 DAO Start here
     */
    protected String domainClassName;

    protected void setDomainClassName(String domainClassName) {
        this.domainClassName = domainClassName;
    }

    public List<Object> CNT_findListWithSample(Object enqObj, Integer startRow, Integer chunksize, String orderByField) throws BaseDBException {
        Class<?> thisContentClass = contentClassValidation(domainClassName);
        /**
         * Find Domain Name such as "Member" **
         */
        String[] tokens = CommonUtil.stringTokenize(domainClassName, ".");
        String domainName = tokens[tokens.length - 1];

        EntityManager em = factory.createEntityManager();
        StringBuffer jpql_bf = new StringBuffer("SELECT cnt from " + domainName + " AS cnt WHERE 1=1 ");

        try {
            Query query = null;
            if (enqObj != null) {
                Method method_getFields = thisContentClass.getMethod("getFields", new Class[]{Object.class});
                Method method_getWildFields = thisContentClass.getMethod("getWildFields", new Class[]{});
                JPAUtil jpaUtil = new JPAUtil(
                        (TreeMap<String, ?>) method_getFields.invoke(null, enqObj),
                        (List) method_getWildFields.invoke(null, new Object[]{}));
                query = jpaUtil.getQuery(em, jpql_bf.toString(), "cnt", (CommonUtil.isNullOrEmpty(orderByField) ? "" : " " + orderByField));
            } else {
                query = em.createQuery(jpql_bf.append((CommonUtil.isNullOrEmpty(orderByField) ? "" : " " + orderByField)).toString());
            }
            PortalLogger.debug(query.toString());
            if (chunksize > 0) {
                query.setFirstResult(startRow);
                query.setMaxResults(chunksize);
            }
            return query.getResultList();
        } catch (IllegalAccessException ex) {
            PortalLogger.error("IllegalAccessException:", ex);
        } catch (IllegalArgumentException ex) {
            PortalLogger.error("IllegalArgumentException:", ex);
        } catch (InvocationTargetException ex) {
            PortalLogger.error("InvocationTargetException:", ex);
        } catch (NoSuchMethodException ex) {
            PortalLogger.error("NoSuchMethodException:", ex);
        } catch (SecurityException ex) {
            PortalLogger.error("SecurityException:", ex);
        } catch (Exception ex) {
            PortalLogger.error("Exception:", ex);
        }
        return null;
    }

    public List<Object> CNT_findListWithSample(Object enqObj) throws BaseDBException {
        return CNT_findListWithSample(enqObj, 0, 0, null);
    }

    public List<Object> CNT_findAllByPage(Integer startRow, Integer chunksize, String orderByField) throws BaseDBException {
        return CNT_findListWithSample(null, startRow, chunksize, orderByField);
    }

    public int CNT_findTotalRecordCount() throws BaseDBException {
        return CNT_findTotalRecordCount(null);
    }

    /***
     * Return total record count with some field filtering criteria (eg. Search with keyword)  and
     * without pagination param
     * @param enqObj
     * @return
     * @throws BaseDBException
     */
    public int CNT_findTotalRecordCount(Object enqObj) throws BaseDBException {
        Class thisContentClass = contentClassValidation(domainClassName);
        /**
         * Find Domain Name such as "Member" **
         */
        String[] tokens = CommonUtil.stringTokenize(domainClassName, ".");
        String domainName = tokens[tokens.length - 1];

        EntityManager em = factory.createEntityManager();
        StringBuffer jpql_bf = new StringBuffer("SELECT cnt from " + domainName + " AS cnt WHERE 1=1 ");

        try {
            Query query = null;
            if (enqObj != null) {
                Method method_getFields = thisContentClass.getMethod("getFields", new Class[]{Object.class});
                Method method_getWildFields = thisContentClass.getMethod("getWildFields", new Class[]{});

                JPAUtil jpaUtil = new JPAUtil(
                        (TreeMap<String, ?>) method_getFields.invoke(null, enqObj),
                        (List) method_getWildFields.invoke(null, new Object[]{}));
                query = jpaUtil.getQuery(em, jpql_bf.toString(), "cnt", "");
            } else {
                query = em.createQuery(jpql_bf.toString());
            }
            PortalLogger.debug(query.toString());
            return query.getResultList().size();
        } catch (IllegalAccessException ex) {
            PortalLogger.error("IllegalAccessException:", ex);
        } catch (IllegalArgumentException ex) {
            PortalLogger.error("IllegalArgumentException:", ex);
        } catch (InvocationTargetException ex) {
            PortalLogger.error("InvocationTargetException:", ex);
        } catch (NoSuchMethodException ex) {
            PortalLogger.error("NoSuchMethodException:", ex);
        } catch (SecurityException ex) {
            PortalLogger.error("SecurityException:", ex);
        } catch (Exception ex) {
            PortalLogger.error("Exception:", ex);
        }
        return 0;
    }

    public Object CNT_create(Object obj) throws BaseDBException {
        Class thisContentClass = contentClassValidation(domainClassName);
        PortalLogger.debug("CNT_create: start");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        beanValidate(obj);
        thisContentClass.cast(obj);
        try {
            Method method_getSys_guid = obj.getClass().getMethod("getSys_guid", new Class[]{});
            Method method_setSys_guid = obj.getClass().getMethod("setSys_guid", new Class[]{String.class});

            String sysGuid = (String) method_getSys_guid.invoke(obj, new Object[]{});
            if (sysGuid == null) {
                method_setSys_guid.invoke(obj, UUIDUtil.getNewUUID(domainClassName + new java.util.Date().toString()));
            }
            em.persist(obj);
            em.getTransaction().commit();
            PortalLogger.debug("CNT_create: end");
        } catch (SecurityException e) {
        	PortalLogger.error("SecurityException", e);
        	return null;
        } catch (NoSuchMethodException e) {
            //Without get/set SysGuid
            em.persist(obj);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
        	PortalLogger.error("IllegalArgumentException", e);
        	return null;
        } catch (IllegalAccessException e) {
        	PortalLogger.error("IllegalAccessException", e);
            return null;
        } catch (InvocationTargetException e) {
        	PortalLogger.error("InvocationTargetException", e);
            return null;
        }
        PortalLogger.debug("CNT_create: return obj");
        return obj;
    }

    public void CNT_delete(Object obj) throws BaseDBException {
        Class thisContentClass = contentClassValidation(domainClassName);
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            beanValidate(obj);
            thisContentClass.cast(obj);
            em.remove(em.merge(obj));
            em.getTransaction().commit();
        } catch (Exception e) {
            PortalLogger.error("CNT_delete", e);
        }
    }

    //TODO: public abstract Object CNT_update(Object obj) throws BaseDBException;
    //public abstract Object create(Object obj) throws BaseDBException;

    public abstract boolean update(Object obj) throws BaseDBException;


    protected void logDebug(String msg) {
            PortalLogger.debug(msg);
    }

    protected void logInfo(String msg) {
            PortalLogger.info(msg);
    }

    protected void logWarn(String msg) {
    	PortalLogger.warn(msg);
    }

    protected void logError(String msg) {
    	PortalLogger.error(msg);
    }

    protected void logError(String msg, Throwable t) {
       	PortalLogger.error(msg, t);
    }

    protected Object checkPstmtValueNull(Object obj, int type) {
        if (obj != null) {
            return obj;
        } else {
            return new NullSQLType(type);
        }
    }

    protected String getStatement(String pField, String pVal) {
        if (!pVal.equals("")) {
            return "AND trim(" + pField + ") = '" + pVal + "' ";
        } else {
            return "";
        }
    } // end of getStatement

    protected String getInStatement(String pField, String[] pVal) {
        if (pVal != null && pVal.length != 0) {
            StringBuffer valList = new StringBuffer();
            for (int i = 0; i < pVal.length; i++) {
                valList.append("'" + pVal[i] + "'");
                if (i + 1 != pVal.length) {
                    valList.append(", ");
                }
            }

            return "AND trim(" + pField + ") in (" + valList.toString() + ") ";
        } else {
            return "";
        }
    } // end of getInStatement

    protected String getDateStatement(String pField, String pVal, String pFormat) {
        if (!pVal.equals("")) {
            return "AND " + pField + " = TO_DATE('" + pVal + "','" + pFormat + "')";
        } else {
            return "";
        }
    } // end of getStatement

    protected String printPreparedStatement(String sqlStr, Object[] params) {
        StringBuffer trackLog = new StringBuffer();
        int numOfQuestionMark = 0;
        int numOfParameters = params.length;

        int index = 0;
        while (true) {
            index = sqlStr.indexOf("?", index);
            if (index == -1) {
                break;
            } else if (index == sqlStr.length() - 1) {
                numOfQuestionMark++;
                break;
            } else {
                numOfQuestionMark++;
                index += 1;
            }
        }

        if (numOfQuestionMark != numOfParameters) {
            trackLog.append("The sql requires ").append(numOfQuestionMark).append(" parameters while parameter arrays size is ").append(numOfParameters).append("\n");
            return trackLog.toString();
        }

        trackLog.append("Number of question marks: ").append(numOfQuestionMark).append("\n");
        index = 0;
        for (int i = 0; i < numOfParameters; i++) {
            index = sqlStr.indexOf("?", 0);
            if (params[i] == null) {
                Object param = (Object) params[i];
                sqlStr = sqlStr.substring(0, index) + "null" + sqlStr.substring(index + 1);
            } else if (params[i] instanceof String) {
                String param = (String) params[i];
                sqlStr = sqlStr.substring(0, index) + "'" + param + "'" + sqlStr.substring(index + 1);
            } else if (params[i] instanceof Integer) {
                Integer param = (Integer) params[i];
                sqlStr = sqlStr.substring(0, index) + param + sqlStr.substring(index + 1);
            } else if (params[i] instanceof java.sql.Date) {
                java.sql.Date param = (java.sql.Date) params[i];
                sqlStr = sqlStr.substring(0, index) + "'" + param.toString() + "'" + sqlStr.substring(index + 1);
            } else if (params[i] instanceof NullSQLType) {
                sqlStr = sqlStr.substring(0, index) + "null" + sqlStr.substring(index + 1);
            } else {
                Object param = (Object) params[i];
                sqlStr = sqlStr.substring(0, index) + "'" + param.toString() + "'" + sqlStr.substring(index + 1);
            }
        }
        trackLog.append("SQL re-constructed: ").append("\n");
        trackLog.append(sqlStr.toString()).append("\n");

        return trackLog.toString();
    }

    protected void throwException(String message, String sql, Throwable t) throws BaseDBException {
        if (t instanceof GenericDatabaseException) {
            throw new BaseDBException(message, "ErrorCodeConstants.ERR_SQL_ERROR_ABSTRACT_DBDAO", sql, t);
        } else {
            throw new BaseDBException(message, "ErrorCodeConstants.ERR_SQL_ERROR_ABSTRACT_DBDAO", t);
        }
    }

    protected void beanValidate(Object entityObj)
            throws BaseDBException {
        try {
            if (!Class.forName(domainClassName).isInstance(entityObj)) {
            	PortalLogger.error("Using wrong DAO implementation: " + domainClassName + " with " + entityObj.getClass().getName());
                throw new BaseDBException("Using wrong DAO implementation: " + domainClassName + " with " + entityObj.getClass().getName(), "");
            }
            Class.forName(domainClassName).cast(entityObj);
        } catch (ClassNotFoundException e) {
        	PortalLogger.error("ClassNotFound for " + domainClassName, e);        	
            throw new BaseDBException("ClassNotFound for " + domainClassName, "", e);

        }
    }

    protected Class<?> contentClassValidation(String domainClassName) throws BaseDBException {
        try {
            if (CommonUtil.isNullOrEmpty(domainClassName)) {
                throw new BaseDBException("DomainClassName is empty", "");
            }
            Class<?> t = Class.forName(domainClassName);
            return t;
        } catch (ClassNotFoundException cnf) {
            throw new BaseDBException("ClassNotFound for " + domainClassName, "", cnf);
        }
    }
}
