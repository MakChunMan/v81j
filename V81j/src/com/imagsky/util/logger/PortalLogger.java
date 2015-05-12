package com.imagsky.util.logger;

import com.imagsky.utility.Logger;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class PortalLogger {

    public static final int ERROR_LEVEL = 5;
    public static final int DEBUG_LEVEL = 2;
    public static final int INFO_LEVEL = 3;
    public static final int WARN_LEVEL = 4;
    public static final String LOGGER_NAME_V81_ERROR = "V81CMALOG";

    public static final int BUFFER_SIZE = 50;
    public static final String[] LEVEL_NAME = new String[]{"LV0","LV1","DEBUG","INFO","WARN","ERROR"};
    private static ArrayList<String> logBuffer = new ArrayList<String>();
    /**
     * <P> This method logs the error/debug messages in Error log file.
     *
     * @param CLASS_NAME Name of the Class
     * @param METHOD_NAME Name of the Method
     * @param FreeTextInfo FreeText to be logged
     * @param level int	The level used for logging
     */
    public static void log(String FreeTextInfo, int level) {
        StringBuffer msg = new StringBuffer();
        msg = msg.append(FreeTextInfo);
        log(LOGGER_NAME_V81_ERROR, msg.toString(), level);
        addToLogBuffer(msg.toString(), level);
    }

    private static void addToLogBuffer(String msg, int level){
        if(logBuffer.size()>=BUFFER_SIZE){
            logBuffer.remove(0);
        }
        logBuffer.add("["+ LEVEL_NAME[level] + "- "+  new java.util.Date() +"] "+ msg);
    }
    
    public static ArrayList getLogBuffer50(){
        return logBuffer;
    }
    /**
     * <P> This method logs the error/debug messages in Error log file.
     *
     * @param CLASS_NAME Name of the Class
     * @param METHOD_NAME Name of the Method
     * @param FreeTextInfo FreeText to be logged
     * @param level int	The level used for logging
     */
    public static void log(String CLASS_NAME, String METHOD_NAME, String FreeTextInfo, int level) {
        StringBuffer msg = new StringBuffer();

        msg = msg.append(CLASS_NAME + "\t");
        msg = msg.append(METHOD_NAME + "\t");
        msg = msg.append(FreeTextInfo);

        log(LOGGER_NAME_V81_ERROR, msg.toString(), level);
    }

    /**
     * <P> This method logs the error/debug messages in Error log file. <P> It
     * initiates and retrieves error logger instance internally.It internally
     * calls
     * <code>error( )/debug( )
     * </code> method of
     * <code>com.cathaypacific.utility.Logger</code> class on basis of level
     * passed in the arguments.
     *
     * @param msg The message to be logged
     * @param level The level used to log messsage
     */
    public static void log(String loggername, String msg, int level) {
        try {
            Logger logger = Logger.getLogger(loggername);
            switch (level) {
                case ERROR_LEVEL:
                    logger.error(msg);
                    break;
                case DEBUG_LEVEL:
                    logger.debug(msg);
                    break;
                case INFO_LEVEL:
                    logger.info(msg);
                    break;
                default:
                    logger.debug(msg);
                    break;
            }
        } catch (Exception e) {
            System.out.println("LOG without configuration: " + msg);
        }
    }

    public static void logError(String className, String methodName, String FreeTextInfo, Throwable th) {
    	StringBuffer msg = new StringBuffer();

        msg = msg.append(className + "\t");
        msg = msg.append(methodName + "\t");
        msg = msg.append(FreeTextInfo);
        
        Logger logger = Logger.getLogger(LOGGER_NAME_V81_ERROR);
        logger.error(msg, th);
    }

    public static void warn(String message) {
        log(message, WARN_LEVEL);
    }

    public static void warn(String message, HttpServletRequest req) {
        if (req != null) {
            log("[" + req.getSession().getId() + "] " + message, WARN_LEVEL);
        } else {
            warn(message);
        }
    }

    public static void debug(String message) {
        log(message, DEBUG_LEVEL);
    }

    public static void debug(String message, HttpServletRequest req) {
        if (req != null) {
            log("[" + req.getSession().getId() + "] " + message, DEBUG_LEVEL);
        } else {
            debug(message);
        }
    }

    public static void error(String className, String methodName, String message) {
        log(className, methodName, message, ERROR_LEVEL);
    }

    public static void error(String className, String methodName, String message, HttpServletRequest req) {
        if (req != null) {
            log(className, methodName, "[" + req.getSession().getId() + "] " + message, ERROR_LEVEL);
        } else {
            error(className, methodName, message);
        }
    }

    public static void error(String className, String methodName, String message, Throwable th) {
        logError(className, methodName, message, th);
    }

    public static void error(String className, String methodName, String message, HttpServletRequest req, Throwable th) {
        if (req != null) {
            logError(className, methodName, "[" + req.getSession().getId() + "] " + message, th);
        } else {
            logError(className, methodName, message, th);
        }
    }

    public static void info(String message) {
        log(message, INFO_LEVEL);
    }

    public static void info(String message, HttpServletRequest req) {
        if (req != null) {
            log("[" + req.getSession().getId() + "] " + message, INFO_LEVEL);
        } else {
            log(message, INFO_LEVEL);
        }
    }
}
