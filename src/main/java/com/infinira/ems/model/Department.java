package com.infinira.ems.model;

import java.text.MessageFormat;

public class Department {

	private int deptId;
	private String deptName;
	private String location;

	public  Department() {

	}
	public Department(String deptName) {
		validate("DepartmentName", deptName);
		this.deptName = deptName;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		validate("DepartmentName", deptName);
		this.deptName = deptName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getLocation() {
		return location;
	}

	public int getDeptId() {
		return deptId;
	}

	private static void validate(String paramName , String paramValue) {
		if(paramValue == null || paramValue.isBlank()){
			throw new RuntimeException(MessageFormat.format(EMS_XXX, paramName));
		}
	}

	private static final String EMS_XXX = "Invalid field for value : {0}";
}
