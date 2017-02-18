package com.outlawsource.domain;

public class Item {
	private String itemUID;
	private String categoryUID;
	private String displayName;
	private String machineName;
	
	private Integer marketPrice;
	private Integer vendorPrice;
	private Integer factoryCostPrice;
	private Integer recycleYieldPrice;
	
	public String getItemUID() {
		return itemUID;
	}
	public void setItemUID(String itemUID) {
		this.itemUID = itemUID;
	}
	public String getCategoryUID() {
		return categoryUID;
	}
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public Integer getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getVendorPrice() {
		return vendorPrice;
	}
	public void setVendorPrice(Integer vendorPrice) {
		this.vendorPrice = vendorPrice;
	}
	public Integer getFactoryCostPrice() {
		return factoryCostPrice;
	}
	public void setFactoryCostPrice(Integer factoryCostPrice) {
		this.factoryCostPrice = factoryCostPrice;
	}
	public Integer getRecycleYieldPrice() {
		return recycleYieldPrice;
	}
	public void setRecycleYieldPrice(Integer recycleYieldPrice) {
		this.recycleYieldPrice = recycleYieldPrice;
	}
}
