package com.example.csv.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.poiji.annotation.ExcelCellName;

@Entity
@Table(name = "local")
public class LocalEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ExcelCellName("Name")
	private String name;

	@ExcelCellName("IP Address")
	private String ipAddress;

	@ExcelCellName("Status")
	private String status;

	@ExcelCellName("Operational status")
	private String operationalStatus;

	@ExcelCellName("Environment")
	private String environment;

	@ExcelCellName("Created")
	private Date created;

	@ExcelCellName("Most recent discovery")
	private Date mostRecentDiscovery;

	@ExcelCellName("Application")
	private String application;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getMostRecentDiscovery() {
		return mostRecentDiscovery;
	}

	public void setMostRecentDiscovery(Date mostRecentDiscovery) {
		this.mostRecentDiscovery = mostRecentDiscovery;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

}