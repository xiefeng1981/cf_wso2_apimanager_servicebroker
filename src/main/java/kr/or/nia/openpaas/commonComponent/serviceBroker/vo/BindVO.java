package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.ArrayList;
import java.util.List;

public class BindVO {

	private List<CredentialVO> credentials;
	private String syslog_drain_url;
	
	public BindVO() {
		credentials = new ArrayList<CredentialVO>();		
	}
	
	public List<CredentialVO> getCredentials() {
		return credentials;
	}
	public void setCredentials(List<CredentialVO> credentials) {
		this.credentials = credentials;
	}
	public String getSyslog_drain_url() {
		return syslog_drain_url;
	}
	public void setSyslog_drain_url(String syslog_drain_url) {
		this.syslog_drain_url = syslog_drain_url;
	}

	@Override
	public String toString() {
		return "BindVO [credentials=" + credentials + ", syslog_drain_url="
				+ syslog_drain_url + "]";
	}	
}
