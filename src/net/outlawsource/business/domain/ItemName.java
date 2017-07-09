package net.outlawsource.business.domain;

public enum ItemName {
	aluminium("Aluminium"),
	attackdogs("Attack dogs"),
	batteries("Batteries"),
	bauxite("Bauxite"),
	bricks("Bricks"),
	chalcopyrite("Chalcopyrite"),
	clay("Clay"),
	coal("Coal"),
	concrete("Concrete"),
	copper("Copper"),
	coppercoins("Copper coins"),
	crudeoil("Crude oil"),
	dronewreckage("Drone wreckage"),
	electronicscrap("Electronic scrap"),
	electronics("Electronics"),
	eliteforce("Elite Force"),
	fertilizer("Fertilizer"),
	fossilfuel("Fossil fuel"),
	fossils("Fossils"),
	gangster("Gangster"),
	giantdiamond("Giant diamond"),
	glass("Glass"),
	gold("Gold"),
	goldore("Gold ore"),
	gravel("Gravel"),
	ilmenite("Ilmenite"),
	insecticides("Insecticides"),
	ironore("Iron ore"),
	jewellery("Jewellery"),
	limestone("Limestone"),
	lithium("Lithium"),
	lithiumore("Lithium ore"),
	maintenancekit("Maintenance kit"),
	medicaltechnology("Medical technology"),
	medicaltechnologyinc("Medical technology Inc."),
	oldtires("Old tires"),
	plasticscrap("Plastic scrap"),
	plastics("Plastics"),
	privatearmy("Private army"),
	quartzsand("Quartz sand"),
	romancoins("Roman coins"),
	roughdiamonds("Rough diamonds"),
	scandrones("Scan drones"),
	scrapmetal("Scrap metal"),
	securitystaff("Security Staff"),
	silicon("Silicon"),
	silver("Silver"),
	silverore("Silver ore"),
	steel("Steel"),
	techupgrade1("Techupgrade 1"),
	techupgrade2("Techupgrade 2"),
	techupgrade3("Techupgrade 3"),
	techupgrade4("Techupgrade 4"),
	titanium("Titanium"),
	usedoil("Used oil"),
	wasteglass("Waste glass"),
	watchdogs("Watch dogs"),
	weapons("Weapons");
	
	private String displayName;
	
	ItemName(String name) {
		this.displayName = name;
	}

	public String displayName() {
		return displayName;
	}
}