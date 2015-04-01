package com.imagsky.v81j.domain.serialized;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.imagsky.v81j.domain.App;
import com.imagsky.v81j.domain.Module;

public class AppForJson extends BaseSerializedDomain {

	@Expose
    private String APP_NAME;
    
	@Expose
    private String APP_DESC;

	@Expose
    private int APP_TYPE; // 0 : Free
    
	@Expose
    private String APP_STATUS; // 
	
	@Expose
    private String APP_TEMPLATE; //	
    
	@Expose
	private Collection<Module> modules = new ArrayList<Module>();
	
	public AppForJson (App thisApp){
		this.sys_guid = thisApp.getSys_guid();
		this.APP_NAME = thisApp.getAPP_NAME();
		this.APP_DESC = thisApp.getAPP_DESC();
		this.APP_STATUS = thisApp.getAPP_STATUS();
		this.APP_TEMPLATE = thisApp.getAPP_TEMPLATE();
		this.modules = thisApp.getModules();
	}
	
	@Override
	public String getJsonString() {
		GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(Module.class   , new ModuleAdapter());
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gsonExt = builder.create();
    	return gsonExt.toJson(this);
	}

}
