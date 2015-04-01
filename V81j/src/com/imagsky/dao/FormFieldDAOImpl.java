package com.imagsky.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.imagsky.exception.BaseDBException;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.FormField;

public class FormFieldDAOImpl extends FormFieldDAO {

    private static FormFieldDAOImpl formFieldDAOImpl = new FormFieldDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.FormField";
    
    FormFieldDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static FormFieldDAO getInstance() {
        return formFieldDAOImpl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
		 	Class thisContentClass = contentClassValidation(domainClassName);
	        EntityManager em = factory.createEntityManager();

	        beanValidate(obj);
	        FormField formField = (FormField) obj;

	        try {
	            em.getTransaction().begin();
	            FormField tmpFormField = em.find(FormField.class, formField.getSys_guid());

	            tmpFormField.setFormfield_displayorder(formField.getFormfield_displayorder());
	            tmpFormField.setFormfield_label(formField.getFormfield_label());
	              
	            tmpFormField.setSys_update_dt(new java.util.Date());
	            tmpFormField.setSys_updator(formField.getSys_updator());
	            tmpFormField.setSys_clfd_guid(formField.getSys_clfd_guid());
	            tmpFormField.setSys_is_live(formField.isSys_is_live());
	            tmpFormField.setSys_is_published(formField.isSys_is_published());
	            tmpFormField.setSys_is_node(formField.isSys_is_node());
	            
	            em.merge(tmpFormField);
	            em.getTransaction().commit();
	            formField = em.find(FormField.class, formField.getSys_guid());
	        } catch (Exception e) {
	            PortalLogger.error("CNT_update Error: " + formField.getFormfield_label(), e);
	            return null;
	        }
	        return formField;
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
