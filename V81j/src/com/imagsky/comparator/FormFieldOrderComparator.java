package com.imagsky.comparator;

import java.util.Comparator;

import com.imagsky.v81j.domain.FormField;

public class FormFieldOrderComparator implements Comparator<FormField> {

	@Override
	public int compare(FormField o1, FormField o2) {
		return o1.getFormfield_displayorder() < o2.getFormfield_displayorder()? -1: 
			o1.getFormfield_displayorder() == o2.getFormfield_displayorder() ? 0 : 1;
	}

}
