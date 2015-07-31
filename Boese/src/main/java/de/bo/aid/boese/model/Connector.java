package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Connector generated by hbm2java
 */
public class Connector implements java.io.Serializable {

	private int coId;
	private String name;
	private String webadress;
	private Set historyLogConnectors = new HashSet(0);
	private Set devices = new HashSet(0);
	private Set logConnectors = new HashSet(0);

	public Connector() {
	}

	public Connector(int coId, String name, String webadress) {
		this.coId = coId;
		this.name = name;
		this.webadress = webadress;
	}

	public Connector(int coId, String name, String webadress, Set historyLogConnectors, Set devices,
			Set logConnectors) {
		this.coId = coId;
		this.name = name;
		this.webadress = webadress;
		this.historyLogConnectors = historyLogConnectors;
		this.devices = devices;
		this.logConnectors = logConnectors;
	}

	public int getCoId() {
		return this.coId;
	}

	public void setCoId(int coId) {
		this.coId = coId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebadress() {
		return this.webadress;
	}

	public void setWebadress(String webadress) {
		this.webadress = webadress;
	}

	public Set getHistoryLogConnectors() {
		return this.historyLogConnectors;
	}

	public void setHistoryLogConnectors(Set historyLogConnectors) {
		this.historyLogConnectors = historyLogConnectors;
	}

	public Set getDevices() {
		return this.devices;
	}

	public void setDevices(Set devices) {
		this.devices = devices;
	}

	public Set getLogConnectors() {
		return this.logConnectors;
	}

	public void setLogConnectors(Set logConnectors) {
		this.logConnectors = logConnectors;
	}

}
