package com.imagsky.v81j.biz;

import com.imagsky.common.ImagskySession;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.common.SystemConstants;
import com.imagsky.v81j.domain.Member;
import com.imagsky.v81j.domain.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jasonmak
 */
public abstract class V8AbstractBiz {

    private ArrayList<String> errMsgList;
    protected Map<String,String[]> paramMap;
    private Map<String,Object> returnAttributeMap;

    private App thisWorkingApp;
    
    protected V8AbstractBiz(Member thisMember){
        owner = thisMember;
        returnAttributeMap = new HashMap<String, Object>();
    }

    protected V8AbstractBiz(Member thisMember, HttpServletRequest req){
        owner = thisMember;
        returnAttributeMap = new HashMap<String, Object>();
        getParamFromHttpRequest(req);
        thisWorkingApp = ((ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
    }
    
    protected void reset(Member thisMember, HttpServletRequest req){
    	owner = thisMember;
        returnAttributeMap = new HashMap<String, Object>();
        getParamFromHttpRequest(req);
        thisWorkingApp = ((ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
    }

    protected Member owner;

    protected String sessionid;
    protected String lang;
    

    public App getThisWorkingApp() {
		return thisWorkingApp;
	}

	public void setThisWorkingApp(App thisWorkingApp) {
		this.thisWorkingApp = thisWorkingApp;
	}

	public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public void addErrorMsg(String errMsg) {
        if (errMsgList == null) {
            errMsgList = new ArrayList<String>();
        }
        errMsgList.add(errMsg);
    }

    public String getErrMsgList() {
        StringBuffer sb = new StringBuffer();
        if(errMsgList==null)
               return "";
        for (int x =0 ; x < this.errMsgList.size(); x++){
            sb.append(this.errMsgList.get(x).toString()+"\n\r<br/>");
        }
        return sb.toString();
    }

    public Map<String,String[]> getParamFromHttpRequest(HttpServletRequest req){
        if(paramMap==null)
            paramMap = new HashMap<String,String[]>();
        this.paramMap.putAll(req.getParameterMap());
        this.sessionid = req.getSession().getId();
        this.lang = (String) req.getAttribute(SystemConstants.REQ_ATTR_LANG);
        return this.paramMap;
    }

    public String getAllParamToString(){
         StringBuffer sb = new StringBuffer();
        if(errMsgList==null)
               return "";
        Iterator<String> it = this.paramMap.keySet().iterator();
        String tmp ;
        while(it.hasNext()){
            tmp = (String)it.next();
            sb.append(tmp + ":"+ paramMap.get(tmp)[0] + "\n\r<br/>");
        }
        return sb.toString();
    }

    public String[] getParam(String key){
        if(paramMap==null) return null;
        if(CommonUtil.isNullOrEmpty(key)) return null;
        return (String[])paramMap.get(key);
    }

    public String getParamToString(String key){
        if(CommonUtil.isNullOrEmpty(key)) return null;
        if(getParam(key)==null) return null;
        if(getParam(key).length<=0) return null;
        return getParam(key)[0];
    }

    public void addParam(String key, String[] values){
        if(paramMap==null) paramMap = new HashMap<String, String[]>();
        paramMap.put(key, values);
    }
    
    public void listParam(){
    	if(paramMap==null) return;
    	else {
    		for(String tmp : paramMap.keySet()){
    			PortalLogger.debug("Param List: key:"+ tmp+";"+ paramMap.get(tmp)[0].toString() );
    		}
    	}
    }
    
    public void addReturnAttribute(String name, Object obj){
    	if (returnAttributeMap==null)
    		returnAttributeMap = new HashMap<String, Object>();
    	returnAttributeMap.put(name, obj);
    }

    public Object getReturnAttribute(String name){
    	if (returnAttributeMap==null)
    		return null;
    	return returnAttributeMap.get(name);
    }
    
    public void setAttributeToRequest( HttpServletRequest req){
    	if (returnAttributeMap==null) 
    		return;
    	for(String key: returnAttributeMap.keySet()){
    		req.setAttribute(key, returnAttributeMap.get(key));
    	}
    }
}
