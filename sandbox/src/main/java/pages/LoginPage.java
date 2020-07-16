package pages;

import org.apache.log4j.Logger;

import action.BaseAction;
import mpavlovic.sandbox.ConfigurationStrings;
import mpavlovic.sandbox.LoginPageLocators;

public class LoginPage {
	BaseAction action;
	static Logger logger = Logger.getLogger(LoginPage.class);
	public LoginPage(BaseAction action) {
		this.action = action;
	}
	
	public LoginPage defaultPage() {
		action.waitElementToLoad(LoginPageLocators.defaultPage, "default page", 1);
		action.clickOnElement(LoginPageLocators.loginButton);
		
		logger.debug("Proceed to login page.");
		
		return this;
	}
	
	private void initLoginPage() {
		action.waitElementToLoad(LoginPageLocators.loginPageContainer, "login page", 1);
	}
	
	public LoginPage login() {
		initLoginPage();
		action.enterInputData(LoginPageLocators.usernameInputField, ConfigurationStrings.USERNAME);
		action.enterInputData(LoginPageLocators.passwordInputField, ConfigurationStrings.PASSWORD);
		action.clickOnElement(LoginPageLocators.submitButton);
		
		if(action.checkIfElementIsPresent(LoginPageLocators.loginErrorMessage, 2)) {
			String errorMessage = action.getElementText(LoginPageLocators.loginErrorMessage);
			logger.error("Unable to login due to '" + errorMessage + "' error message.");
			throw new RuntimeException("Unable to login due to '" + errorMessage + "' error message.");
		}
		
		if (action.checkIfElementIsPresent(ConfigurationStrings.loadingGIF, 1)) {
			action.waitForElementToBeInvisible(ConfigurationStrings.loadingGIF);
		}
		
		logger.debug("User: " + ConfigurationStrings.USERNAME + " successfully logged in.");
		
		return this;
	}
	
	public void signOut() {
		action.clickOnElement(LoginPageLocators.logoutButton);
		action.waitElementToLoad(LoginPageLocators.loginButton, "default page", 1);
		
		logger.debug("User successfully sign out.");
	}
}
