package com.imagsky.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.imagsky.v6.cma.constants.SystemConstants;
import com.imagsky.v81j.domain.StringMessage;
import com.imagsky.v81j.domain.StringMessagePK;
import com.imagsky.util.logger.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
	
public class MessageUtil {

	private static final String paramSymbol = "@@";
	public static String MOD_V8 = "V8";
	
	public static String getMessage(String mod, String lang, String appCode, ArrayList<String> param){
		String str = getMessage(mod, lang, appCode);
		if(param==null){
			return str;
		} else {
			for (int x= 0 ; x< param.size(); x++){
				try{
					str = str.replaceAll(paramSymbol + (x+1) + paramSymbol, Matcher.quoteReplacement(CommonUtil.null2Empty(param.get(x))));
				} catch (Exception e){
					PortalLogger.error("replaceAll Exception: " + paramSymbol + (x+1) + paramSymbol, e);
				}
			}
			return str;
		}
	}
	
	public static String getMessage(String mod, String lang, String appCode){
		return getMessage(mod, lang, appCode, true);
	}
	
	
	public static String getMessage(String mod, String lang, String appCode, boolean returnCodeIfEmpty){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(SystemConstants.PERSISTENCE_NAME);
		EntityManager em = factory.createEntityManager();
		StringMessagePK pk = new StringMessagePK();
		pk.setLang(lang);
		pk.setModule(mod);
		pk.setStrCode(appCode);
		
		StringMessage str = em.find(StringMessage.class, pk);
		if(str==null || CommonUtil.isNullOrEmpty(str.getStr_value())){
			PortalLogger.info("[STR_MISSING] "+appCode);
			if(returnCodeIfEmpty){
				return appCode;
			} else {
				return "";
			}
		} else {
				return str.getStr_value();
		}
	}
	
	public static String getV8Message(String lang, String appCode){
		return getMessage(MOD_V8, lang, appCode);
	}
	
	public static String getV8Message(String lang, String appCode, ArrayList<String> param){
		return getMessage(MOD_V8, lang, appCode, param);
	}
	
	public static String getV8Message(String lang, String appCode, String paramStr){
		ArrayList aList = new ArrayList();
		String[] tokens = CommonUtil.stringTokenize(paramStr, "^");
		for (int x = 0; x< tokens.length; x++){
			aList.add(tokens[x]);
		}
		return getV8Message(lang, appCode, aList);
	}
	
	/***
	public static boolean updateV6Message(String lang, String appCode, String value){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(SystemConstants.PERSISTENCE_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		StringMessagePK pk = new StringMessagePK();
		pk.setLang(lang);
		pk.setModule(MOD_V6);
		pk.setStrCode(appCode);
		
		StringMessage str = em.find(StringMessage.class, pk);
		if(str==null || CommonUtil.isNullOrEmpty(str.getStr_value())){
			return false;
		} else {
			str.setStr_value(value);
			em.getTransaction().commit();
			return true;
		}
	}***/
	
	public static void main (String[] args){
		//System.out.println(getV6Message("en","testing"));
	} 
}
