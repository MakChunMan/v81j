package com.imagsky.v81j.domain;

import javax.persistence.*;
import java.io.*;

@Embeddable
public class StringMessagePK implements Serializable {

	@Column(name = "MODULE", nullable = false, length=8)
    private String module;

    @Column(name = "LANG", nullable = false, length=2)
    private String lang;
    
    @Column(name = "STR_CODE", nullable = false, length=30)
    private String strCode;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}
    
    

}
