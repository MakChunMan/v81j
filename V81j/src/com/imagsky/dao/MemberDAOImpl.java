/***
 * 2014-08-25 Add Package Type
 */
package com.imagsky.dao;

import com.imagsky.exception.BaseDBException;
import com.imagsky.sqlframework.DatabaseQueryException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.JPAUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.utility.UUIDUtil;
import com.imagsky.common.SystemConstants;
//import com.imagsky.v6.dao.resultProcessor.ShopProcessor;
import com.imagsky.v81j.domain.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class MemberDAOImpl extends MemberDAO{
	
	private static MemberDAOImpl memberDAOImpl = new MemberDAOImpl();
	
	public static MemberDAO getInstance() {
		PortalLogger.debug("LOGGING = MemberDAO.getInstance() ");
		return memberDAOImpl;
	}

	private static final String domainClassName = "com.imagsky.v81j.domain.Member";
	
	@Override
	protected void beanValidate(Object entityObj)
		throws BaseDBException {
			try {
				if( ! Class.forName ( domainClassName ).isInstance ( entityObj ) ){
					throw new BaseDBException("Using wrong DAO implementation: "+domainClassName + " with "+ entityObj.getClass().getName(),"");
				}
				Class.forName(domainClassName).cast(entityObj);
			} catch (ClassNotFoundException e) {
				throw new BaseDBException("ClassNotFound for "+ domainClassName , "", e);
				
			}

	}

	public Object create(Object obj) throws BaseDBException {
		PortalLogger.debug("MemberDAOImpl.create: [START]");
		EntityManager em = factory.createEntityManager();
                //this.txnBegin();
                                    em.getTransaction().begin();
		beanValidate(obj);
		Member mem = (Member)obj;
		if(mem.getSys_guid()==null)
			mem.setSys_guid(UUIDUtil.getNewUUID("member"+ new java.util.Date().toString()));
		if(CommonUtil.isNullOrEmpty(mem.getPackage_type())){
			mem.setPackage_type("1a");
		}
		em.persist(mem);
                                    em.getTransaction().commit();
		PortalLogger.debug("MemberDAOImpl.create: [END]");
		return obj;
	}

	@Override
	public List<Object> findAll() throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findAllByPage(String orderByField, int startRow,
			int chunksize) throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findListWithSample(Object obj, ArrayList orderByPair) throws BaseDBException {
		EntityManager em = factory.createEntityManager();
		StringBuffer jpql_bf = new StringBuffer("SELECT mem from Member AS mem WHERE 1=1 ");
		
		try{
			beanValidate(obj);
			Member enqObj = (Member)obj;
			
			JPAUtil jpaUtil = new JPAUtil(
					Member.getFields(enqObj),
					Member.getWildFields()
					);
			
			Query query = jpaUtil.getQuery(em, jpql_bf.toString(), "mem", JPAUtil.getOrderByString("mem",orderByPair));
			return query.getResultList();
		} catch (NoResultException nre){
			PortalLogger.debug("Result not found");
			return null;
		}
	}		

	@Override
	public List<Object> findListWithSample(Object obj)
			throws BaseDBException {
		return findListWithSample(obj, null);
	}

	public Member validURL(Member mem) throws BaseDBException{
		EntityManager em = factory.createEntityManager();
		Query query = em.createQuery("SELECT mem from Member AS mem WHERE mem.mem_shopurl = :mem_shopurl");
		try{
			beanValidate(mem);
			query.setParameter("mem_shopurl", mem.getMem_shopurl());
			return (Member)query.getSingleResult();
		} catch (NoResultException nre){
			return null;
		}
	}
	
	
	@Override
	public boolean update(Object obj) throws BaseDBException {
		beanValidate(obj);
		Member mem = (Member)obj;
		EntityManager em = factory.createEntityManager();
		//this.txnBegin();
                                    em.getTransaction().begin();
		Member tmpMem = em.find(Member.class, mem.getSys_guid());
		if(!CommonUtil.isNullOrEmpty(mem.getMem_firstname())){
			tmpMem.setMem_firstname(mem.getMem_firstname());
		}
		if(!CommonUtil.isNullOrEmpty(mem.getMem_lastname())){
			tmpMem.setMem_lastname(mem.getMem_lastname());
		}
		if(!CommonUtil.isNullOrEmpty(mem.getMem_passwd())){
			tmpMem.setMem_passwd(mem.getMem_passwd());
		}
		if(!CommonUtil.isNullOrEmpty(mem.getMem_shopname())){
			tmpMem.setMem_shopname(mem.getMem_shopname());
		}
		if(!CommonUtil.isNullOrEmpty(mem.getMem_shopbanner())){
			tmpMem.setMem_shopbanner(mem.getMem_shopbanner());
		}		
		if(!CommonUtil.isNullOrEmpty(mem.getMem_shopurl())){
			if(validURL(mem)==null){
				tmpMem.setMem_shopurl(mem.getMem_shopurl());
			}
		}
		
		if(!CommonUtil.isNullOrEmpty(mem.getMem_display_name())){
			tmpMem.setMem_display_name(mem.getMem_display_name());
		}
		if(mem.getMem_salutation()!=null){
			tmpMem.setMem_salutation(mem.getMem_salutation());
		}
		if(mem.getMem_fullname_display_type()!=null){
			tmpMem.setMem_fullname_display_type(mem.getMem_fullname_display_type());
		}
		if(mem.getMem_feedback()!=null){
			tmpMem.setMem_feedback(mem.getMem_feedback());
		}
		
		tmpMem.setMem_shop_hp_arti(mem.getMem_shop_hp_arti());
		
		if(mem.getMem_cash_balance()!=null){
			tmpMem.setMem_cash_balance(mem.getMem_cash_balance());
		}
		//2014-08-25
		if(mem.getPackage_type()!=null){
			tmpMem.setPackage_type(mem.getPackage_type());
		}
		
		if(mem.getApps()!=null){
			PortalLogger.debug("Level:"+ mem.getApps().size()+ "/"+ mem.getApps().toString());
			tmpMem.setApps(mem.getApps());
		}
		
		tmpMem.setMem_lastlogindate(mem.getMem_lastlogindate());
		tmpMem.setSystemField(mem);
        em.merge(tmpMem);
		em.getTransaction().commit();
		return true;
	}
	
	/***
	@Override
	public Member[] findNewShopWithProduct() throws BaseDBException{
		StringBuffer sql = new StringBuffer("SELECT m.SYS_GUID as MEM_SYSGUID, m.MEM_SHOPNAME,m.MEM_SHOPURL ,m_sys.SYS_CREATE_DT as REG_DATE,(SELECT PROD_IMAGE1 FROM tb_item i where PROD_IMAGE1 is not null and m.SYS_GUID = i.PROD_OWNER limit 0, 1) as PROD_IMAGE1, ");
		sql.append(" count(*) as PROD_COUNT FROM `tb_item`,`tb_sys_object` ,`tb_sys_object` m_sys , tb_member  m where tb_item.SYS_GUID = tb_sys_object.SYS_GUID and m_sys.SYS_GUID = m.SYS_GUID and");
		sql.append(" m_sys.SYS_CREATE_DT is not null and m.SYS_GUID = tb_item.PROD_OWNER and  tb_sys_object.SYS_LIVE_DT is not null and");
		sql.append(" tb_sys_object.SYS_LIVE_DT < ? and tb_sys_object.SYS_IS_LIVE and  tb_sys_object.SYS_IS_PUBLISHED  group by m.SYS_GUID,m.MEM_SHOPNAME,m.MEM_SHOPURL,m_sys.SYS_CREATE_DT having count(*) > 0 ");
		sql.append(" order by m_sys.SYS_CREATE_DT limit 0, 10");

		try{
			ShopProcessor proc = ShopProcessor.getInstance();
			Member[] countObj = (Member[]) APPDB_PROCESSOR(
					SystemConstants.DB_DS_PROPERTIES_NAME,
					SystemConstants.DB_DS_DATABASE_NAME
					).executeQuery(sql.toString(), new Object[]{
						new java.util.Date()
					},proc);
			return countObj;
		} catch (DatabaseQueryException dqe){
			throwException("MemberDAOImpl : findNewShopWithProduct Failed" + dqe.getMessage(),
					sql.toString() ,dqe);
		} catch (Exception be){
			throwException("MemberDAOImpl : findNewShopWithProduct: Failed" + be.getMessage(),
					sql.toString() ,be);
		}
		return null;	
	} ***/

	@Override
	public Object findBySample(Object obj) throws BaseDBException {
		// TODO Auto-generated method stub
		return null;
	}

}
