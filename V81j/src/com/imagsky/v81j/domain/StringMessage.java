package com.imagsky.v81j.domain;

import javax.persistence.*;

@Entity
@Table(name="tb_str")

public class StringMessage {

	/**
     * EmbeddedId primary key field
     */
    @EmbeddedId
    protected StringMessagePK stringMessagePK;
    
    @Column (name="STR_VALUE", length=1000)
    protected String str_value;

	public String getStr_value() {
		return str_value;
	}

	public void setStr_value(String strValue) {
		str_value = strValue;
	}

	public StringMessagePK getStringMessagePK() {
		return stringMessagePK;
	}

	public void setStringMessagePK(StringMessagePK stringMessagePK) {
		this.stringMessagePK = stringMessagePK;
	}
    
	public String getModule() {
		if(getStringMessagePK()==null) return null;
		return getStringMessagePK().getModule();
	}

	public void setModule(String module) {
		if(getStringMessagePK()==null) this.stringMessagePK = new StringMessagePK();
		this.stringMessagePK.setModule(module);
	}

	public String getLang() {
		if(getStringMessagePK()==null) return null;
		return getStringMessagePK().getLang();
	}

	public void setLang(String lang) {
		if(getStringMessagePK()==null) this.stringMessagePK = new StringMessagePK();
		this.stringMessagePK.setLang(lang);
	}

	public String getStrCode() {
		if(getStringMessagePK()==null) return null;
		return getStringMessagePK().getStrCode();
	}

	public void setStrCode(String strCode) {
		if(getStringMessagePK()==null) this.stringMessagePK = new StringMessagePK();
		this.stringMessagePK.setStrCode(strCode);
	}


}
