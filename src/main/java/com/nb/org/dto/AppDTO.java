package com.nb.org.dto;

import java.util.ArrayList;
import java.util.List;

import com.nb.org.domain.AppInfo;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppDTO {
	private int id;
	private String name;
	private String creator;
	private String manageDep;
	private String description;

	public AppDTO() {
		super();
	}

	public AppDTO(AppInfo app) {
		this.id=app.getId();
		this.name=app.getName();
		this.creator = app.getCreator().getName();
		this.manageDep = app.getManageDep().getName();
		this.description = app.getDescription();
	}

	public static List<AppDTO> getListFromApps(List<AppInfo> apps){
		ArrayList<AppDTO> dto=new ArrayList<AppDTO>();
		for(AppInfo app:apps){
			dto.add(new AppDTO(app));
		}
		return dto;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getManageDep() {
		return manageDep;
	}

	public void setManageDep(String manageDep) {
		this.manageDep = manageDep;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AppDTO [name=" + name + ", creator=" + creator + ", manageDep=" + manageDep + ", description="
				+ description + "]";
	}
	
}
