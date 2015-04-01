package com.imagsky.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.imagsky.exception.BaseDBException;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.FormSubmit;

public class FormSubmitDAOImpl extends FormSubmitDAO {

    private static FormSubmitDAOImpl formSubmitDAOImpl = new FormSubmitDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.FormSubmit";
    
    FormSubmitDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static FormSubmitDAO getInstance() {
        return formSubmitDAOImpl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
	 	Class thisContentClass = contentClassValidation(domainClassName);
        EntityManager em = factory.createEntityManager();

        beanValidate(obj);
        FormSubmit formField = (FormSubmit) obj;

        try {
            em.getTransaction().begin();
            FormSubmit tmpFormSubmit = em.find(FormSubmit.class, formField.getSys_guid());

            tmpFormSubmit.setFORM_GUID(formField.getFORM_GUID());
            tmpFormSubmit.setFORM_MACHINE_ID(formField.getFORM_MACHINE_ID());
            tmpFormSubmit.setFORM_SENDER(formField.getFORM_SENDER());
            tmpFormSubmit.setFORM_SUBMIT_STRING(formField.getFORM_SUBMIT_STRING());
              
            tmpFormSubmit.setSys_update_dt(new java.util.Date());
            tmpFormSubmit.setSys_updator(formField.getSys_updator());
            tmpFormSubmit.setSys_clfd_guid(formField.getSys_clfd_guid());
            tmpFormSubmit.setSys_is_live(formField.isSys_is_live());
            tmpFormSubmit.setSys_is_published(formField.isSys_is_published());
            tmpFormSubmit.setSys_is_node(formField.isSys_is_node());
            
            em.merge(tmpFormSubmit);
            em.getTransaction().commit();
            formField = em.find(FormSubmit.class, formField.getSys_guid());
        } catch (Exception e) {
            PortalLogger.error("CNT_update Error: " + formField.getFORM_GUID(), e);
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
