package net.outlawsource.business.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Factory {
	private String displayName;
	private Item product;
	
	private Integer firstItemPrice;
	private Integer firstItemRate;
	
	private Integer secondItemPrice;
	private Integer secondItemRate;
	
	private Integer thirdItemPrice;
	private Integer thirdItemRate;
	
	private Integer baseProduction;
	private Integer moneyRate;
	private Integer userLevel;
	
	// calculated
	private BigInteger hourlyProfit;
	private BigInteger upgradeCost;
	private BigInteger upgradePayoff;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Item getProduct() {
		return product;
	}
	public void setProduct(Item product) {
		this.product = product;
	}
	public Integer getBaseProduction() {
		return baseProduction;
	}
	public void setBaseProduction(Integer baseProduction) {
		this.baseProduction = baseProduction;
	}
	public Integer getMoneyRate() {
		return moneyRate;
	}
	public void setMoneyRate(Integer moneyRate) {
		this.moneyRate = moneyRate;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public Integer getFirstItemPrice() {
		return firstItemPrice;
	}
	public void setFirstItemPrice(Integer firstItemPrice) {
		this.firstItemPrice = firstItemPrice;
	}
	public Integer getFirstItemRate() {
		return firstItemRate;
	}
	public void setFirstItemRate(Integer firstItemRate) {
		this.firstItemRate = firstItemRate;
	}
	public Integer getSecondItemPrice() {
		return secondItemPrice;
	}
	public void setSecondItemPrice(Integer secondItemPrice) {
		this.secondItemPrice = secondItemPrice;
	}
	public Integer getSecondItemRate() {
		return secondItemRate;
	}
	public void setSecondItemRate(Integer secondItemRate) {
		this.secondItemRate = secondItemRate;
	}
	public Integer getThirdItemPrice() {
		return thirdItemPrice;
	}
	public void setThirdItemPrice(Integer thirdItemPrice) {
		this.thirdItemPrice = thirdItemPrice;
	}
	public Integer getThirdItemRate() {
		return thirdItemRate;
	}
	public void setThirdItemRate(Integer thirdItemRate) {
		this.thirdItemRate = thirdItemRate;
	}
	
	public void setHourlyProfit() {
		this.hourlyProfit = BigInteger.valueOf(getBaseProduction())
			.multiply(BigInteger.valueOf(getUserLevel()))
			.multiply(BigInteger.valueOf(getProduct().getMarketPrice() - getProduct().getFactoryCostPrice()));
	}
	public BigInteger getHourlyProfit() {
		return hourlyProfit;
	}

	public void setUpgradeCost() {
		BigDecimal moneyRate = BigDecimal.valueOf(getMoneyRate());
		BigDecimal firstRate = BigDecimal.valueOf(getFirstItemPrice()).multiply(BigDecimal.valueOf(getFirstItemRate()));
		BigDecimal secondRate = BigDecimal.valueOf(getSecondItemPrice()).multiply(BigDecimal.valueOf(getSecondItemRate()));
		BigDecimal thirdRate = BigDecimal.valueOf(getThirdItemPrice()).multiply(BigDecimal.valueOf(getThirdItemRate()));
		BigDecimal rate = moneyRate.add(firstRate).add(secondRate).add(thirdRate);
		
		BigDecimal levelFactor = BigDecimal.valueOf(Math.pow(getUserLevel() + 1, 2));
		
		this.upgradeCost = rate.multiply(levelFactor).toBigInteger();
	}
	public BigInteger getUpgradeCost() {		
		return upgradeCost;
	}
	
	public void setUpgradePayoff() {
		BigInteger upgradeProfit = BigInteger.valueOf(getBaseProduction())
				.multiply(BigInteger.valueOf(getProduct().getMarketPrice() - getProduct().getFactoryCostPrice()));
		
		upgradePayoff = upgradeCost.divide(upgradeProfit);
	}
	public BigInteger getUpgradePayoff() {
		return upgradePayoff;
	}
}