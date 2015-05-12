package com.imagsky.dao;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import com.imagsky.exception.BaseDBException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.AppImage;
import com.imagsky.v81j.domain.FormField;
import com.imagsky.v81j.domain.ModForm;

public class FormDAOImpl extends FormDAO {
	public static final String CLASSNAME = "FormDAOImpl";

    private static FormDAOImpl impl = new FormDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.ModForm";
    
    FormDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static FormDAO getInstance() {
        return impl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
		final String METHODNAME = "CNT_update";
		
		Class thisContentClass = contentClassValidation(domainClassName);
        EntityManager em = factory.createEntityManager();

        beanValidate(obj);
        ModForm module = (ModForm) obj;
        FormFieldDAO subdao = FormFieldDAO.getInstance();
        AppImageDAO adao = AppImageDAO.getInstance();
        
        try {
            em.getTransaction().begin();
            ModForm tmpModule = em.find(ModForm.class, module.getSys_guid());
            if (!CommonUtil.isNullOrEmpty(module.getForm_name())) {
            	tmpModule.setForm_name(module.getForm_name());
            }
            tmpModule.setForm_success_msg(module.getForm_success_msg());
            tmpModule.setForm_fields(module.getForm_fields());
            HashSet<FormField> aSet = new HashSet<FormField>();
            for(FormField thisFormField: module.getForm_fields()){
            	thisFormField.setForm(tmpModule);
	            if(thisFormField.getSys_guid()==null){
	            	//Create
	            	//cmaLogger.debug("Create form field");
	            	aSet.add((FormField)subdao.CNT_create(thisFormField));
	            } else { 
	            	//Update
	            	//cmaLogger.debug("Update form field");
	            	aSet.add((FormField)subdao.CNT_update(thisFormField));
	            }
            }
            
            if(tmpModule.getModBackground()==null && module.getModBackground()!=null){
            	//Create
            	tmpModule.setModBackground((AppImage)adao.CNT_create(module.getModBackground()));
            } else if(tmpModule.getModBackground()!=null && module.getModBackground()!=null) { 
            	//Update
            	tmpModule.getModBackground().setImageUrl(module.getModBackground().getImageUrl());
            } else if(tmpModule.getModBackground()!=null && module.getModBackground()==null) { 
            	//Remove
            	adao.CNT_delete(tmpModule.getModBackground());
            	tmpModule.setModBackground(null);
            }
            
            if(tmpModule.getModIcon()==null && module.getModIcon()!=null){
            	//Create
            	tmpModule.setModIcon((AppImage)adao.CNT_create(module.getModIcon()));
            } else if(tmpModule.getModIcon()!=null && module.getModIcon()!=null) { 
            	//Update
            	tmpModule.getModIcon().setImageUrl(module.getModIcon().getImageUrl());
            } else if(tmpModule.getModIcon()!=null && module.getModIcon()==null) {
            	//Remove
            	adao.CNT_delete(tmpModule.getModIcon());
            	tmpModule.setModIcon(null);
            }
            
            
            tmpModule.setSys_update_dt(new java.util.Date());
            tmpModule.setSys_updator(module.getSys_updator());
            tmpModule.setSys_clfd_guid(module.getSys_clfd_guid());
            tmpModule.setSys_is_live(module.isSys_is_live());
            tmpModule.setSys_is_published(module.isSys_is_published());
            tmpModule.setSys_is_node(module.isSys_is_node());
            
            em.merge(tmpModule);
            em.getTransaction().commit();
            module = em.find(ModForm.class, module.getForm_name());
        } catch (Exception e) {
        	PortalLogger.error(CLASSNAME, METHODNAME, "CNT_update Error: " + module.getForm_name(), e);
            return null;
        }
        return module;
	}

	@Override
	public List<Object> findAll() throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findBySample(Object obj) throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findAllByPage(String orderByField, int startRow, int chunksize) throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object obj) throws BaseDBException {
		// TODO Auto-generated method stub
		return false;
	}
}
