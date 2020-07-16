package mpavlovic.sandbox;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import action.BaseAction;
import util.TestDrive;

public class AppTest {

	WebDriver driver = null;
	BaseAction action = null;
	TestDrive testDrive = null;
	static Logger logger = Logger.getLogger(AppTest.class);
	
	@Before
	public void init() {
		System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		action = new BaseAction(driver);
		testDrive = new TestDrive(action);
	}

	@Test
	public void CreateUseCasesTest() {
		try {
			testDrive.RunCreateUseCasesTest();
		} catch (RuntimeException error) {
			action.takeScreenshot();
			throw (error);
		}
	}
	
	@After
	public void tearDown() {
		driver.close();
	}
}
