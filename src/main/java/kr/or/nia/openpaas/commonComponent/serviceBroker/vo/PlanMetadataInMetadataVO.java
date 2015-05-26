package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.ArrayList;
import java.util.List;

public class PlanMetadataInMetadataVO {

	private List<String> bullets;
	private List<CostVO> costs;
	private String displayName;
	
	public PlanMetadataInMetadataVO() {
		bullets = new ArrayList<String>();
		costs = new ArrayList<CostVO>();
	}
	
	public List<String> getBullets() {
		return bullets;
	}
	public void setBullets(List<String> bullets) {
		this.bullets = bullets;
	}
	public List<CostVO> getCosts() {
		return costs;
	}
	public void setCosts(List<CostVO> costs) {
		this.costs = costs;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
