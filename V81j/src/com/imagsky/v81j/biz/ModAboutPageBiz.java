package com.imagsky.v81j.biz;

import java.util.List;
import java.util.Map;
import com.imagsky.dao.AppImageDAO;
import com.imagsky.dao.ModAboutPageDAO;
import com.imagsky.exception.BaseDBException;
import com.imagsky.exception.BaseException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.V81Util;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.AppImage;
import com.imagsky.v81j.domain.ModAboutPage;
import com.imagsky.v81j.domain.Module;

public class ModAboutPageBiz extends BaseModuleBiz {

	private Module returnModule;
	
	@Override
	public Module execute(ModuleBiz biz, String actionCode, Map paramMap)
			throws BaseException {

			this.thisParamMap = paramMap;
			//Assign Class Type
			assignClass(ModAboutPage.class);
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
		ModAboutPageDAO mdao = ModAboutPageDAO.getInstance();
		AppImageDAO adao = AppImageDAO.getInstance();
		
		ModAboutPage enqObj = new ModAboutPage();
		ModAboutPage oldObj;
		AppImage thisAppImage = null;
		
        if (!CommonUtil.isNullOrEmpty(this.getParamToString("MODGUID"))) {
        	enqObj.setSys_guid(this.getParamToString("MODGUID"));
        }
        
        
        try{
        	List oldModList =  mdao.CNT_findListWithSample(enqObj);
        	oldObj =(ModAboutPage)oldModList.get(0);
        	
	        enqObj.setPageTitle(this.getParamToString("edit-abt-title"));
	        enqObj.setPageAbout(this.getParamToString("edit-abt-about"));
	        enqObj.setPageAddress(this.getParamToString("edit-abt-address"));
	        enqObj.setPageDescription(this.getParamToString("edit-abt-desc"));
	        enqObj.setPageEmail(this.getParamToString("edit-abt-email"));
	        enqObj.setPageFacebookLink(this.getParamToString("edit-abt-fb"));
	
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
	        
	        PortalLogger.debug("abt_image_response: "+this.getParamToString("abt_image_response"));
	        if(!CommonUtil.isNullOrEmpty(this.getParamToString("abt_image_response"))){
	        	PortalLogger.debug("thisApp:" + this.thisApp);
	        	thisAppImage = new AppImage(this.thisApp, this.getParamToString("abt_image_response"));
	        	List alist =  adao.CNT_findListWithSample(thisAppImage);
	        	if(CommonUtil.isNullOrEmpty(alist)){
	        		PortalLogger.debug("empty List");
	        		enqObj.setPageImage(thisAppImage);
	        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
	        	} else {
	        		PortalLogger.debug("not empty List");
	        		//(AppImage)subdao.CNT_update(tmpAppImage));
	        		thisAppImage = (AppImage)alist.get(0);
	        		thisAppImage.setImageUrl(this.getParamToString("edit-abt-image"));
	        		enqObj.setPageImage(thisAppImage);
	        		V81Util.imageUploadFileMove(this.thisApp, thisAppImage.getImageUrl());
	        	}
	        } else {
	        	enqObj.setPageImage(null);
	        	if(oldObj.getPageImage()!=null)
	        		V81Util.imageFileDelete(this.thisApp, oldObj.getPageImage().getImageUrl());
	        }
	        
	        enqObj.setSys_update_dt(new java.util.Date());
	        enqObj.setSys_updator(this.getParamToString("updator"));
	        //enqObj.setSys_clfd_guid(module.getSys_clfd_guid());
	        enqObj.setSys_is_live(Boolean.TRUE);
	        enqObj.setSys_is_published(Boolean.TRUE);
	        enqObj.setSys_is_node(Boolean.FALSE);
	        enqObj = (ModAboutPage)mdao.CNT_update(enqObj);		
        } catch (BaseDBException e) {
			PortalLogger.error("ModAboutPageBiz.doUpdate()", e);
		}
        return enqObj;
	}


	private Module doFind() {
		ModAboutPageDAO mdao = ModAboutPageDAO.getInstance();
		ModAboutPage enqObj = new ModAboutPage();
		try{
			enqObj.setSys_guid((String)thisParamMap.get("guid"));
			List aList = mdao.CNT_findListWithSample(enqObj);
			if(aList==null || aList.size()==0){
				PortalLogger.debug(aList.toString());
				return null;
			}
			enqObj = (ModAboutPage) aList.get(0);
			//ModAboutPageForJson jsonObj = new ModAboutPageForJson(enqObj);
			//PortalLogger.debug(jsonObj.getJsonString());
		} catch (BaseDBException e) {
			PortalLogger.error("ModAboutPageBiz.doFind()", e);
		}
		return enqObj;
	}


	private Module doCreate(){
		ModAboutPageDAO mdao = ModAboutPageDAO.getInstance();
		ModAboutPage newMod = new ModAboutPage();
		try {
			newMod.setModDisplayOrder((Integer)thisParamMap.get("idx"));
			newMod.setPageTitle("A new "+ Module.ModuleTypes.ModAboutPage.name());
			newMod.setPageAbout("");
			newMod.setPageAddress("");
			newMod.setPageDescription("");
			newMod.setPageFacebookLink("");
			newMod.setPageImage(null);
			newMod.setPageEmail("");
			newMod.setPageFacebookLink("");
			newMod = (ModAboutPage)mdao.CNT_create(newMod);
		} catch (BaseDBException e) {
			PortalLogger.error("ModAboutPageBiz.doCreate()", e);
		}
		return newMod;
	}
	
	private Module doDelete(){
		ModAboutPageDAO mdao = ModAboutPageDAO.getInstance();
		ModAboutPage newMod = new ModAboutPage();
		try {
			newMod.setSys_guid((String)thisParamMap.get("guid"));
			mdao.CNT_delete(newMod);
		} catch (BaseDBException e) {
			PortalLogger.error("ModAboutPageBiz.doDelete()", e);
		}
		return newMod;
	}
}
