package core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	//What is factory is something that produces something. In this case is a class that will be generating recent drivers based on "browser" variable given.
	private WebDriver driver;
	
	public WebDriver getDriver(String browserName) throws Exception {
		
		switch(browserName.toLowerCase()) {
		case "chrome": 
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Olin\\Downloads\\recent_drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox": 
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Olin\\Downloads\\recent_drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "edge": 
			System.setProperty("webdriver.edge.driver", "C:\\Users\\Olin\\Downloads\\recent_drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		default: 
			throw new Exception("Incorrect browser "+ browserName + " provided");
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	public void tearDown() 
	{
		if(driver != null) {
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			driver.quit();
		}
	}
}
