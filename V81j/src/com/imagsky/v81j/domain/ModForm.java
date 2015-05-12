package com.imagsky.v81j.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.imagsky.v81j.domain.SysObject;

@Entity
@Table(name = "tb8_form")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public class ModForm extends Module {

	private static final long serialVersionUID = 1L;

	public ModForm() {
		super.moduleType = Module.ModuleTypes.ModForm;
	}

	@Column(name = "FORM_NAME")
	@Expose
	private String form_name;

	@ManyToOne
	@JoinColumn(name = "FORM_APP")
	private App form_app;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "form", orphanRemoval = true)
	@Expose
	private Set<FormField> form_fields;

	@Column(name = "FORM_SUCCESS_MSG")
	private String form_success_msg;

	public String getForm_name() {
		return form_name;
	}

	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}

	public App getForm_app() {
		return form_app;
	}

	public void setForm_app(App form_app) {
		this.form_app = form_app;
	}

	public Set<FormField> getForm_fields() {
		return form_fields;
	}

	public void setForm_fields(Set<FormField> form_fields) {
		this.form_fields = form_fields;
	}

	public String getForm_success_msg() {
		return form_success_msg;
	}

	public void setForm_success_msg(String form_success_msg) {
		this.form_success_msg = form_success_msg;
	}

	@Override
	public String getModuleTitle() {
		return form_name;
	}

	public static List getWildFields() {
		List returnList = new ArrayList();
		returnList.add("FORM_NAME");
		return returnList;
	}

	public static TreeMap<String, Object> getFields(Object thisObj) {
		TreeMap<String, Object> aHt = new TreeMap<String, Object>();
		if (ModForm.class.isInstance(thisObj)) {
			ModForm obj = (ModForm) thisObj;
			aHt.put("FORM_NAME", obj.form_name);
			aHt.put("FORM_APP", obj.form_app);
			aHt.put("FORM_SUCCESS_MSG", obj.form_success_msg);
			aHt.putAll(SysObject.getSysFields(obj));
		}
		return aHt;
	}

}
