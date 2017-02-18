package com.outlawsource.domain;

import java.util.ArrayList;

public class ItemProperty {	
	private ItemName Name;
	private ArrayList<ItemPrice> VendorPrices;
	private ArrayList<ItemPrice> MarketPrices;
	private ArrayList<ItemPrice> ResourcePrices;
	private ArrayList<ItemPrice> RecyclePrices;
		
	private ArrayList<ItemConversion> ItemResources;
	private ArrayList<ItemConversion> ItemRecycle;
	
	public ItemProperty(String name) {
		Name = ItemName.valueOf(name.toLowerCase().replace(" ", "").replace(".", ""));
		VendorPrices = new ArrayList<ItemPrice>();
		MarketPrices = new ArrayList<ItemPrice>();
		ResourcePrices = new ArrayList<ItemPrice>();
		RecyclePrices = new ArrayList<ItemPrice>();
		ItemResources = setItemResources();
		ItemRecycle = setItemRecycle();
	}
	public String getName() {
		return Name.toString();
	}
	public void addVendorPrice(ItemPrice newPrice) {
		VendorPrices.add(newPrice);
	}
	public void addMarketPrice(ItemPrice newPrice) {
		MarketPrices.add(newPrice);
	}
	public void addResourcePrice(ItemPrice newPrice) {
		ResourcePrices.add(newPrice);
	}
	public void addRecyclePrice(ItemPrice newPrice) {
		RecyclePrices.add(newPrice);
	}
	public ArrayList<ItemPrice> getVendorPrices() {
		return VendorPrices;
	}
	public ArrayList<ItemPrice> getMarketPrices() {
		return MarketPrices;
	}
	public ArrayList<ItemPrice> getResourcePrices() {
		return ResourcePrices;
	}
	public ArrayList<ItemPrice> getRecyclePrices() {
		return RecyclePrices;
	}
	
	public boolean itemHasResources() {
		return !ItemResources.isEmpty();
	}
	public ArrayList<ItemConversion> getItemResources() {
		return ItemResources;
	}
	private ArrayList<ItemConversion> setItemResources() {
		ArrayList<ItemConversion> conversions = new ArrayList<ItemConversion>();
		
		switch(Name) {
		case aluminium:
			conversions.add(new ItemConversion("money", 1250));
			conversions.add(new ItemConversion(ItemName.bauxite.displayName(), 6));
			break;
		case attackdogs:
			conversions.add(new ItemConversion("money", 171766));
			conversions.add(new ItemConversion(ItemName.steel.displayName(), 1));
			conversions.add(new ItemConversion(ItemName.fossils.displayName(), 50));
			break;
		case batteries:
			conversions.add(new ItemConversion("money", 7500));
			conversions.add(new ItemConversion(ItemName.lithium.displayName(), 2));
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), 4));
			conversions.add(new ItemConversion(ItemName.aluminium.displayName(), 1));
			break;
		case bricks:
			conversions.add(new ItemConversion("money", 5));
			conversions.add(new ItemConversion(ItemName.clay.displayName(), 0.66f));
			break;
		case concrete:
			conversions.add(new ItemConversion("money", 1.4f));
			conversions.add(new ItemConversion(ItemName.gravel.displayName(), 0.21f));
			conversions.add(new ItemConversion(ItemName.limestone.displayName(), 0.14f));
			break;
		case copper:
			conversions.add(new ItemConversion("money", 833));
			conversions.add(new ItemConversion(ItemName.chalcopyrite.displayName(), 3));
			break;
		case electronics:
			conversions.add(new ItemConversion("money", 625));
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), 0.5f));
			conversions.add(new ItemConversion(ItemName.copper.displayName(), 0.375f));
			conversions.add(new ItemConversion(ItemName.silicon.displayName(), 0.125f));
			break;
		case eliteforce:
			conversions.add(new ItemConversion("money", 1235463));
			conversions.add(new ItemConversion(ItemName.weapons.displayName(), 60));
			conversions.add(new ItemConversion(ItemName.electronics.displayName(), 30));
			conversions.add(new ItemConversion(ItemName.gold.displayName(), 1));
			break;
		case fertilizer:
			conversions.add(new ItemConversion("money", 8.18f));
			conversions.add(new ItemConversion(ItemName.limestone.displayName(), 0.72f));
			break;
		case fossilfuel:
			conversions.add(new ItemConversion("money", 37.5f));
			conversions.add(new ItemConversion(ItemName.crudeoil.displayName(), 1));
			break;
		case glass:
			conversions.add(new ItemConversion("money", 375));
			conversions.add(new ItemConversion(ItemName.quartzsand.displayName(), 0.75f));
			conversions.add(new ItemConversion(ItemName.fossilfuel.displayName(), 1f));
			conversions.add(new ItemConversion(ItemName.limestone.displayName(), 0.5f));
			break;
		case gold:
			conversions.add(new ItemConversion("money", 6666.66f));
			conversions.add(new ItemConversion(ItemName.goldore.displayName(), 6.66f));
			break;
		case insecticides:
			conversions.add(new ItemConversion("money", 68.57f));
			conversions.add(new ItemConversion(ItemName.copper.displayName(), 0.03f));
			conversions.add(new ItemConversion(ItemName.limestone.displayName(), 0.09f));
			break;
		case jewellery:
			conversions.add(new ItemConversion("money", 25000));
			conversions.add(new ItemConversion(ItemName.roughdiamonds.displayName(), 500));
			conversions.add(new ItemConversion(ItemName.gold.displayName(), 0.5f));
			conversions.add(new ItemConversion(ItemName.silver.displayName(), 0.5f));
			break;
		case lithium:
			conversions.add(new ItemConversion("money", 1000));
			conversions.add(new ItemConversion(ItemName.lithiumore.displayName(), 23));
			break;
		case medicaltechnology:
		case medicaltechnologyinc:
			conversions.add(new ItemConversion("money", 9000));
			conversions.add(new ItemConversion(ItemName.titanium.displayName(), 0.4f));
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), 0.2f));
			conversions.add(new ItemConversion(ItemName.electronics.displayName(), 0.2f));
			break;
		case plastics:
			conversions.add(new ItemConversion("money", 40));
			conversions.add(new ItemConversion(ItemName.crudeoil.displayName(), 0.1f));
			break;
		case scandrones:
			conversions.add(new ItemConversion("money", 25000000));
			conversions.add(new ItemConversion(ItemName.electronics.displayName(), 25));
			conversions.add(new ItemConversion(ItemName.titanium.displayName(), 50));
			conversions.add(new ItemConversion(ItemName.batteries.displayName(), 250));
			break;
		case securitystaff:
			conversions.add(new ItemConversion("money", 136272));
			conversions.add(new ItemConversion(ItemName.weapons.displayName(), 5));
			conversions.add(new ItemConversion(ItemName.electronics.displayName(), 10));
			conversions.add(new ItemConversion(ItemName.silver.displayName(), 50));
			break;
		case silicon:
			conversions.add(new ItemConversion("money", 24750));
			conversions.add(new ItemConversion(ItemName.quartzsand.displayName(), 10));
			conversions.add(new ItemConversion(ItemName.clay.displayName(), 0.5f));
			conversions.add(new ItemConversion(ItemName.fossilfuel.displayName(), 2.5f));
			break;
		case silver:
			conversions.add(new ItemConversion("money", 200));
			conversions.add(new ItemConversion(ItemName.silverore.displayName(), 0.16f));
			break;
		case steel:
			conversions.add(new ItemConversion("money", 350));
			conversions.add(new ItemConversion(ItemName.ironore.displayName(), 7));
			conversions.add(new ItemConversion(ItemName.coal.displayName(), 10));
			break;
		case titanium:
			conversions.add(new ItemConversion("money", 1250));
			conversions.add(new ItemConversion(ItemName.ilmenite.displayName(), 2));
			break;
		case watchdogs:
			conversions.add(new ItemConversion("money", 17598));
			conversions.add(new ItemConversion(ItemName.steel.displayName(), 5));
			conversions.add(new ItemConversion(ItemName.fossils.displayName(), 5));
			break;
		case weapons:
			conversions.add(new ItemConversion("money", 10000));
			conversions.add(new ItemConversion(ItemName.steel.displayName(), 0.04f));
			conversions.add(new ItemConversion(ItemName.aluminium.displayName(), 0.04f));
			conversions.add(new ItemConversion(ItemName.batteries.displayName(), 0.04f));
			break;
		default:
			break;
		}
		
		return conversions;
	}
	
	public boolean itemHasRecycle() {
		return !ItemRecycle.isEmpty();
	}
	public ArrayList<ItemConversion> getItemRecycle() {
		return ItemRecycle;
	}
	
	private ArrayList<ItemConversion> setItemRecycle() {
		ArrayList<ItemConversion> conversions = new ArrayList<ItemConversion>();
		
		switch(Name) {
		case coppercoins:
			conversions.add(new ItemConversion(ItemName.techupgrade1.displayName(), (10f / 50000f)));
			break;
		case dronewreckage:
			conversions.add(new ItemConversion(ItemName.plasticscrap.displayName(), 100));
			conversions.add(new ItemConversion(ItemName.electronicscrap.displayName(), 50));
			conversions.add(new ItemConversion(ItemName.scrapmetal.displayName(), 40));
			conversions.add(new ItemConversion(ItemName.usedoil.displayName(), 30));
			break;
		case electronicscrap:
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), (200f / 500f)));
			conversions.add(new ItemConversion(ItemName.silicon.displayName(), (100f / 500f)));
			conversions.add(new ItemConversion(ItemName.copper.displayName(), (600f / 500f)));
			conversions.add(new ItemConversion(ItemName.silver.displayName(), (5000f / 500f)));
			break;
		case fossils:
			conversions.add(new ItemConversion(ItemName.limestone.displayName(), (100f / 200f)));
			conversions.add(new ItemConversion(ItemName.gravel.displayName(), (50f / 200f)));
			conversions.add(new ItemConversion(ItemName.clay.displayName(), (30f / 200f)));
			conversions.add(new ItemConversion(ItemName.quartzsand.displayName(), (10f / 200f)));
			break;
		case oldtires:
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), (40f / 100f)));
			conversions.add(new ItemConversion(ItemName.steel.displayName(), (10f / 100f)));
			break;
		case plasticscrap:
			conversions.add(new ItemConversion(ItemName.plastics.displayName(), (500f / 500f)));
			conversions.add(new ItemConversion(ItemName.usedoil.displayName(), (20f / 500f)));
			conversions.add(new ItemConversion(ItemName.aluminium.displayName(), (10f / 500f)));
			break;
		case romancoins:
			conversions.add(new ItemConversion(ItemName.techupgrade2.displayName(), (10f / 50000f)));
			break;
		case scrapmetal:
			conversions.add(new ItemConversion(ItemName.aluminium.displayName(), (30f / 1000f)));
			conversions.add(new ItemConversion(ItemName.steel.displayName(), (60f / 1000f)));
			conversions.add(new ItemConversion(ItemName.copper.displayName(), (20f / 1000f)));
			break;
		case usedoil:
			conversions.add(new ItemConversion(ItemName.crudeoil.displayName(), (20f / 250f)));
			conversions.add(new ItemConversion(ItemName.fossilfuel.displayName(), (40f / 250f)));
			conversions.add(new ItemConversion(ItemName.scrapmetal.displayName(), (20f / 250f)));
			break;
		case wasteglass:
			conversions.add(new ItemConversion(ItemName.glass.displayName(), (600f / 900f)));
			conversions.add(new ItemConversion(ItemName.scrapmetal.displayName(), (50f / 900f)));
			conversions.add(new ItemConversion(ItemName.plasticscrap.displayName(), (50f / 900f)));
			break;
		case maintenancekit:
			conversions.add(new ItemConversion(ItemName.titanium.displayName(), (500f / 5f)));
			conversions.add(new ItemConversion(ItemName.fossilfuel.displayName(), (100f / 5f)));
			conversions.add(new ItemConversion(ItemName.electronics.displayName(), (100f / 5f)));
			conversions.add(new ItemConversion(ItemName.plasticscrap.displayName(), (600f / 5f)));
			break;
		default:
			break;
		}
		
		return conversions;
	}
}
