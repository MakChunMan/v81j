package com.imagsky.v81j.biz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.imagsky.dao.AppImageDAO;
import com.imagsky.dao.FormDAO;
import com.imagsky.dao.FormFieldDAO;
import com.imagsky.exception.BaseDBException;
import com.imagsky.exception.BaseException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.V81Util;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.AppImage;
import com.imagsky.v81j.domain.FormField;
import com.imagsky.v81j.domain.ModForm;
import com.imagsky.v81j.domain.Module;

public class ModFormBiz extends BaseModuleBiz {

	private ModuleBiz callerBiz;
	private Module returnModule;
	
	@Override
	public Module execute(ModuleBiz biz, String actionCode, Map paramMap)
			throws BaseException {

			//
			this.callerBiz = biz;
			this.thisParamMap = paramMap;
			//Assign Class Type
			assignClass(ModForm.class);
			//Dispatch Action
			if(ModuleBiz.ACTION_CODE.CREATE.name().equalsIgnoreCase(actionCode)){
				returnModule = doCreate();
			} else if(ModuleBiz.ACTION_CODE.UPDATE.name().equalsIgnoreCase(actionCode)){
				returnModule = doUpdate();
			} else if(ModuleBiz.ACTION_CODE.DELETE.name().equalsIgnoreCase(actionCode)){
				returnModule = doDelete();
			} else  if(ModuleBiz.ACTION_CODE.FIND.name().equalsIgnoreCase(actionCode)){
				returnModule = doFind();
			}
			return returnModule;
	}


	private Module doUpdate() {
		ModForm enqObj = new ModForm();
		ModForm oldObj;

		FormDAO fdao = FormDAO.getInstance();
		FormFieldDAO subdao = FormFieldDAO.getInstance();
		AppImageDAO adao = AppImageDAO.getInstance();
		
		AppImage thisAppImage = null;
		
		try{
    	List oldModList =  fdao.CNT_findListWithSample(enqObj);
    	oldObj =(ModForm)oldModList.get(0);
        	
		//New input fields
		List<FormField> fields = new ArrayList<FormField>();
		FormField tmpField;
		if(!CommonUtil.isNullOrEmpty(this.getParamToString("newaddrow"))){
			String[] tokens = CommonUtil.stringTokenize(this.getParamToString("newaddrow"), ",");
			if(!(tokens==null || tokens.length==0)){
				for(String thisToken : tokens){
					if(!CommonUtil.isNullOrEmpty(thisToken)){
						tmpField = new FormField();
						tmpField.setFormfield_label(this.getParamToString("newfieldname"+thisToken));
						tmpField.setFormfield_type_code(Integer.parseInt(this.getParamToString("newfieldtype"+thisToken)));
						tmpField.setFormfield_displayorder(Integer.parseInt(this.getParamToString("newfieldorder"+thisToken)));
						fields.add(tmpField);
					}
				}
			}
		}
		//Old field
		List tmpList;
		Integer oldCount = Integer.parseInt(this.getParamToString("oldfieldcount"));
		for (int x = 1; x <= oldCount ; x++){
			//Old field still exist in the form
			if(!CommonUtil.isNullOrEmpty(this.getParamToString("field"+x))){
				tmpList = null;
				tmpField = new FormField();
				tmpField.setSys_guid(this.getParamToString("field"+x)); //Sys_guid
				try{
				tmpList = subdao.CNT_findListWithSample(tmpField);
				} catch (Exception e){
					PortalLogger.error("Get old field from DB : sys_guid - "+ this.getParamToString("field"+x), e);
				}
				if(CommonUtil.isNullOrEmpty(tmpList)){
					PortalLogger.error("Cannot find old field from DB : sys_guid - "+ this.getParamToString("field"+x));
				} else {
					tmpField = (FormField)tmpList.get(0);
					tmpField.setFormfield_label(this.getParamToString("fieldname"+x));
					tmpField.setFormfield_displayorder(Integer.parseInt(this.getParamToString("fieldorder"+x)));
					fields.add(tmpField);
				}
			}
		}
		
		
        //Common field
        if(!CommonUtil.isNullOrEmpty(this.getParamToString("mod_bg_response"))){
        	thisAppImage = new AppImage(this.thisApp, this.getParamToString("mod_bg_response"));
        	List alist =  adao.CNT_findListWithSample(thisAppImage);
        	if(CommonUtil.isNullOrEmpty(alist)){
        		enqObj.setModBackground(thisAppImage);
        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
        	} else {
        		thisAppImage = (AppImage)alist.get(0);
        		thisAppImage.setImageUrl(this.getParamToString("mod_bg_response"));
        		enqObj.setModBackground(thisAppImage);
        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
        	}
        } else {
        	enqObj.setModBackground(null);
        	if(oldObj.getModBackground()!=null)
        		V81Util.imageFileDelete(this.thisApp, oldObj.getModBackground().getImageUrl());
        }
        
        PortalLogger.debug("mod_icon_response: "+this.getParamToString("mod_icon_response"));
        if(!CommonUtil.isNullOrEmpty(this.getParamToString("mod_icon_response"))){
        	thisAppImage = new AppImage(this.thisApp, this.getParamToString("mod_icon_response"));
        	List alist =  adao.CNT_findListWithSample(thisAppImage);
        	if(CommonUtil.isNullOrEmpty(alist)){
        		PortalLogger.debug("mod_icon_response: Not found");
        		enqObj.setModIcon(thisAppImage);
        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
        	} else {
        		PortalLogger.debug("mod_icon_response: Found");
        		thisAppImage = (AppImage)alist.get(0);
        		thisAppImage.setImageUrl(this.getParamToString("mod_icon_response"));
        		enqObj.setModIcon(thisAppImage);
        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
        	}
        }else {
        	enqObj.setModIcon(null);
        	if(oldObj.getModIcon()!=null)
        		V81Util.imageFileDelete(this.thisApp, oldObj.getModIcon().getImageUrl());
        }
        //End of Common field
		
		
		/*** Main Form ***/
        if (!CommonUtil.isNullOrEmpty(this.getParamToString("MODGUID"))) {
        	enqObj.setSys_guid(this.getParamToString("MODGUID"));
        }
        
	        enqObj.setForm_name(this.getParamToString("edit-form-title"));
	        enqObj.setForm_fields(new HashSet<FormField>(fields));
	        
	        
	        enqObj.setSys_update_dt(new java.util.Date());
	        enqObj.setSys_updator(this.getParamToString("updator"));
	        //enqObj.setSys_clfd_guid(module.getSys_clfd_guid());
	        enqObj.setSys_is_live(Boolean.TRUE);
	        enqObj.setSys_is_published(Boolean.TRUE);
	        enqObj.setSys_is_node(Boolean.FALSE);
	        enqObj = (ModForm)fdao.CNT_update(enqObj);		
        } catch (BaseDBException e) {
        	PortalLogger.error("ModFormBiz.doUpdate()", e);
		}
        return enqObj;
	}


	private Module doFind() {
		FormDAO mdao = FormDAO.getInstance();
		ModForm enqObj = new ModForm();
		try{
			enqObj.setSys_guid((String)thisParamMap.get("guid"));
			List aList = mdao.CNT_findListWithSample(enqObj);
			if(aList==null || aList.size()==0){
				//cmaLogger.debug(aList.toString());
				return null;
			}
			enqObj = (ModForm) aList.get(0);
			//ModFormForJson jsonObj = new ModFormForJson(enqObj);
			//cmaLogger.debug(jsonObj.getJsonString());
		} catch (BaseDBException e) {
			PortalLogger.error("ModFormBiz.doFind()", e);
		}
		return enqObj;
	}


	private Module doCreate(){
		FormDAO mdao = FormDAO.getInstance();
		ModForm newMod = new ModForm();
		try {
			newMod.setModDisplayOrder((Integer)thisParamMap.get("idx"));
			newMod.setForm_name("A new form");
			newMod.setForm_app(this.thisApp);
			newMod = (ModForm)mdao.CNT_create(newMod);
		} catch (BaseDBException e) {
			PortalLogger.error("ModFormBiz.doCreate()", e);
		}
		return newMod;
	}
	
	private Module doDelete(){
		FormDAO mdao = FormDAO.getInstance();
		ModForm newMod = new ModForm();
		try {
			newMod.setSys_guid((String)thisParamMap.get("guid"));
			mdao.CNT_delete(newMod);
		} catch (BaseDBException e) {
			PortalLogger.error("ModFormBiz.doDelete()", e);
		}
		return newMod;
	}
}
