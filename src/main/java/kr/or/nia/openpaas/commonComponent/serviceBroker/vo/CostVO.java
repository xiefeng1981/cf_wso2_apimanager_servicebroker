package kr.or.nia.openpaas.commonComponent.serviceBroker.vo;

import java.util.HashMap;

public class CostVO {

	private HashMap<String, Double> amount;
	private String unit;
	
	public CostVO() {
		amount = new HashMap<String, Double>();
	}
	
	public HashMap<String, Double> getAmount() {
		return amount;
	}
	public void setAmount(HashMap<String, Double> amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
