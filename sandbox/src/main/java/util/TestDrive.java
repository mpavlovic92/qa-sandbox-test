package util;

import action.BaseAction;
import mpavlovic.sandbox.ConfigurationStrings;
import pages.DashboardPage;
import pages.LoginPage;
import pages.UseCasesPage;

public class TestDrive {
	private BaseAction action;
	private LoginPage loginPage;
	private DashboardPage dashboardPage;
	private UseCasesPage useCasesPage;
	
	public TestDrive(BaseAction action) {
		this.action = action;
		this.loginPage = new LoginPage(this.action);
		this.dashboardPage = new DashboardPage(this.action);
		this.useCasesPage = new UseCasesPage(this.action);
	}
	
	public void RunCreateUseCasesTest() {
		initTest();
		
		loginPage.defaultPage()
			.login();
		
		dashboardPage.initDashboardPage()
			.selectUseCasesSection();
		
		useCasesPage.initUseCasesPage()
			.createUseCases(ConfigurationStrings.NUMBER_OF_USE_CASES)
			.editUseCases();
		
		loginPage.signOut();
	}
	
	private void initTest() {
		action.getDriver().manage().window().maximize();
		action.getDriver().get(ConfigurationStrings.URL);
	}
}
