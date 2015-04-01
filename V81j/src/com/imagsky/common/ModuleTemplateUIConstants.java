package com.imagsky.common;

public class ModuleTemplateUIConstants {

	public static class ModuleUI{
		
		public ModuleUI(String colortheme, String icon){
			this.colortheme = colortheme;
			this.icon = icon;
		}
		
		private String colortheme;
		private String icon;
		public String getColortheme() {
			return colortheme;
		}
		public void setColortheme(String colortheme) {
			this.colortheme = colortheme;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		
		
	}
	
	public static final ModuleUI modAboutPage = new ModuleUI("success","fa fa-info-circle"); 
	public static final ModuleUI modCatalog = new ModuleUI("danger","fa fa-gift");
	public static final ModuleUI modForm = new ModuleUI("social","hi hi-file");
	
	public static ModuleUI getUI(String modname){
		if(modname.equalsIgnoreCase("ModAboutPage")){
			return modAboutPage;
		} else if(modname.equalsIgnoreCase("MODCATALOG")){
			return modCatalog;
		} else if(modname.equalsIgnoreCase("MODFORM")){
			return modForm;
		}
		return null;
	}
	
	public static String getUIHtml_modListPage(String modname){
		ModuleUI mod = getUI(modname);
		if(mod!=null){
			return "<div class=\"widget-icon themed-background-"+ mod.getColortheme()+" pull center-block\">" +
                                                        "<i class=\""+ mod.getIcon()+" text-light-op\"></i></div>";
		} else {
			return "";
		}
	}
}
