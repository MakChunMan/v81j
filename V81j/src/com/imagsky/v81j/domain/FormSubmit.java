package com.imagsky.v81j.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.imagsky.v81j.domain.SysObject;

@Entity
@Table(name = "tb8_formsubmit")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public class FormSubmit extends SysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "FORM_GUID", length = 32)
    private String FORM_GUID;
    
    @Column(name = "FORM_SENDER", length = 100)
    private String FORM_SENDER;
    
    @Column(name = "FORM_MACHINE_ID", length = 40)
    private String FORM_MACHINE_ID;
    
    @Column(name = "FORM_SUBMIT_STRING")
    @Lob
    private String FORM_SUBMIT_STRING;
     
    
    public String getFORM_GUID() {
		return FORM_GUID;
	}

	public void setFORM_GUID(String fORM_GUID) {
		FORM_GUID = fORM_GUID;
	}

	public String getFORM_SENDER() {
		return FORM_SENDER;
	}

	public void setFORM_SENDER(String fORM_SENDER) {
		FORM_SENDER = fORM_SENDER;
	}

	public String getFORM_MACHINE_ID() {
		return FORM_MACHINE_ID;
	}

	public void setFORM_MACHINE_ID(String fORM_MACHINE_ID) {
		FORM_MACHINE_ID = fORM_MACHINE_ID;
	}

	public String getFORM_SUBMIT_STRING() {
		return FORM_SUBMIT_STRING;
	}

	public void setFORM_SUBMIT_STRING(String fORM_SUBMIT_STRING) {
		FORM_SUBMIT_STRING = fORM_SUBMIT_STRING;
	}

	public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("FORM_SENDER");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if(FormSubmit.class.isInstance(thisObj)){
        	FormSubmit obj = (FormSubmit)thisObj;
            aHt.put("FORM_GUID", obj.FORM_GUID);
            aHt.put("FORM_SENDER", obj.FORM_SENDER);
            aHt.put("FORM_MACHINE_ID", obj.FORM_MACHINE_ID);
            aHt.put("FORM_SUBMIT_STRING", obj.FORM_SUBMIT_STRING);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }
}
