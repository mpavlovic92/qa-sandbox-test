package mpavlovic.sandbox;

import org.openqa.selenium.By;

public class UseCasesPageLocators {

	public static final By useCasesPageContainer = By.xpath("//div[contains(@class,'usecases')]");
	public static final By createUseCaseButton = By.xpath("//a[contains(@href,'create-usecase')]");
	public static final By createUseCaseContainer = By.xpath("//div[@class='container']//form");
	
	public static final By titleInputField = By.xpath("//input[@name='title']");
	public static final By descriptionTextareaField = By.xpath("//textarea[@name='description']");
	public static final By expectedResultInputField = By.xpath("//input[@name='expected_result']");
	public static final By automatedCheckboxDefaultValue = By.xpath("//input[@name='automated-switch']");
	public static final By automatedCheckbox = By.xpath("//label[@for='switch']");
	public static final By useCaseStepInputFields = By.xpath("//input[@id='stepId']");
	
	public static final By addStepButton = By.xpath("//span[contains(@data-testid,'add_step')]");
	public static final By submitButton = By.xpath("//button[@type='submit']");
}
