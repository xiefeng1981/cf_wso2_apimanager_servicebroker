package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

public class ProvisionRequestVO {
	
	private String service_id;
	private String plan_id;
	private String organization_guid;
	private String space_guid;

	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getOrganization_guid() {
		return organization_guid;
	}
	public void setOrganization_guid(String organization_guid) {
		this.organization_guid = organization_guid;
	}
	public String getSpace_guid() {
		return space_guid;
	}
	public void setSpace_guid(String space_guid) {
		this.space_guid = space_guid;
	}
	
	@Override
	public String toString() {
		return "ProvisionRequestVO [service_id=" + service_id + ", plan_id="
				+ plan_id + ", organization_guid=" + organization_guid
				+ ", space_guid=" + space_guid + "]";
	}
}
