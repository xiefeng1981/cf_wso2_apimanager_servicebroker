package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.ArrayList;
import java.util.List;

public class ServiceVO {
	
	private String id;
	private String name;
	private String description;
	private boolean bindable;
	private List<String> tags;
	private ServiceMetadataVO metadata;
	private boolean plan_updateable;
	private List<PlanVO> plans;
	private List<DashboardClientVO> dashboard_client;
	
	public ServiceVO() {
		tags = new ArrayList<String>();
		plans = new ArrayList<PlanVO>();
		dashboard_client = new ArrayList<DashboardClientVO>();
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
	public boolean isBindable() {
		return bindable;
	}
	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public ServiceMetadataVO getMetadata() {
		return metadata;
	}
	public void setMetadata(ServiceMetadataVO metadata) {
		this.metadata = metadata;
	}
	public boolean isPlan_updateable() {
		return plan_updateable;
	}
	public void setPlan_updateable(boolean plan_updateable) {
		this.plan_updateable = plan_updateable;
	}
	public List<PlanVO> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanVO> plans) {
		this.plans = plans;
	}
	public List<DashboardClientVO> getDashboard_client() {
		return dashboard_client;
	}
	public void setDashboard_client(List<DashboardClientVO> dashboard_client) {
		this.dashboard_client = dashboard_client;
	}
}
