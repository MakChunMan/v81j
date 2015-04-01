package com.imagsky.exception;

import java.util.ArrayList;
import java.util.List;

public class BaseDBException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sql = null;
    private List<?> paramList = null;

    /**
     * @param errorCode
     * @param sql
     * @param paramList
     */
    public BaseDBException(String errorCode, String sql, List paramList) {
        super(errorCode);
        this.sql = sql;
        this.paramList = paramList;
    }

    /**
     * @param errorCode
     * @param sql
     */
    public BaseDBException(String errorCode, String sql) {
        super(errorCode);
        this.sql = sql;
        this.paramList = null;
    }

    /**
     * @param errorCode
     * @param message
     * @param sql
     * @param paramList
     */
    public BaseDBException(String message, String errorCode, String sql, List paramList) {
        super(message, errorCode);
        this.sql = sql;
        this.paramList = paramList;
    }

    /**
     * @param errorCode
     * @param message
     * @param sql
     */
    public BaseDBException(String message, String errorCode, String sql) {
        super(message, errorCode);
        this.sql = sql;
        this.paramList = null;
    }

    /**
     * @param message
     * @param errorCode
     * @param sql
     * @param paramList
     * @param cause
     */
    public BaseDBException(String message, String errorCode, String sql, List paramList, Throwable cause) {
        super(message, errorCode, cause);
        this.sql = sql;
        this.paramList = paramList;
    }

    /**
     * @param message
     * @param errorCode
     * @param sql
     * @param cause
     */
    public BaseDBException(String message, String errorCode, String sql, Throwable cause) {
        super(message, errorCode, cause);
        this.sql = sql;
        this.paramList = null;
    }

    /**
     * @param errorCode
     * @param sql
     * @param paramList
     * @param cause
     */
    public BaseDBException(String errorCode, String sql, List paramList, Throwable cause) {
        super(errorCode, cause);
        this.sql = sql;
        this.paramList = paramList;
    }

    /**
     * @param errorCode
     * @param sql
     * @param cause
     */
    public BaseDBException(String errorCode, String sql, Throwable cause) {
        super(errorCode, cause);
        this.sql = sql;
        this.paramList = null;
    }

    // -------------
    // Getter/Setter 
    // -------------
    /**
     * @return Returns the paramList.
     */
    public List getParamList() {
        if (this.paramList != null) {
            return paramList;
        } else {
            return new ArrayList();
        }
    }

    /**
     * @return Returns the sql.
     */
    public String getSql() {
        return sql;
    }
}
