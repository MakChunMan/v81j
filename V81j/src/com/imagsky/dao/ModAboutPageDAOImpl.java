package com.imagsky.dao;

import java.util.List;


import javax.persistence.EntityManager;

import com.imagsky.exception.BaseDBException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.AppImage;
import com.imagsky.v81j.domain.ModAboutPage;

public class ModAboutPageDAOImpl extends ModAboutPageDAO {

    private static ModAboutPageDAOImpl modAboutPageDAOImpl = new ModAboutPageDAOImpl();
    protected static final String thisDomainClassName = "com.imagsky.v81j.domain.ModAboutPage";
    
    ModAboutPageDAOImpl() {
    	super.setDomainClassName(thisDomainClassName);
    }

    public static ModAboutPageDAOImpl getInstance() {
        return modAboutPageDAOImpl;
    }
    
	@Override
	public Object CNT_update(Object obj) throws BaseDBException {
		Class thisContentClass = contentClassValidation(domainClassName);
        EntityManager em = factory.createEntityManager();

        beanValidate(obj);
        ModAboutPage module = (ModAboutPage) obj;
        AppImageDAO subdao = AppImageDAO.getInstance();
        
        try {
            em.getTransaction().begin();
            ModAboutPage tmpModule = em.find(ModAboutPage.class, module.getSys_guid());
            if (!CommonUtil.isNullOrEmpty(module.getPageTitle())) {
            	tmpModule.setPageTitle(module.getPageTitle());
            }
            tmpModule.setPageAbout(module.getPageAbout());
            tmpModule.setPageAddress(module.getPageAddress());
            tmpModule.setPageDescription(module.getPageDescription());
            tmpModule.setPageEmail(module.getPageEmail());
            tmpModule.setPageFacebookLink(module.getPageFacebookLink());

            if(tmpModule.getPageImage()==null && module.getPageImage()!=null){
            	//Create
            	//cmaLogger.debug("Create app image");
            	tmpModule.setPageImage((AppImage)subdao.CNT_create(module.getPageImage()));
            } else if(tmpModule.getPageImage()!=null && module.getPageImage()!=null) { 
            	//Update
            	//cmaLogger.debug("Update app image");
            	tmpModule.getPageImage().setImageUrl(module.getPageImage().getImageUrl());
            } else { 
            	//Remove
            	//cmaLogger.debug("Remove app image");
            	subdao.CNT_delete(tmpModule.getPageImage());
            	tmpModule.setPageImage(null);
            }
            
            if(tmpModule.getModBackground()==null && module.getModBackground()!=null){
            	//Create
            	PortalLogger.debug("Create app bg");
            	tmpModule.setModBackground((AppImage)subdao.CNT_create(module.getModBackground()));
            } else if(tmpModule.getModBackground()!=null && module.getModBackground()!=null) { 
            	//Update
            	PortalLogger.debug("Update app bg");
            	tmpModule.getModBackground().setImageUrl(module.getModBackground().getImageUrl());
            } else { 
            	//Remove
            	PortalLogger.debug("Remove app bg");
            	subdao.CNT_delete(tmpModule.getModBackground());
            	tmpModule.setModBackground(null);
            }
            
            if(tmpModule.getModIcon()==null && module.getModIcon()!=null){
            	//Create
            	PortalLogger.debug("Create app icon");
            	tmpModule.setModIcon((AppImage)subdao.CNT_create(module.getModIcon()));
            } else if(tmpModule.getModIcon()!=null && module.getModIcon()!=null) { 
            	//Update
            	PortalLogger.debug("Update app icon");
            	tmpModule.getModIcon().setImageUrl(module.getModIcon().getImageUrl());
            } else { 
            	//Remove
            	PortalLogger.debug("Remove app icon");
            	subdao.CNT_delete(tmpModule.getModIcon());
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
            module = em.find(ModAboutPage.class, module.getPageTitle());
        } catch (Exception e) {
        	PortalLogger.error("CNT_update Error: " + module.getPageTitle(), e);
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

