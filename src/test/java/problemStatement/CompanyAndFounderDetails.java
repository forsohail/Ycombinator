package problemStatement;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CompanyAndFounderDetails {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://www.ycombinator.com/companies/");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		WebElement loadMoreCompany = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class ='styles-module__rightCol___2NKRr']//div[contains(text(),'Loading more...')]")));

		Actions a = new Actions(driver);
		try {
			//Scroll until all companies are loaded.
			while (loadMoreCompany.isDisplayed()){
					a.moveToElement(loadMoreCompany).build().perform();
				}
		} catch (Exception stException) {

		}
		
		int companyNumber = driver.findElements(By.xpath(
				"//div[@class ='styles-module__rightCol___2NKRr']//a[@class='styles-module__company___1UVnl no-hovercard']"))
				.size();
		System.out.println("Number of Companies are " + companyNumber);
		

		List<WebElement> companyList = driver.findElements(By.xpath(
				"//div[@class ='styles-module__rightCol___2NKRr']//a[@class='styles-module__company___1UVnl no-hovercard']"));
		

		for (WebElement cl : companyList) {
			String parent = driver.getWindowHandle();
			cl.click();
			Set<String> windowList = driver.getWindowHandles();
			// Now iterate using Iterator
			Iterator<String> I = windowList.iterator();
			while (I.hasNext()) {
				String child_Window = I.next();
				if (!parent.equals(child_Window)) {
					driver.switchTo().window(child_Window);
					
					System.out.println("Comapany URL: "+driver.getCurrentUrl());
					List<WebElement> founderLink = driver.findElements(By.xpath(
							"//div[contains(@class,'mt')]//a[contains(@class,'inline-block w-5 h-5 bg-contain')]"));
					for (WebElement fl : founderLink) {
						System.out.println("Founder Social Media Link: "+fl.getAttribute("href"));
					}
					System.out.println("**************************");
					//Close child  window
					driver.close();
				}
			}
			// switch to the parent window
			driver.switchTo().window(parent);

		}
		//close parent or all windows.
		driver.quit();
	}

}
