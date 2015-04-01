package com.imagsky.v81j.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.imagsky.v81j.domain.SysObject;

@Entity
@Table(name = "tb8_module")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public abstract class Module extends SysObject {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "MOD_TYPE", columnDefinition = "ENUM('ModAboutPage', 'ModForm', 'ModShopCatalog')")
	@Expose
	protected ModuleTypes moduleType;
	
	public static enum ModuleTypes{
		ModDefault,
		ModAboutPage,
		ModForm,
		ModShopCatalog
	};

	@OneToOne 
	@JoinColumn(name = "MOD_ICON")
	@Expose
	private AppImage modIcon;

	@OneToOne 
	@JoinColumn(name = "MOD_BG")
	@Expose
	private AppImage modBackground;

	@Column(name="MOD_DISPLAY_ORDER")
	@Expose
	private int modDisplayOrder;
	
	public String getModuleTypeName() {
		return moduleType.name();
	}
	
	public ModuleTypes getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleTypes moduleType) {
		this.moduleType = moduleType;
	}
	
	public void setModuleType(String moduleTypeName){
		this.moduleType = ModuleTypes.valueOf(moduleTypeName);
	}
	
	public abstract String getModuleTitle();


	/***
	public App getModOwnerApp() {
		return modOwnerApp;
	}

	public void setModOwnerApp(App modOwnerApp) {
		this.modOwnerApp = modOwnerApp;
	}
	***/
	
	public AppImage getModIcon() {
		return modIcon;
	}

	public void setModIcon(AppImage modIcon) {
		this.modIcon = modIcon;
	}

	public int getModDisplayOrder() {
		return modDisplayOrder;
	}

	public void setModDisplayOrder(int modDisplayOrder) {
		this.modDisplayOrder = modDisplayOrder;
	}

	public AppImage getModBackground() {
		return modBackground;
	}

	public void setModBackground(AppImage modBackground) {
		this.modBackground = modBackground;
	}
	
	
}
