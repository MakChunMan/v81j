package com.imagsky.util;

import com.imagsky.util.logger.PortalLogger;
//import com.imagsky.util.logger.jpaLogger;
import java.lang.reflect.Field;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JPAUtil {

    private TreeMap<String, ?> fields;
    private List wildCardFields;
    private HashMap<String, Object> paramMap;
    private boolean isLiveContent = false;

    public JPAUtil(TreeMap<String, ?> f, List a) {
        this.fields = f;
        this.wildCardFields = a;
        this.paramMap = new HashMap();
    }

    /**
     * *
     * Clone Object including GUID
     *
     * @param obj
     * @return
     */
    public Object clone(Object obj, String newGuid) {
        if (this.fields == null) {
            return null;
        }
        Iterator<String> it = fields.keySet().iterator();
        String key = null;
        Class<? extends Object> clazz = obj.getClass();
        try {
            Object returnObj = clazz.newInstance();
            Field objField = null;
            while (it.hasNext()) {
                key = (String) it.next();
                try {
                    objField = clazz.getDeclaredField(key);
                } catch (NoSuchFieldException fe) {
                    objField = clazz.getSuperclass().getDeclaredField(key);
                }
                objField.setAccessible(true);
                objField.set(returnObj, this.fields.get(key));
            }
            if (!CommonUtil.isNullOrEmpty(newGuid)) {
                objField = clazz.getSuperclass().getDeclaredField("sys_guid");
                objField.setAccessible(true);
                objField.set(returnObj, newGuid);
            }
            return returnObj;
        } catch (Exception e) {
        	PortalLogger.error("JPAUtil.clone Exception:", e);
            return null;
        }
    }

    public Query getQuery(EntityManager em, String headerQuery, String alias, String orderBy) {
        Iterator it = fields.keySet().iterator();
        String key = "";
        StringBuffer sb = new StringBuffer(headerQuery);
        int i = 0;
        while (it.hasNext()) {
            key = (String) it.next();
            try {
                if (!shouldIgnore(this.fields.get(key))) {
                    if (this.wildCardFields.contains(key)) {
                        sb.append(" and UPPER(" + alias + "." + key + ") like :FLD" + i);
                        paramMap.put("FLD" + i, "%" + ((String) this.fields.get(key)).toUpperCase() + "%");
                    } else {
                        sb.append(" and " + alias + "." + key + " = :FLD" + i);
                        paramMap.put("FLD" + i, this.fields.get(key));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            i++;
        }
        if (this.isLiveContent) {
            java.util.Date today = new java.util.Date();
            sb.append(" and (" + alias + ".sys_exp_dt is null or " + alias + ".sys_exp_dt >= :FLD" + i + ")");
            paramMap.put("FLD" + i, today);
            i++;
        }
        PortalLogger.debug("[JPA]" + sb.toString());
        Query query = em.createQuery(sb.append(" " + orderBy).toString());
        Iterator<String> itParam = paramMap.keySet().iterator();
        StringBuffer sbLog = new StringBuffer();
        while (itParam.hasNext()) {
            key = (String) itParam.next();
            sbLog.append(key).append("=").append(paramMap.get(key)).append(";");
            query.setParameter(key, paramMap.get(key));
        }
        PortalLogger.debug("[JPA]"+ sbLog.toString());
        return query;
    }

    private boolean shouldIgnore(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static String getOrderByString(String alias, ArrayList list) {
        StringBuffer returnSb = new StringBuffer("");
        if (list == null) {
            return "";
        }
        Iterator it = list.iterator();
        returnSb.append(" order by ");
        String[] tmpObj;
        while (it.hasNext()) {
            tmpObj = (String[]) it.next();
            returnSb.append(" ").append(alias).append(".").append(tmpObj[0]).append(" ").append(tmpObj[1]);
            if (it.hasNext()) {
                returnSb.append(",");
            }
        }
        return returnSb.toString();
    }

    public static String getOrderByString(String stringFromJTable) {
        String[] tokens = CommonUtil.stringTokenize(stringFromJTable, ",");
        ArrayList alist = new ArrayList();
        for (int x = 0; x < tokens.length; x++) {
            alist.add(CommonUtil.stringTokenize(tokens[x], " "));
        }
        return getOrderByString("cnt", alist); //TODO: Hardcode alias here
    }

    public boolean isLiveContent() {
        return isLiveContent;
    }

    public void setLiveContent(boolean isLiveContent) {
        this.isLiveContent = isLiveContent;
    }
}
