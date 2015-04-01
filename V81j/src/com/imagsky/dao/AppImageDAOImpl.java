package com.imagsky.dao;

import java.util.List;

import javax.persistence.EntityManager;
import com.imagsky.exception.BaseDBException;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.AppImage;

public class AppImageDAOImpl extends AppImageDAO {

    private static AppImageDAOImpl appImageDAOImpl = new AppImageDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.AppImage";
    
    AppImageDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static AppImageDAO getInstance() {
        return appImageDAOImpl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
		 	Class thisContentClass = contentClassValidation(domainClassName);
	        EntityManager em = factory.createEntityManager();

	        beanValidate(obj);
	        AppImage image = (AppImage) obj;

	        try {
	            em.getTransaction().begin();
	            AppImage tmpAppImage = em.find(AppImage.class, image.getSys_guid());

	            tmpAppImage.setImageOwnerApp(image.getImageOwnerApp());
	            tmpAppImage.setImageUrl(image.getImageUrl());
	              
		        tmpAppImage.setSys_update_dt(new java.util.Date());
		        tmpAppImage.setSys_updator(image.getSys_updator());
		        tmpAppImage.setSys_clfd_guid(image.getSys_clfd_guid());
		        tmpAppImage.setSys_is_live(image.isSys_is_live());
		        tmpAppImage.setSys_is_published(image.isSys_is_published());
	            PortalLogger.debug("DAO is_published:"+ image.isSys_is_published());
	            tmpAppImage.setSys_is_node(image.isSys_is_node());
	            
	            em.merge(tmpAppImage);
	            em.getTransaction().commit();
	            image = em.find(AppImage.class, image.getSys_guid());
	        } catch (Exception e) {
	        	PortalLogger.error("CNT_update Error: " + image.getImageUrl(), e);
	            return null;
	        }
	        return image;
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
