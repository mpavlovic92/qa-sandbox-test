package pages;

import org.apache.log4j.Logger;

import action.BaseAction;
import mpavlovic.sandbox.ConfigurationStrings;
import mpavlovic.sandbox.DashboardPageLocators;

public class DashboardPage {
	BaseAction action;
	static Logger logger = Logger.getLogger(DashboardPage.class);
	
	public DashboardPage(BaseAction action) {
		this.action = action;
	}
	
	public DashboardPage initDashboardPage() {
		action.waitElementToLoad(DashboardPageLocators.dashboardPageContainer, "sandbox home page", 1);
		
		logger.debug("Welcome to the Dashboard page.");
		
		return this;
	}
	
	public DashboardPage selectUseCasesSection() {
		action.clickOnElement(DashboardPageLocators.useCasesSection, "Unable to click on use cases section.");
		
		if (action.checkIfElementIsPresent(ConfigurationStrings.loadingGIF, 1)) {
			action.waitForElementToBeInvisible(ConfigurationStrings.loadingGIF);
		}
		
		logger.debug("Proceed to the Use Cases page.");
		
		return this;
	}
}
