package Automation.Naukri;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NaukriAutomation {

	public static void main(String[] args) {
		// Set up WebDriver
		WebDriverManager.chromedriver().clearDriverCache().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // Run browser in background
//		options.addArguments("--window-size=1920,1080"); // Sets the browser window size to 1920x1080 pixels
//		options.addArguments("--disable-gpu"); // Disables GPU hardware acceleration.
//		options.addArguments("--no-sandbox"); // Disables the Chrome security sandbox
//		options.addArguments("--disable-dev-shm-usage"); // Prevents Chrome from using /dev/shm (shared memory) for temporary storage
//		options.addArguments("--enable-javascript"); // Ensures JavaScript execution is enabled in Chrome
		
		//options.addArguments("--headless=new");
		//options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);

		try {
			// Maximize browser window
			driver.manage().window().maximize();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// Navigate to Naukri login page
			driver.get("https://www.naukri.com/nlogin/login");
			System.out.println("URL Hit");
			Thread.sleep(1000);
			
			// Wait until login elements are available
			WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usernameField")));
			WebElement passwordField = driver.findElement(By.id("passwordField"));
			WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit'][1]"));

			// Enter login credentials
//			emailField.sendKeys(System.getenv("NAUKRI_USERNAME")); // Email from environment variable
//			passwordField.sendKeys(System.getenv("NAUKRI_PASSWORD")); // Password from environment variable
			
			emailField.sendKeys("aniketgapat0303@gmail.com");
			passwordField.sendKeys("Aniket@333");

			// Click login button
			loginButton.click();
			System.out.println("Profile SignIn");

			// Wait until the profile Button loads and click on that
			WebElement viewButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='View profile']")));
			viewButton.click();

			// Javascript executor to mimic human behaviour
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)");
			Thread.sleep(500);
			WebElement headline = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[@class='card mt15']/div/div/span[@class='edit icon']")));
			headline.click();

			// Get text from resume headline
			WebElement data = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resumeHeadlineTxt")));
			String text = data.getText();
			System.out.println(text);

//			if (text.endsWith(".")) {
//				text = text.substring(0, text.length() - 1); // Remove the last dot
//			} else {
//				text = text + "."; // Add a dot at the end
//			}
			
			//Use ternary operator instead of if-else
			text = text.endsWith(".") ? text.substring(0, text.length() - 1) : text + ".";
			
			// clear the existing text and add updated text with or without dot
			data.clear();
			Thread.sleep(2000);
			data.sendKeys(text);

			// Update profile by clicking on a button or filling a field
			WebElement saveButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save']")));
			saveButton.click();
			System.out.println("Profile Updated");
			Thread.sleep(1000);

			// Move to notifications
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.cssSelector(".nI-gNb-notif-center"))).build().perform();
			Thread.sleep(500);

			// Log out
			driver.findElement(By.cssSelector(".nI-gNb-drawer__icon")).click();
			Thread.sleep(2000);
			WebElement logout = wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.linkText("Logout"))));
			logout.click();
			System.out.println("Profile Logout");

		} catch (Exception e) {
			System.out.println("Exception Type: " + e.getClass().getName());
			System.out.println("Exception Message: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}
}
