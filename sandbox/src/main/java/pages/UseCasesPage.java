package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import action.BaseAction;
import mpavlovic.sandbox.ConfigurationStrings;
import mpavlovic.sandbox.UseCase;
import mpavlovic.sandbox.UseCasesPageLocators;

public class UseCasesPage {
	BaseAction action;
	static Logger logger = Logger.getLogger(UseCasesPage.class);
	UseCase useCase;
	List<UseCase> useCases;
	List<WebElement> elements;
	WebElement element;
	Random random = new Random();
	
	String title;
	String description;
	String expectedResult;
	boolean automated;
	List<String> steps;
	
	public UseCasesPage(BaseAction action) {
		this.action = action;
	}
	
	public UseCasesPage initUseCasesPage() {
		action.waitElementToLoad(UseCasesPageLocators.useCasesPageContainer, "use cases page", 1);
		waitForLoading();
		
		return this;
	}
	
	public UseCasesPage createUseCases(int numberOfUseCases) {
		useCases = new ArrayList<UseCase>(numberOfUseCases);
		
		for (int index = 0; index < numberOfUseCases; index++) {
			useCases.add(createUseCase());
			initUseCasesPage();
		}
		
		return this;
	}
	
	public UseCase createUseCase() {
		action.clickOnElement(UseCasesPageLocators.createUseCaseButton, "Unable to click on 'Create Use Case' button.");
		waitForLoading();
		action.waitElementToLoad(UseCasesPageLocators.createUseCaseContainer, "create use case", 1);
		//logger.debug("Fill out use case data.");
		
		generateAndFillOutUseCaseData();
		checkAutomatedCheckbox(automated);
		fillOutUseCaseSteps();
		fillOutUseCaseData();
		
		action.clickOnElement(UseCasesPageLocators.submitButton, "Unable to submit new use case.");
		
		return useCase;
	}
	
	private void generateAndFillOutUseCaseData() {
		title = "Title_" + action.generateRandomString(action.getRandomIndex(20));
		description = "Description_" + action.generateRandomString(action.getRandomIndex(50));
		expectedResult = "Expected Result_" + action.generateRandomString(action.getRandomIndex(30));
		automated = true;//random.nextBoolean();

		action.enterInputData(UseCasesPageLocators.titleInputField, title, "Unable to fill out title field.");
		action.enterInputData(UseCasesPageLocators.descriptionTextareaField, description, "Unable to fill out description field.");
		action.enterInputData(UseCasesPageLocators.expectedResultInputField, expectedResult, "Unable to fill out expected results field.");
		logger.debug("Title: " + title + " | description: " + description + " | expected result: " + expectedResult);
	}
	
	private void checkAutomatedCheckbox(boolean state) {
		element = action.getDriver().findElement(UseCasesPageLocators.automatedCheckboxDefaultValue);
		String defaultValue = element.getAttribute("value");
		
		if (state) {
			if (defaultValue.equals("false"))
				action.clickOnElement(UseCasesPageLocators.automatedCheckbox, "Unable to select automated checkbox.");
		//}else {
		//	if (defaultValue.equals("true"))
		//		action.clickOnElement(UseCasesPageLocators.automatedCheckbox, "Unable to select automated checkbox.");
		}
	}
	
	private void fillOutUseCaseSteps() {
		int randomNumber = action.getRandomIndex(5);
		
		for (int index = 0; index < randomNumber; index++) {
			action.clickOnElement(UseCasesPageLocators.addStepButton);
				//action.pause(1000);
		}
		
		elements = action.getDriver().findElements(UseCasesPageLocators.useCaseStepInputFields);
		int numberOfUseCaseSteps = elements.size();
		steps = new ArrayList<String>(numberOfUseCaseSteps - 1);
		
		for (int useCaseIndex = 1; useCaseIndex <= numberOfUseCaseSteps; useCaseIndex++) {
			String useCaseStep = "Use case step " + useCaseIndex + "_" + action.generateRandomString(action.getRandomIndex(20));
			By step = By.xpath("(//input[@id='stepId'])[" + useCaseIndex + "]");
			action.enterInputData(step, useCaseStep);
			logger.debug(useCaseStep);
			steps.add(useCaseStep);
		}
	}
	
	private void fillOutUseCaseData() {
		useCase = new UseCase(title);
		useCase.set_description(description);
		useCase.set_expectedResult(expectedResult);
		useCase.set_automated(automated);
		useCase.set_steps(steps);
	}
	
	public UseCasesPage editUseCases() {
		for (int useCaseIndex = 0; useCaseIndex < useCases.size(); useCaseIndex++) {
			editUseCase(useCases.get(useCaseIndex));
			initUseCasesPage();
		}
		
		return this;
	}
	
	private void editUseCase(UseCase useCase) {
		By useCaseByName = By.xpath("//a[contains(@href,'use-cases') and text()='" + useCase.get_title() + "']");
		action.clickOnElement(useCaseByName, "Unable to select " + useCase.get_title() + ".");
		waitForLoading();
		action.waitElementToLoad(UseCasesPageLocators.createUseCaseContainer, "create use case", 1);
		
		editInputFields(useCase);
		
		action.clickOnElement(UseCasesPageLocators.submitButton, "Unable to submit new use case.");
	}
	
	private void editInputFields(UseCase useCase) {
		String prefix = "This field previously had ";
		String sufix = " characters";
		String message;
		int numberOfCharacters;
		
		numberOfCharacters = getNumberOfCharacters(UseCasesPageLocators.titleInputField);
		message = prefix + numberOfCharacters + sufix;
		logger.debug(message);
		action.enterInputData(UseCasesPageLocators.titleInputField, message, "Unable to edit title field.");
		
		numberOfCharacters = action.getElementText(UseCasesPageLocators.descriptionTextareaField).length();
		message = prefix + numberOfCharacters + sufix;
		logger.debug(message);
		action.enterInputData(UseCasesPageLocators.descriptionTextareaField, message, "Unable to edit description field.");
		
		numberOfCharacters = getNumberOfCharacters(UseCasesPageLocators.expectedResultInputField);
		message = prefix + numberOfCharacters + sufix;
		logger.debug(message);
		action.enterInputData(UseCasesPageLocators.expectedResultInputField, message, "Unable to edit expected results field.");
		
		for (int useCaseIndex = 1; useCaseIndex <= useCase.get_steps().size(); useCaseIndex++) {
			By step = By.xpath("(//input[@id='stepId'])[" + useCaseIndex + "]");
			numberOfCharacters = getNumberOfCharacters(step);
			message = prefix + numberOfCharacters + sufix;
			logger.debug(message);
			action.enterInputData(step, message, "Unable to edit " + useCaseIndex + ". use case step field.");
		}
	}

	private int getNumberOfCharacters(By locator) {
		element = action.getDriver().findElement(locator);
		
		return element.getAttribute("value").length();
	}
	
	private void waitForLoading() {
		if (action.checkIfElementIsPresent(ConfigurationStrings.loadingGIF, 1)) {
			action.waitForElementToBeInvisible(ConfigurationStrings.loadingGIF);
		}
	}
}
