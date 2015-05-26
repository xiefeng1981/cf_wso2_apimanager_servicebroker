package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CatalogVO {

	private List<ServiceVO> services;
	
	@JsonIgnore
	private String cookie;
	
	public CatalogVO() {
		services = new ArrayList<ServiceVO>();
	}

	public List<ServiceVO> getServices() {
		return services;
	}

	public void setServices(List<ServiceVO> services) {
		this.services = services;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
