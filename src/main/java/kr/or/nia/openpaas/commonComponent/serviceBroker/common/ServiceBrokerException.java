package kr.or.nia.openpaas.commonComponent.serviceBroker.common;

public class ServiceBrokerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String codeMessage;
	
	public ServiceBrokerException(int status, String message) {
		super(message);
		
		this.statusCode = status;
		this.codeMessage = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
