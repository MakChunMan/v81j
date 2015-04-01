package com.imagsky.v81j.domain.serialized;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public abstract class BaseSerializedDomain {
	
	@Expose
	protected String sys_guid;

	protected String getJsonString(Class thisClass, Object thisObj){
	    	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	    	String jsonStr = gson.toJson(thisObj, thisClass);
	    	return jsonStr;
	}
	
	public abstract String getJsonString();
}
