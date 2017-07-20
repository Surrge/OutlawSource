package net.outlawsource.web.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.outlawsource.business.domain.Factory;
import net.outlawsource.business.domain.ItemConversion;
import net.outlawsource.business.domain.ItemPrice;
import net.outlawsource.business.domain.ItemProperty;
import net.outlawsource.business.domain.ItemSummary;
import net.outlawsource.business.domain.Mine;
import net.outlawsource.business.domain.User;
import net.outlawsource.data.FactoryDAO;
import net.outlawsource.data.ItemDAO;
import net.outlawsource.data.MineDAO;
import net.outlawsource.data.PriceDAO;

@Controller
@RequestMapping("Resources")
public class ResourcesController {
	
	@Autowired
	FactoryDAO factoryDao;
	
	@Autowired
	MineDAO mineDao;
	
	@Autowired
	ItemDAO itemDao;
	
	@Autowired
	PriceDAO priceDao;

	@RequestMapping("Hub")
	public String getHub() {
		return "Hub";
	}
	
	@RequestMapping(path = "Stats", method = RequestMethod.GET)
	public String getStats() {
		return "Stats";
	}
	
	@RequestMapping("Alert")
	public String getAlert() {
		return "Alert";
	}
	
	@RequestMapping(path = "Chart", method = RequestMethod.GET)
	public String getChart() {
		return "Chart";
	}
	
	@RequestMapping("GetItems")
	public @ResponseBody List<String> getItems() throws Exception {
		return itemDao.getItemNames();
	}
	
	@RequestMapping("GetLatestPrice")
	public @ResponseBody String lastUpdate() throws Exception {
		// TODO: return Date
		SimpleDateFormat jsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		TimeZone tz = TimeZone.getTimeZone("UTC");
		jsFormat.setTimeZone(tz);
		return jsFormat.format(priceDao.getLatestPrice());
	}
	
	@RequestMapping("Summary")
	public String getSummary(HttpServletRequest request) throws Exception {
		Map<String, List<ItemSummary>> summaries = priceDao.getSummary();
		
		request.setAttribute("resources", summaries.get("Resources"));
		request.setAttribute("products", summaries.get("Product"));
		request.setAttribute("loot", summaries.get("Loot"));
		request.setAttribute("units", summaries.get("Unit"));
		
		return "Summary";
	}
	
	@RequestMapping(path = "Stats", method = RequestMethod.POST)
	public String doStats(HttpServletRequest request) throws Exception {
		List<String> messages = new ArrayList<String>();
		User user = (User) request.getSession().getAttribute("user");
		
		String item = request.getParameter("item");
		try {
			Integer level = Integer.parseInt(request.getParameter("level"));
			
			factoryDao.updateFactoryLevel(user.getUserId(), item, level);			
			messages.add("Factory " + item + " successfully upgraded to Level " + level);
		}
		catch(NumberFormatException ex) {
			messages.add("Submitted level is unrecognized, expected format is numbers only");
		}
		
		request.setAttribute("messages", messages);
		request.setAttribute("factories", factoryDao.getUserFactories(user.getUserId()));
		return "Stats";
	}
	
	@RequestMapping(path = "GetUserFactories", method = RequestMethod.GET)
	public @ResponseBody List<Factory> getUserFactories(HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<Factory> factories = factoryDao.getUserFactories(user.getUserId());
		
		// Calculate
		int levelTotal = 0;
		BigInteger hourlyProfitTotal = BigInteger.ZERO;
		BigInteger upgradeCostTotal = BigInteger.ZERO;
		BigInteger upgradePayoffTotal = BigInteger.ZERO;
		for(Factory factory : factories) {
			factory.setHourlyProfit();
			factory.setUpgradeCost();
			factory.setUpgradePayoff();
			
			levelTotal += factory.getUserLevel();
			hourlyProfitTotal = hourlyProfitTotal.add(factory.getHourlyProfit());
			upgradeCostTotal = upgradeCostTotal.add(factory.getUpgradeCost());
			upgradePayoffTotal = upgradePayoffTotal.add(factory.getUpgradePayoff());
		}
		
		// TODO: cant set
		Factory totals = new Factory();
		totals.setUserLevel(levelTotal);
		totals.setHourlyProfit();
		totals.setUpgradeCost();
		totals.setUpgradePayoff();
		
		factories.add(totals);
		return factories;
	}
	
	@RequestMapping(path = "UpdateUserFactories", method = RequestMethod.POST)
	public @ResponseBody Factory updateUserFactory(HttpServletRequest request, 
			@RequestParam String item, @RequestParam Integer level) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		
		factoryDao.updateFactoryLevel(user.getUserId(), item, level);
		Factory updatedFactory = factoryDao.getUserFactory(user.getUserId(), item);
		updatedFactory.setHourlyProfit();
		updatedFactory.setUpgradeCost();
		updatedFactory.setUpgradePayoff();
		
		return updatedFactory;
	}
	
	@RequestMapping(path = "GetUserMines", method = RequestMethod.GET)
	public @ResponseBody List<Mine> getUserMines(HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<Mine> mines = mineDao.getUserMines(user.getUserId());
    	
		// Calculate
		int totalQuantity = 0;
		int totalHourlyYield = 0;
		BigInteger totalHourlyProfit = BigInteger.ZERO;
		BigInteger totalBuildCost = BigInteger.ZERO;
		BigInteger totalBuildPayoff = BigInteger.ZERO;
		for(Mine mine : mines) {
			totalQuantity += mine.getUserQuantity();
			totalHourlyYield += mine.getUserHourlyYield();
		}
		for(Mine mine : mines) {
			mine.Calculate(totalQuantity);
			totalHourlyProfit = totalHourlyProfit.add(mine.getHourlyProfit());
			totalBuildCost = totalBuildCost.add(mine.getBuildCost());
			totalBuildPayoff = totalBuildPayoff.add(mine.getBuildPayoff());
		}
		
		Mine totals = new Mine();
		totals.setUserQuantity(totalQuantity);
		totals.setUserHourlyYield(totalHourlyYield);
		//TODO: doesnt work
		//totals.setHourlyProfit\
		//totals.setBaseCost(totalBuildCost);
		//totals.setBasePayoff(totalBuildPayoff);
		mines.add(totals);
		
		return mines;
	}
	
	@RequestMapping(path = "UpdateUserMines", method = RequestMethod.POST)
	public @ResponseBody boolean updateUserMine(HttpServletRequest request,
			@RequestParam String item, @RequestParam Integer quantity, @RequestParam Integer hourlyYield) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
    	
		if(quantity != null) {				
			mineDao.updateUserMine(user.getUserId(), item, quantity, null);
			return true;
		}
		else if(hourlyYield != null) {			
			mineDao.updateUserMine(user.getUserId(), item, null, hourlyYield);
			return true;
		}
		else {
			return false;
		}
	}
	
	@RequestMapping(path = "Chart", method = RequestMethod.POST)
	public @ResponseBody ItemProperty getChartPrices(
			@RequestParam String itemname, @RequestParam boolean getHistorical,
			@RequestParam boolean getResources, @RequestParam boolean getRecycle) throws Exception {
		
		ItemProperty itemPrices = priceDao.getItemPrices(itemname, getHistorical);
		
		String resourceCalculation = "";
		if(getResources && itemPrices.itemHasResources()) {
			resourceCalculation += "Factory Cost = ";
			List<ItemConversion> conversions = itemPrices.getItemResources();
			
			List<ItemProperty> resourceItems = new ArrayList<ItemProperty>();
			for(ItemConversion conversion : conversions) {
				String conversionItem = conversion.getItemName();
				if(conversionItem == "money") {	
					resourceCalculation += conversion.getConversionRatio() + "($$)";
				}
				else {
					resourceCalculation += " + " + conversion.getConversionRatio() + "(" + conversion.getItemName() + ")";
					resourceItems.add(priceDao.getItemPrices(conversion.getItemName(), getHistorical));
				}
			}
			
			// Link Resource prices back to Product(item)
			for(int dateIndex = 0; dateIndex < itemPrices.getMarketPrices().size(); dateIndex++) {
				float resourceCost = conversions.get(0).getConversionRatio();	// money
				
				for(int itemIndex = 1; itemIndex < conversions.size(); itemIndex++) {
					ItemProperty resourceItem = resourceItems.get(itemIndex - 1);
					float conversionRate = conversions.get(itemIndex).getConversionRatio();
					double resourcePrice = (resourceItem.getMarketPrices().get(dateIndex).getPrice() > 0)
							? resourceItem.getMarketPrices().get(dateIndex).getPrice() * conversionRate
							: resourceItem.getVendorPrices().get(dateIndex).getPrice() * conversionRate;
					
					resourceCost += resourcePrice;
				}
				
				itemPrices.addResourcePrice(new ItemPrice(
						Math.round(resourceCost),
						itemPrices.getMarketPrices().get(dateIndex).getCreateDate()
				));
			}
		}		
		
		// Get Recycle Yield
		String recycleCalculation = "";
		if(getRecycle && itemPrices.itemHasRecycle()) {
			recycleCalculation += "Recycle Yield = ";
			List<ItemConversion> conversions = itemPrices.getItemRecycle();
			
			List<ItemProperty> recycleItems = new ArrayList<ItemProperty>();
			for(ItemConversion conversion : conversions) {
				String conversionItem = conversion.getItemName();
				recycleCalculation += " + " + conversion.getConversionRatio() + "(" + conversion.getItemName() + ")";
				recycleItems.add(priceDao.getItemPrices(conversion.getItemName(), getHistorical));
			}
			
			// Link Resource prices back to Product(item)
			for(int dateIndex = 0; dateIndex < itemPrices.getMarketPrices().size(); dateIndex++) {
				float recycleYield = 0;
				
				for(int itemIndex = 0; itemIndex < conversions.size(); itemIndex++) {
					ItemProperty resourceItem = recycleItems.get(itemIndex);
					float conversionRate = conversions.get(itemIndex).getConversionRatio();
					double recyclePrice = resourceItem.getMarketPrices().get(dateIndex).getPrice() > 0
							? resourceItem.getMarketPrices().get(dateIndex).getPrice()
							: resourceItem.getVendorPrices().get(dateIndex).getPrice();
					
					recycleYield += recyclePrice * conversionRate;
				}
				
				itemPrices.addResourcePrice(new ItemPrice(
						Math.round(recycleYield),
						itemPrices.getMarketPrices().get(dateIndex).getCreateDate()
				));
			}
		}
		
		return itemPrices;
	}
}
