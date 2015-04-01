package com.imagsky.v81j.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.imagsky.v81j.domain.SysObject;

@Entity
@Table(name = "tb8_mod_aboutpage")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public class ModAboutPage extends Module {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModAboutPage(){
		super.moduleType = Module.ModuleTypes.ModAboutPage;
	}
	
	@Column(name="ABT_TITLE")
	@Expose
	private String pageTitle;
	
	@Column(name="ABT_ABOUT")
	@Expose
	private String pageAbout;
	
	@Column(name="ABT_DESC")
	@Expose
	private String pageDescription;
	
	@OneToOne (cascade= {CascadeType.MERGE} , orphanRemoval=true)
	@JoinColumn(name="ABT_IMAGE")
	@Expose
	private AppImage pageImage;
	
	@Column(name="ABT_FACEBOOK")
	@Expose
	private String pageFacebookLink;
	
	@Column(name="ABT_EMAIL")
	@Expose
	private String pageEmail;
	
	@Column(name="ABT_ADDRESS")
	@Expose
	private String pageAddress;

    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("ABT_TITLE");
        returnList.add("ABT_ABOUT");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (ModAboutPage.class.isInstance(thisObj)) {
        	ModAboutPage obj = (ModAboutPage) thisObj;
            aHt.put("ABT_TITLE", obj.pageTitle);
            aHt.put("ABT_ABOUT", obj.pageAbout);
            aHt.put("ABT_DESC", obj.pageDescription);
            aHt.put("ABT_IMAGE", obj.pageImage);
            aHt.put("ABT_FACEBOOK", obj.pageFacebookLink);
            aHt.put("ABT_EMAIL", obj.pageEmail);
            aHt.put("ABT_ADDRESS", obj.pageAddress);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }
	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageAbout() {
		return pageAbout;
	}

	public void setPageAbout(String pageAbout) {
		this.pageAbout = pageAbout;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public AppImage getPageImage() {
		return pageImage;
	}

	public void setPageImage(AppImage pageImage) {
		this.pageImage = pageImage;
	}

	public String getPageFacebookLink() {
		return pageFacebookLink;
	}

	public void setPageFacebookLink(String pageFacebookLink) {
		this.pageFacebookLink = pageFacebookLink;
	}

	public String getPageEmail() {
		return pageEmail;
	}

	public void setPageEmail(String pageEmail) {
		this.pageEmail = pageEmail;
	}

	public String getPageAddress() {
		return pageAddress;
	}

	public void setPageAddress(String pageAddress) {
		this.pageAddress = pageAddress;
	}

	@Override
	public String getModuleTitle() {
		return this.pageTitle;
	}
	
	
}
