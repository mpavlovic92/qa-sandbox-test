package action;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public class BaseAction {
	public WebDriver driver;
	public WebDriverWait wait;
	static Logger logger = Logger.getLogger(BaseAction.class);

	public BaseAction(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}

	public BaseAction(WebDriver driver, int defaultWaitTime) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, defaultWaitTime);
	}

	public void waitElementToLoad(By contentLocator, String pageName, int numberOfTry) {
		int index = 0;
		String space = pageName.equals("") ? "" : " ";

		while (index < numberOfTry) {
			if (!checkIfElementIsPresent(contentLocator)) {
				logger.warn("Unable to load" + space + pageName + ". Try to refresh...");
				driver.navigate().refresh();

				index++;
				continue;
			} else {
				break;
			}

		}

		if (index == numberOfTry && !checkIfElementIsPresent(contentLocator, 5)) {
			logger.error("Unable to load" + space + pageName + " content.");
			throw new RuntimeException("Unable to load" + space + pageName + " content.");
		}
	}

	public void clickOnElement(By locator) {
		clickOnElement(locator, "");
	}

	public void clickOnElement(By locator, String errorMessage) {
		try {
			waitForElementToBeClickable(locator);
			driver.findElement(locator).click();
		} catch (RuntimeException e) {
			if (errorMessage.equals("")) {
				logger.error("Unable to click on element.");
				throw new RuntimeException("Unable to click on element.");
			} else {
				logger.error(errorMessage);
				throw new RuntimeException(errorMessage);
			}
		}
	}

	public void enterInputData(By locator, String inputText) {
		enterInputData(locator, inputText, "");
	}

	public void enterInputData(By locator, String inputText, String errorMessage) {
		try {
			waitForElementToBeVisible(locator);
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(inputText);
		} catch (RuntimeException e) {
			if (errorMessage.equals("")) {
				logger.error("Unable to enter input data.");
				throw new RuntimeException("Unable to enter input data.");
			} else {
				logger.error(errorMessage);
				throw new RuntimeException(errorMessage);
			}
		}
	}

	public void waitForElementToBeVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElementToBeClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitForElementToBeInvisible(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public boolean checkIfElementIsPresent(By elementLocation) {
		try {
			driver.findElement(elementLocation);

			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean checkIfElementIsPresent(By locator, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			driver.findElement(locator);

			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getElementText(By locator) {
		String text = "";

		try {
			waitForElementToBeVisible(locator);
			text = driver.findElement(locator).getText();
		} catch (Exception e) {
			logger.error("Unable to get text from element.");
			throw new RuntimeException("Unable to get text from element.");
		}

		return text;
	}

	public void takeScreenshot() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String tempFileName = "sandbox_" + timeStamp + ".jpg";
		String fileName = tempFileName.replaceAll(" ", "_");
		logger.warn("TAKE SCREENSHOT: " + fileName);

		try {
			Screenshot screenshot = new AShot().takeScreenshot(driver);
			ImageIO.write(screenshot.getImage(), "jpg", new File(".\\screenshots\\" + fileName));
		} catch (IOException e) {
			logger.error("Unable to take a screenshot.");
			throw new RuntimeException(e);
		}
	}

	public void pause(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			logger.error("Unable to take thread suspension.");
			throw new RuntimeException(e);
		}
	}
	
	public String generateRandomString(int lenght) {

		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public int getRandomIndex(int maxValue){
		Random random = new Random();
		return maxValue == 1? 1 : random.nextInt(maxValue - 1) + 1;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
}
