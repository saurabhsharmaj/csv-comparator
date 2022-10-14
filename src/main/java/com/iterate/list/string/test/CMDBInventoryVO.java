package com.iterate.list.string.test;

import com.opencsv.bean.CsvBindByName;

public class CMDBInventoryVO {

	@CsvBindByName
	String id;
	
	@CsvBindByName
	String hostname;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
}
