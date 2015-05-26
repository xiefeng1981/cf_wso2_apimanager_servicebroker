package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.List;

public class PlanMetadataVO {
	
	private String name;
	private String description;
	private PlanMetadataInMetadataVO metadata;
	private String displayName;
	
	public PlanMetadataVO() {
		metadata = new PlanMetadataInMetadataVO();
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
	public PlanMetadataInMetadataVO getMetadata() {
		return metadata;
	}
	public void setMetadata(PlanMetadataInMetadataVO metadata) {
		this.metadata = metadata;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
