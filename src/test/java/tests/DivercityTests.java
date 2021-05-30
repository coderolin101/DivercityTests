/*
 * TITLE: CHECK POSSIBLE BROKEN LINKS IN FOOTER
 * AUTHOR: OLIN SAINT_LOUIS
 * DATE: 05/29/2021
 * VERSION: 1.0
 */
package tests;

import java.io.IOException;
import core.DriverFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


public class DivercityTests {

	private WebDriver driver;
	private DriverFactory df;
	
	public DivercityTests() throws Exception
	{
		String browser = System.getenv("browser");  //Different browsers given by env variable to be tested.
		df = new DriverFactory();
		driver = df.getDriver(browser);
		driver.get("http://sandbox.divercity.io/"); //same website is to be tested.
	}
	
	@Test
	public void TestFooterLinks() throws IOException 
	{
		
		List<WebElement> footerLinks = driver
				.findElements(By.xpath(".//div[@class='footer-first-wrapper']//a")); //using xpath method to find all relevant links in footer div section.
		
		for(WebElement ele: footerLinks)	{
			String url = ele.getAttribute("href");

			HttpURLConnection huc = null;
            
            huc = (HttpURLConnection)(new URL(url).openConnection());    
            huc.setRequestMethod("GET");  
            huc.connect();
            
            int respCode = huc.getResponseCode();  //using the response code to print out link category each iterations.
            
            if(respCode >= 400 && respCode != 999)  //
            {
               System.out.println(url + " is a broken link " + respCode);
            }
            
            else if(respCode == 999)  // one significant type of many possible errors. Does not fall into "Broken link" category as most links are of 200 type.
            {
            	System.out.println(url + " is a blocked User Agent error \"Not Broken\" limited Access " + respCode);
            }
            
            else{
               System.out.println(url + " is a valid link "  + respCode);
            }

        }
        
    }
	
	@AfterTest
	public void tearDown() throws Exception  // After the test is performed above then close the browser session.
	{
		df.tearDown();
	}
	
}
