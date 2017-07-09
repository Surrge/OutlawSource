package net.outlawsource.business.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Mine {
	private String displayName;
	private Item resource;
	
	private Integer baseCost;
	private Double costFactor;
	private Integer	maxProduction;
	private Integer requiredLevel;
	
	private Integer userQuantity;
	private Integer userHourlyYield;
	
	// calculated
	private BigInteger hourlyProfit;
	private Double averageQuality;
	private BigInteger buildCost;
	private BigInteger buildPayoff;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Item getResource() {
		return resource;
	}
	public void setResource(Item resource) {
		this.resource = resource;
	}
	public Integer getBaseCost() {
		return baseCost;
	}
	public void setBaseCost(Integer baseCost) {
		this.baseCost = baseCost;
	}
	public Double getCostFactor() {
		return costFactor;
	}
	public void setCostFactor(Double costFactor) {
		this.costFactor = costFactor;
	}
	public Integer getMaxProduction() {
		return maxProduction;
	}
	public void setMaxProduction(Integer maxProduction) {
		this.maxProduction = maxProduction;
	}
	public Integer getRequiredLevel() {
		return requiredLevel;
	}
	public void setRequiredLevel(Integer requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
	public Integer getUserQuantity() {
		return userQuantity;
	}
	public void setUserQuantity(Integer userQuantity) {
		this.userQuantity = userQuantity;
	}
	public Integer getUserHourlyYield() {
		return userHourlyYield;
	}
	public void setUserHourlyYield(Integer userHourlyYield) {
		this.userHourlyYield = userHourlyYield;
	}
	
	public void Calculate(int totalMines) {
		calculateHourlyProfit();
		calculateAverageQuality();
		calculateBuildCost(totalMines);
		calculateBuildPayoff();
	}
	
	public BigInteger getHourlyProfit() {
		return hourlyProfit;
	}
	private void calculateHourlyProfit() {
		this.hourlyProfit = BigInteger.valueOf(getUserHourlyYield())
				.multiply(BigInteger.valueOf(getResource().getMarketPrice()));
	}
	
	public Double getAverageQuality() {
		return averageQuality;
	}
	private void calculateAverageQuality() {
		this.averageQuality = (getUserQuantity() > 0)
				? (double) (getUserHourlyYield() / getUserQuantity()) * getMaxProduction()
				: 0;
	}
	
	public BigInteger getBuildCost() {
		return buildCost;
	}
	private void calculateBuildCost(int totalMines) {
		final int totalMineFactor = 20000;
		
		BigDecimal mineFactor = BigDecimal.valueOf(totalMines).multiply(BigDecimal.valueOf(totalMineFactor));
		BigDecimal totalFactor = mineFactor.multiply(BigDecimal.valueOf(getCostFactor()));
		BigDecimal cost = totalFactor.add(BigDecimal.valueOf(getBaseCost()));
		this.buildCost = cost.toBigInteger();
	}
	
	public BigInteger getBuildPayoff() {
		return buildPayoff;
	}
	private void calculateBuildPayoff() {
		this.buildPayoff = getBuildCost().divide(BigInteger.valueOf(getResource().getMarketPrice() * getMaxProduction()));
	}
}