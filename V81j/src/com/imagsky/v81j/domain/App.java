package com.imagsky.v81j.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.imagsky.v81j.domain.Member;
import com.imagsky.v81j.domain.SysObject;

@Entity
@Table(name = "tb8_app")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public class App extends SysObject {

    private static final long serialVersionUID = 1L;

	@ManyToMany
	@JoinTable(name="tb8_app_user_xref",
			joinColumns = { @JoinColumn(name="APP_GUID") },
			inverseJoinColumns = { @JoinColumn(name="MEMBER_GUID") } )
	private Collection<Member> member;
	
    @Column(name = "APP_NAME", length = 100)
    private String APP_NAME;
    
    @Column(name = "APP_DESC", length = 255)
    private String APP_DESC;

    @Column(name = "APP_TYPE")
    private int APP_TYPE; // 0 : Free
    
    @Column(name = "APP_TEMPLATE", length = 2)
    private String APP_TEMPLATE; // 0 : Free
    
    @Column(name = "APP_STATUS", length = 10)
    private String APP_STATUS; // 
    
    @JoinColumn(name = "APP_CREATOR")
    private Member APP_CREATOR;
    
	@JoinColumn(name = "MOD_OWNER_APP")
	@OneToMany(targetEntity = Module.class, cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
	private Collection<Module> modules = new ArrayList<Module>();
    
	@OneToOne (cascade= {CascadeType.MERGE} , orphanRemoval=true)
	@JoinColumn(name="APP_MAINMENU_BG")
	@Expose
	private AppImage APP_MAINMENU_BG;

	@OneToOne (cascade= {CascadeType.MERGE} , orphanRemoval=true)
	@JoinColumn(name="APP_ICON")
	@Expose
	private AppImage APP_ICON;
	
	@Expose
    @Column(name = "APP_COLOR_THEME")
	private int APP_COLOR_THEME;
	
    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("APP_NAME");
        returnList.add("APP_DESC");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (App.class.isInstance(thisObj)) {
            App obj = (App) thisObj;
            aHt.put("APP_NAME", obj.APP_NAME);
            aHt.put("APP_DESC", obj.APP_DESC);
            aHt.put("APP_TYPE", obj.APP_TYPE);
            aHt.put("APP_TEMPLATE",obj.APP_TEMPLATE);
            aHt.put("APP_STATUS", obj.APP_STATUS);
            aHt.put("APP_ICON", obj.APP_ICON);
            aHt.put("APP_MAINMENU_BG", obj.APP_MAINMENU_BG);
            aHt.put("APP_COLOR_THEME", obj.APP_COLOR_THEME);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }

	public Collection<Member> getMember() {
		return member;
	}

	public void setMember(Collection<Member> member) {
		this.member = member;
	}

	public Collection<Module> getModules() {
		if(modules==null){
			modules = new ArrayList<Module>();
		}
		return modules;
	}

	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}

	public String getAPP_DESC() {
		return APP_DESC;
	}

	public void setAPP_DESC(String aPP_DESC) {
		APP_DESC = aPP_DESC;
	}

	public int getAPP_TYPE() {
		return APP_TYPE;
	}

	public void setAPP_TYPE(int aPP_TYPE) {
		APP_TYPE = aPP_TYPE;
	}

	public String getAPP_STATUS() {
		return APP_STATUS;
	}

	public void setAPP_STATUS(String aPP_STATUS) {
		APP_STATUS = aPP_STATUS;
	}

	public Member getAPP_CREATOR() {
		return APP_CREATOR;
	}

	public void setAPP_CREATOR(Member aPP_CREATOR) {
		APP_CREATOR = aPP_CREATOR;
	}
    
	public String getAPP_TEMPLATE() {
		return APP_TEMPLATE;
	}

	public void setAPP_TEMPLATE(String aPP_TEMPLATE) {
		APP_TEMPLATE = aPP_TEMPLATE;
	}

	public AppImage getAPP_MAINMENU_BG() {
		return APP_MAINMENU_BG;
	}

	public void setAPP_MAINMENU_BG(AppImage aPP_MAINMENU_BG) {
		APP_MAINMENU_BG = aPP_MAINMENU_BG;
	}

	public AppImage getAPP_ICON() {
		return APP_ICON;
	}

	public void setAPP_ICON(AppImage aPP_ICON) {
		APP_ICON = aPP_ICON;
	}

	public int getAPP_COLOR_THEME() {
		return APP_COLOR_THEME;
	}

	public void setAPP_COLOR_THEME(int aPP_COLOR_THEME) {
		APP_COLOR_THEME = aPP_COLOR_THEME;
	}
	
	

}
