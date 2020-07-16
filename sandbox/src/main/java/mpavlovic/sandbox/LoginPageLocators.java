package mpavlovic.sandbox;

import org.openqa.selenium.By;

public class LoginPageLocators {
	
	public static final By defaultPage = By.xpath("//div[contains(@class,'landing-inner')]");
	public static final By loginButton = By.xpath("//nav[contains(@class,'navbar')]//a[@href='/login']");
	
	public static final By loginPageContainer = By.xpath("//div[contains(@class,'login')]");
	public static final By usernameInputField = By.xpath("//input[@name='email']");
	public static final By passwordInputField = By.xpath("//input[@name='password']");
	public static final By submitButton = By.xpath("//button[@type='submit']");
	public static final By loginErrorMessage = By.xpath("//div[contains(@class,'invalid-feedback')]");
	
	public static final By logoutButton = By.xpath("//a[contains(@href,'top') and contains(text(),'Logout')]");
}
