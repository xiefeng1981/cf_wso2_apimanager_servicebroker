package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

public class ServiceMetadataVO {

	private String name;
	private String description;
	private ServiceMetadataInMetadataVO metadata;
	
	private String provider; // OpenPaaS : to use service broker provision api.
	
	public ServiceMetadataVO() {
		metadata = new ServiceMetadataInMetadataVO();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ServiceMetadataInMetadataVO getMetadata() {
		return metadata;
	}
	public void setMetadata(ServiceMetadataInMetadataVO metadata) {
		this.metadata = metadata;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}