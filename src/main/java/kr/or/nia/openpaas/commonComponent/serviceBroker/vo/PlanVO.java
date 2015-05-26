package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

public class PlanVO {

	private String id;
	private String name;
	private String description;
	private PlanMetadataVO metadata;
	private boolean free;
	
	public PlanVO() {
		metadata = new PlanMetadataVO();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public PlanMetadataVO getMetadata() {
		return metadata;
	}
	public void setMetadata(PlanMetadataVO metadata) {
		this.metadata = metadata;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
}
