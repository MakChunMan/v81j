/***
 * 2015-03-19 Add Color Theme Field (Update)
 */
package com.imagsky.dao;

import java.util.List;
import javax.persistence.EntityManager;
import com.imagsky.exception.BaseDBException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.App;

public class AppDAOImpl  extends AppDAO{

    private static AppDAOImpl appDAOImpl = new AppDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.App";
    
    AppDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static AppDAO getInstance() {
        return appDAOImpl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
		 Class thisContentClass = contentClassValidation(domainClassName);
	        EntityManager em = factory.createEntityManager();

	        beanValidate(obj);
	        App app = (App) obj;

	        try {
	            em.getTransaction().begin();
	            App tmpApp = em.find(App.class, app.getSys_guid());

	            if (!CommonUtil.isNullOrEmpty(app.getAPP_NAME())) {
	            	tmpApp.setAPP_NAME(app.getAPP_NAME());
	            }
	            tmpApp.setAPP_DESC(app.getAPP_DESC());
		        tmpApp.setAPP_TYPE(app.getAPP_TYPE());    
		        tmpApp.setAPP_COLOR_THEME(app.getAPP_COLOR_THEME());
	            tmpApp.setSys_update_dt(new java.util.Date());
	            tmpApp.setSys_updator(app.getSys_updator());
	            tmpApp.setSys_clfd_guid(app.getSys_clfd_guid());
	            tmpApp.setSys_is_live(app.isSys_is_live());
	            tmpApp.setSys_is_published(app.isSys_is_published());
	            PortalLogger.debug("DAO is_published:"+ app.isSys_is_published());
	            app.setSys_is_node(app.isSys_is_node());

	            tmpApp.setModules(app.getModules());
	            
	            em.merge(tmpApp);
	            em.getTransaction().commit();
	            app = em.find(App.class, app.getSys_guid());
	        } catch (Exception e) {
	        	PortalLogger.error("CNT_update Error: " + app.getAPP_NAME(), e);
	            return null;
	        }
	        return app;
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
