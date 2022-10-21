package com.example.csv.entity;

import java.util.List;

public class ResonseVO {
	List<CMDBEntity> cmdbList;
	List<LocalEntity> localList;

	public List<CMDBEntity> getCmdbList() {
		return cmdbList;
	}

	public void setCmdbList(List<CMDBEntity> cmdbList) {
		this.cmdbList = cmdbList;
	}

	public List<LocalEntity> getLocalList() {
		return localList;
	}

	public void setLocalList(List<LocalEntity> localList) {
		this.localList = localList;
	}

}
