package com.outlawsource.domain;

public class ItemSummary {
	public String displayName;
	public String machineName;
	public Integer currentMarketPrice;
	public Integer averagePrice;
	public Integer rawMaterialPrice;
	public Integer recycleYieldPrice;
	
	public String getDisplayName() {
		return displayName;
	}
	public String getMachineName() {
		return machineName;
	}
	public Integer getCurrentMarketPrice() {
		return currentMarketPrice;
	}
	public Integer getAveragePrice() {
		return averagePrice;
	}
	public Integer getRawMaterialPrice() {
		return rawMaterialPrice;
	}
	public Integer getRecycleYieldPrice() {
		return recycleYieldPrice;
	}
}
