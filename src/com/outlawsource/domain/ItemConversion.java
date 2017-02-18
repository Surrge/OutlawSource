package com.outlawsource.domain;

public class ItemConversion {
	private String ItemName;
	private float ConversionRatio;
	
	public ItemConversion(String name, float ratio) {
		ItemName = name;
		ConversionRatio = ratio;
	}
	public String getItemName() {
		return ItemName;
	}
	public float getConversionRatio() {
		return ConversionRatio;
	}
}
