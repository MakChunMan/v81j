package com.imagsky.v81j.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import com.imagsky.util.CommonUtil;

public class FormFieldType {

	public static enum Type {INPUT_TEXT, INPUT_RADIO};
	
	public String getInputHtmlCode(Type thisType, String name, String id, String value, String clazz){
		if(Type.INPUT_TEXT.name().equalsIgnoreCase(thisType.name())){
			return "<input type=text name='"+ CommonUtil.null2Empty(name) + "' value='"+ CommonUtil.null2Empty(value) 
					+ "' class='"+CommonUtil.null2Empty(clazz)+"' id='"+CommonUtil.null2Empty(id)+"'/>";
		} else {
			return null;
		}
	}
	
	public static List<FormFieldType.Type> getFormFieldTypeSelector(){
		return new ArrayList<Type>(EnumSet.allOf(Type.class));
	}
}
